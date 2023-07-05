package com.example.revsb_11.views


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.revsb_11.R
import com.example.revsb_11.adapters.ItemRecycleAdapter
import com.example.revsb_11.contracts.FirstFragmentContract
import com.example.revsb_11.data.Data
import com.example.revsb_11.data.WorkingWithFiles
import com.example.revsb_11.databinding.FragmentFirstBinding
import com.example.revsb_11.models.FileNameModel
import com.example.revsb_11.presenters.FirstFragmentPresenter


class FirstFragment : Fragment(), FirstFragmentContract.View {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private lateinit var firstPresenter: FirstFragmentContract.Presenter
    private lateinit var model: FirstFragmentContract.Model
    private var recyclerView: RecyclerView? = null
    private lateinit var adapter: ItemRecycleAdapter
    private val typeDialog = R.string.typeDialog
    private val nameSP = R.string.nameSP

    //TODO private var requestPermission https://www.youtube.com/watch?v=Gwu_iS-Z48U
    private var getContent = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { handleFileUri(it) }

//    private val requestPermissionLauncher =
//        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
//        isGranted: Boolean ->
//            if (isGranted){
//                openFileSelector()
//            }
//        }

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
                requireContext().applicationContext, "Вась, мы модель уронили", Toast.LENGTH_SHORT
            ).show()
            return
        }
    }

    override fun initAdapterRecycleView(itemsList: List<Data>) {
        recyclerView = view?.findViewById(R.id.recyclerViewFiles)
        adapter = ItemRecycleAdapter(onEditButtonClicked = { item ->
            firstPresenter.onItemClicked(item)
        }, onSwipeToDelete = { item ->
            firstPresenter.swipeDeleteItem(item)
        })
        adapter.setItems(itemsList)
        recyclerView?.let { adapter.attachSwipeToDelet(it) }
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = LinearLayoutManager(context)
    }

    override fun goToFragmentForChanges(data: Data) {
        val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment("${data.fullPath}")
        findNavController().navigate(action)
    }


    private fun initPresenters() {
        firstPresenter = FirstFragmentPresenter(this, model)
    }

    private fun setClickListeners() {
        binding.fileButton1.setOnClickListener {
            firstPresenter.onFindFIleButtonClicked()
        }
    }

    override fun onStart() {
        super.onStart()
        requireActivity().title = getString(R.string.fTitle_name)
    }

    override fun openFileSelector() {
        getContent.launch(getString(typeDialog))
//        val permission = Manifest.permission.READ_EXTERNAL_STORAGE
//        if (context?.let { ContextCompat.checkSelfPermission(it, permission) } == PackageManager.PERMISSION_GRANTED) {
//            getContent.launch(getString(typeDialog))
//        } else {
//            // Запрашиваем разрешение на чтение внешнего хранилища
//            requestPermissionLauncher.launch(permission)
//        }
    }


    override fun setFileNameTitle(itemsList: List<Data>) =
        adapter.setItems(itemsList)


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleFileUri(uri: Uri?) {
        uri?.let { selectedUri ->
            val contentResolver = context?.contentResolver
            val filepath = selectedUri.path
            val fileSize = filepath?.let {
                WorkingWithFiles().filePathHandlingSize(
                    contentResolver, selectedUri
                )
            }
            firstPresenter.onFileNameSelected(Data(filepath, fileSize, selectedUri.toString()))
        }
    }

}
