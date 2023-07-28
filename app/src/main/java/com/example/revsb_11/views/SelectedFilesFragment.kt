package com.example.revsb_11.views


import SelectedFilesAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.revsb_11.R
import com.example.revsb_11.dataclasses.SelectedFile
import com.example.revsb_11.databinding.SelectedFilesFragmentBinding
import com.example.revsb_11.viewmodels.SelectedFilesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SelectedFilesFragment : Fragment() {

    private var _binding: SelectedFilesFragmentBinding? = null
    private val binding get() = _binding!!
    private var recyclerView: RecyclerView? = null
    private lateinit var adapter: SelectedFilesAdapter
    private val args: SelectedFilesFragmentArgs by navArgs()
    private val viewModel: SelectedFilesViewModel by viewModel()


    private var getContent = registerForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) {
            viewModel.fileHasBeenSelected(uri)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = SelectedFilesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSelectedFilesList()
        viewModel.onScreenOpened()
        setNavigationListener()
        setClickListeners()
        setTitle()
        observeScreenState()
    }

    private fun observeScreenState() {
        viewModel.savedSelectedFilesListLiveData.observe(viewLifecycleOwner) { savedSelectedFilesList ->
            initAdapterRecycleView(savedSelectedFilesList)
        }
        viewModel.selectedFilesListLiveData.observe(viewLifecycleOwner) { selectedFilesList ->
            updateFileCommentsList(selectedFilesList)
        }
        viewModel.onFindFileButtonClickedLiveData.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                openFileSelector()
            }
        }
        viewModel.selectedFileLiveData.observe(viewLifecycleOwner) { selectedFile ->
            goToFragmentForChanges(selectedFile)
        }
    }

    private fun initSelectedFilesList() {
        viewModel.updateSelectedFilesList()
    }

    private fun initAdapterRecycleView(selectedFilesList: List<SelectedFile>) {
        recyclerView = view?.findViewById(R.id.recyclerViewFiles)
        adapter = SelectedFilesAdapter(onEditButtonClicked = { filename ->
            viewModel.onSelectedFileClicked(filename)
        }, onSwipeToDelete = { selectedFile ->
            viewModel.onSwipeDeleteItem(selectedFile)
        })
        adapter.submitList(selectedFilesList)
        recyclerView?.let { adapter.attachSwipeToDelete(it) }
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = LinearLayoutManager(context)
    }

    private fun goToFragmentForChanges(selectedFile: SelectedFile) {
        val action =
            SelectedFilesFragmentDirections.actionSelectedFilesFragmentToAddFileCommentsFragment(
                selectedFile.longTermPath, selectedFile.fileComments
            )
        findNavController().navigate(action)
    }

    private fun setNavigationListener() {
        val arguments = arguments
        if (arguments != null) {
            if (!arguments.isEmpty) {
                val arg1 = args.originalFileUri
                val arg2 = args.newFileComments
                arguments.clear()
                viewModel.setFileCommentHasChanged(arg1, arg2)
            }
        }
    }

    private fun setClickListeners() {
        binding.fileButton1.setOnClickListener {
            viewModel.onFindFileButtonClicked()
        }
    }

    private fun setTitle() {
        requireActivity().title = getString(R.string.fTitle_name)
    }

    private fun openFileSelector() =
        getContent.launch(arrayOf(getString(R.string.typeDialog)))

    private fun updateFileCommentsList(selectedFilesList: List<SelectedFile>) =
        adapter.submitList(selectedFilesList)


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
