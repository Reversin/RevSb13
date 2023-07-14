package com.example.revsb_11.views


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.revsb_11.R
import com.example.revsb_11.adapters.ItemRecycleAdapter
import com.example.revsb_11.contracts.SelectedFilesContract
import com.example.revsb_11.data.SelectedFile
import com.example.revsb_11.databinding.SelectedFilesFragmentBinding
import com.example.revsb_11.models.FileNameModel
import com.example.revsb_11.presenters.SelectedFilesPresenter


class SelectedFilesFragment : Fragment(), SelectedFilesContract.View {

    private var _binding: SelectedFilesFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var selectedFilesPresenter: SelectedFilesContract.Presenter
    private lateinit var model: SelectedFilesContract.Model
    private var recyclerView: RecyclerView? = null
    private lateinit var adapter: ItemRecycleAdapter
    private val typeDialog = R.string.typeDialog
    private val nameSP = R.string.nameSP
    private val args: SelectedFilesFragmentArgs by navArgs()


    private var getContent = registerForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) {
        selectedFilesPresenter.fileHasBeenSelected(it, context?.contentResolver)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = SelectedFilesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initModel()
        initPresenters()
        selectedFilesPresenter.onScreenOpened()
        navigationListener()
        setClickListeners()
        setTitle()

    }

    private fun initModel() {
        val context = this.context
        if (context != null) {
            val prefs = context.getSharedPreferences(getString(nameSP), Context.MODE_PRIVATE)
            model = FileNameModel(prefs)
        } else {
            Toast.makeText(
                requireContext().applicationContext, "Вась, мы модель уронили", Toast.LENGTH_SHORT
            ).show()
            return
        }
    }

    override fun initAdapterRecycleView(itemsList: List<SelectedFile>) {
        recyclerView = view?.findViewById(R.id.recyclerViewFiles)
        adapter = ItemRecycleAdapter(onEditButtonClicked = { filename ->
            selectedFilesPresenter.onItemClicked(filename)
        }, onSwipeToDelete = { filename ->
            selectedFilesPresenter.onSwipeDeleteItem(filename)
        })
        adapter.setItems(itemsList)
        recyclerView?.let { adapter.attachSwipeToDelete(it) }
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = LinearLayoutManager(context)
    }

    override fun goToFragmentForChanges(selectedFile: SelectedFile) {
        val action = selectedFile.longTermPath?.let {
            selectedFile.fileComments?.let { it1 ->
                SelectedFilesFragmentDirections.actionSelectedFilesFragmentToAddFileCommentsFragment(
                    it, it1
                )
            }
        }
        if (action != null) {
            findNavController().navigate(action)
        }
    }

    private fun navigationListener() {
        val argums = arguments
        if (argums != null) {
            if (!argums.isEmpty) {
                val arg1 = args.selectedFilesFragmentArgument
                val arg2 = args.selectedFilesFragmentArgument2
                selectedFilesPresenter.fileCommentHasChanged(arg1, arg2)
            }
        }
    }


    private fun initPresenters() {
        selectedFilesPresenter = SelectedFilesPresenter(this, model)
    }

    private fun setClickListeners() {
        binding.fileButton1.setOnClickListener {
            selectedFilesPresenter.onFindFileButtonClicked()
        }
    }

    private fun setTitle() {
        requireActivity().title = getString(R.string.fTitle_name)
    }

    override fun openFileSelector() = getContent.launch(arrayOf(getString(typeDialog)))

    override fun setFileNameTitle(itemsList: List<SelectedFile>) = adapter.setItems(itemsList)


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
