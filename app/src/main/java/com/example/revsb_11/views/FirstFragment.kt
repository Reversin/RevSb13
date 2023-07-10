package com.example.revsb_11.views


import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
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

    val requestManageStoragePermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            renameFile()

        } else {
            Toast.makeText(
                requireContext().applicationContext, "неа1", Toast.LENGTH_SHORT
            ).show()
        }
    }


    val requestManageAppFilesAccessLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        if (activityResult.resultCode == Activity.RESULT_OK) {
            renameFile()
        } else {
            Toast.makeText(
                requireContext().applicationContext, "неа2", Toast.LENGTH_SHORT
            ).show()
        }
    }

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
        firstPresenter.onScreenOpened()
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
            firstPresenter.onSwipeDeleteItem(item)
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
            if (!argums.isEmpty) {

                renameFile()
            }
        }
    }


    private fun initPresenters() {
        firstPresenter = FirstFragmentPresenter(this, model)
    }

    private fun setClickListeners() {
        binding.fileButton1.setOnClickListener {
            firstPresenter.onFindFileButtonClicked()
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

    override fun renameFile() {
        val manageStoragePermission = Manifest.permission.MANAGE_EXTERNAL_STORAGE

        val settingsIntent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
        val packageUri = Uri.fromParts("package", context?.packageName, null)
        settingsIntent.data = packageUri


        if (ContextCompat.checkSelfPermission(
                requireContext(),
                manageStoragePermission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
        } else {
            if (true && !Environment.isExternalStorageManager()) {
                // Разрешение не было предоставлено, открываем экран настроек
                requestManageAppFilesAccessLauncher.launch(settingsIntent)
            } else {
                val arg1 = args.firstFragmentArgument
                val arg2 = args.firstFragmentArgument2
                val contentResolver = context?.contentResolver
                val dir = context?.cacheDir
                val newPath =
                    contentResolver?.let { WorkingWithFiles().renameFile(it, dir, arg1, arg2) }
                firstPresenter.onFileNameSelected(
                    Data(
                        newPath,
                        "fileSize",
                        "longFileUri.toString()"
                    )
                )

            }
        }

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
