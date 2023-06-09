package com.example.revsb_11.views


import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.revsb_11.R
import com.example.revsb_11.adapters.ItemRecycleAdapter
import com.example.revsb_11.contracts.FirstFragmentContract
import com.example.revsb_11.data.GetNameFromUri
import com.example.revsb_11.data.Item
import com.example.revsb_11.databinding.FragmentFirstBinding
import com.example.revsb_11.models.FileNameModel
import com.example.revsb_11.presenters.FirstFragmentPresenter
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.attribute.BasicFileAttributes
import java.text.SimpleDateFormat
import java.util.Locale


class FirstFragment : Fragment(), FirstFragmentContract.View {
    
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private lateinit var firstPresenter: FirstFragmentContract.Presenter
    private lateinit var model: FirstFragmentContract.Model
    private var recyclerView: RecyclerView? = null
    private lateinit var adapter: ItemRecycleAdapter
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
        initPresenters()
        firstPresenter.modelInitialized()
        setClickListeners()
        
    }
    
    private fun initModel() {
        val context = this.context
        if (context != null) {
            val prefs = context.getSharedPreferences(getString(nameSP), Context.MODE_PRIVATE)
            model = FileNameModel(prefs)
        } else {
            Toast.makeText(
                requireContext().applicationContext,
                "Вась, мы модель уронили",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
    }
    
    override fun initAdapterRecycleView(itemsList: List<Item>) {
        recyclerView = view?.findViewById(R.id.recyclerViewFiles)
        adapter = ItemRecycleAdapter { item ->
            firstPresenter.onItemClicked(item)
        }
        adapter.setItems(itemsList)
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = LinearLayoutManager(context)
        
    }

//    override fun onItemClick() =
//        firstPresenter.onItemClicked()
    
    override fun changeFragment(item: Item) {
        val bundle = Bundle()
        bundle.putString("1", item.fileName)
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
    }
    
    
    
    private fun initPresenters() {
        firstPresenter = FirstFragmentPresenter(this, model)
//        secondPresenter
        
    }
    
    private fun setClickListeners() {
        binding.fileButton1?.setOnClickListener {
            firstPresenter.onFindFIleButtonClicked()
        }
    }
    
    override fun onStart() {
        super.onStart()
        requireActivity().title = getString(R.string.fTitle_name)
    }
    
    override fun openFileSelector() =
        getContent.launch(getString(typeDialog))
    
    
    override fun setFileNameTitle(itemsList: List<Item>) =
        adapter.setItems(itemsList)
    
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    
    private fun handleFileUri(uri: Uri?) {
        uri?.let { selectedUri ->
            val contentResolver = context?.contentResolver
            val filepath = selectedUri.path
            val fileCreationDate = GetNameFromUri().recyclePath(contentResolver, selectedUri)
            val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
            val creationDate: String = dateFormat.format(File(filepath).lastModified())

            firstPresenter.onFileNameSelected(Item(filepath, fileCreationDate))
//            GetNameFromUri().recyclePath(path, selectedUri)
//                .let { firstPresenter.onFileNameSelected(Item(it)) }
        }
    }
    
}
