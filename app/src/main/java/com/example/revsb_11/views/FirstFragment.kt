package com.example.revsb_11.views


import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.revsb_11.adapters.ItemRecycleAdapter
import com.example.revsb_11.contracts.FirstFragmentContract
import com.example.revsb_11.data.GetNameFromUri
import com.example.revsb_11.data.Item
import com.example.revsb_11.models.FileNameModel
import com.example.revsb_11.presenters.FirstFragmentPresenter
import com.example.revsb_11.R
import com.example.revsb_11.databinding.FragmentFirstBinding
import java.util.Locale


class FirstFragment : Fragment(), FirstFragmentContract.View {
    
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private lateinit var presenter: FirstFragmentContract.Presenter
    private lateinit var model: FirstFragmentContract.Model
    private var recyclerView: RecyclerView? = null
    private lateinit var adapter: ItemRecycleAdapter
    private val items: MutableList<Item> = mutableListOf()
    private val typeDialog = R.string.typeDialog
    private val nameSP = R.string.nameSP
    private var getContent = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { handleFileUri(it) }
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initModel()
        initAdapterRV()
        currentLang()
        initPresenter()
        setClickListeners()
        
    }
    
    private fun initModel() {
        val context = this.context
        if (context != null) {
            val prefs = context.getSharedPreferences(getString(nameSP), Context.MODE_PRIVATE)
            model = FileNameModel(prefs)
        } else {
            //showToast("Все сломалось")
            // close
        }
    }
    
    private fun currentLang() {
        Locale.getDefault().displayLanguage
    }
    
    private fun initAdapterRV() {
        recyclerView = view?.findViewById(R.id.filesRV)
        adapter = ItemRecycleAdapter(model)
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = LinearLayoutManager(context)
        
    }
    
    private fun initPresenter() {
        presenter = FirstFragmentPresenter(this, model)
    }
    
    private fun setClickListeners() {
        binding.fileButton1?.setOnClickListener {
            presenter.onFindFIleButtonClicked()
        }
        binding.button2?.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }
    
    override fun onStart() {
        super.onStart()
        requireActivity().title = getString(R.string.fTitle_name)
        presenter.onScreenOpened()
    }
    
    override fun openFileSelector() {
        getContent.launch(getString(typeDialog))
        
    }
    
    override fun recoveryRV(items: List<Item>) {
    
        //TODO
    }
    
    override fun setFileNameTitle(item: Item) {
        adapter.addItem(item)
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    
    private fun handleFileUri(uri: Uri?) {
        uri?.let { selectedUri ->
            val path = context?.contentResolver
            GetNameFromUri().recyclePath(path, selectedUri)
                .let { presenter.onFileNameSelected(Item(it)) }
        }
    }
    
}
