package com.example.revsb_11.views


import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
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
    private val args: FirstFragmentArgs by navArgs()


    private var getContent = registerForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { handleFileUri(it) }

//    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
//    private var requestPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
//        if(it) {
//            Toast.makeText(
//                requireContext().applicationContext, "requestPermission if 1", Toast.LENGTH_SHORT
//            ).show()
//        }else{
//            if(ActivityCompat.shouldShowRequestPermissionRationale(this.requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
//                this.context?.let { it1 ->
//                    MaterialAlertDialogBuilder(it1)
//                        .setTitle("Permission Required")
//                        .setMessage("Жопа чё")
//                        .setNegativeButton("No"){ d, _ ->
//                            d.dismiss()
//                        }.setPositiveButton("Ask Permission Again"){ d, _ ->
//                            requestStoragePermission()
//                            d.dismiss()
//                        }
//                }
//
//            }else{
//                Snackbar.make(binding.root, "Permission Required For App Func", Snackbar.LENGTH_LONG)
//                    .setAction("Settings") {
//                        val intent = Intent()
//                        intent.data = Uri.fromParts("package", "com.example.revsb_11", null)
//                        intent.action = Uri.decode(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
//                        startActivity(intent)
//                    }
//            }
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initModel()
        initPresenters()
//        if(this.context?.let { ActivityCompat.checkSelfPermission(it, Manifest.permission.READ_MEDIA_IMAGES) } == PackageManager.PERMISSION_GRANTED){
//            Toast.makeText(
//                requireContext().applicationContext, "вроде да", Toast.LENGTH_SHORT
//            ).show()
//        }else{
//            Toast.makeText(
//                requireContext().applicationContext, "неа", Toast.LENGTH_SHORT
//            ).show()
//            requestStoragePermission()
//        }
        firstPresenter.modelInitialized()
        navigationListener()
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
        recyclerView?.let { adapter.attachSwipeToDelete(it) }
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = LinearLayoutManager(context)
    }

    override fun goToFragmentForChanges(data: Data) {
        val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment("${data.fullPath}")
        findNavController().navigate(action)
    }

    private fun navigationListener() {
        val argums = arguments
        if (argums != null) {
            if (!argums.isEmpty){
                val arg1 = args.firstFragmentArgument
                firstPresenter.onFileNameSelected(Data("filepath", "fileSize", "longFileUri.toString()"))
            }
        }
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

    override fun openFileSelector() =
        getContent.launch(arrayOf(getString(typeDialog)))

    override fun setFileNameTitle(itemsList: List<Data>) =
        adapter.setItems(itemsList)


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun handleFileUri(uri: Uri?) {
        uri?.let { selectedUri ->
            val contentResolver = context?.contentResolver
            val longFileUri = WorkingWithFiles().grantUriPermissions(contentResolver, selectedUri)
            val filepath = selectedUri.path
            val fileSize = filepath?.let {
                WorkingWithFiles().filePathHandlingSize(
                    contentResolver, longFileUri
                )
            }
            firstPresenter.onFileNameSelected(Data(filepath, fileSize, longFileUri.toString()))
        }
    }

}
