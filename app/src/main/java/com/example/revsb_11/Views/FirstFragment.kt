package com.example.revsb_11.Views


import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.revsb_11.Contracts.FirstFragmentContract
import com.example.revsb_11.Data.getNameFromUri
import com.example.revsb_11.Models.FileNameModel
import com.example.revsb_11.Presenters.FirstFragmentPresenter
import com.example.revsb_11.R
import com.example.revsb_11.databinding.FragmentFirstBinding
import java.util.Locale


class FirstFragment() : Fragment(), FirstFragmentContract.View {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private lateinit var presenter: FirstFragmentContract.Presenter
    private lateinit var model: FirstFragmentContract.Model
    private val typeDialog = "*/*"
    private val nameSP = "fname_prefs"


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

        val activity = this.activity
        if (activity != null) {
            val title = activity.getString(R.string.title_name)
            activity.title = title
        }

        initModel()
        currentLang()
        initPresenter()
        setClickListeners()

    }

    private fun initModel() {
        val prefs = context?.getSharedPreferences(nameSP, Context.MODE_PRIVATE)
        model = FileNameModel(prefs)
    }

    private fun currentLang() {
        Locale.getDefault().displayLanguage
    }

    private fun initPresenter() {
        presenter = FirstFragmentPresenter(this, model)
    }


    fun setClickListeners() {
        binding.fileButton1?.setOnClickListener {
            presenter.onFindFIleButtonClicked()
        }
        binding.button2?.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onStart() {
        super.onStart()
        presenter.onScreenOpened()
    }

    override fun openFileSelector() {
        getContent.launch(typeDialog)

    }

    override fun setFileNameTitle(filePath: String) {
        binding.tvFileName.text = getString(R.string.last_file, filePath)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleFileUri(uri: Uri?) {
        uri?.let { selectedUri ->
            val path = context?.contentResolver
            getNameFromUri().recyclePath(path, selectedUri)
                ?.let { presenter.onFileNameSelected(it) }
        }
    }

}
