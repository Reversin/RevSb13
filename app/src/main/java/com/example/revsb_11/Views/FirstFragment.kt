package com.example.revsb_11.Views


import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.revsb_11.Contracts.GetActionContract
import com.example.revsb_11.Models.FileNameModel
import com.example.revsb_11.Presenters.FirstFragmentPresenter
import com.example.revsb_11.R
import com.example.revsb_11.databinding.FragmentFirstBinding
import java.io.File
import java.util.Locale


class FirstFragment : Fragment(), GetActionContract.FirstFragmentView {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private lateinit var presenter: GetActionContract.FirstFragmentPresenter
    private lateinit var model: GetActionContract.Model
    private lateinit var currentLang: String
    private lateinit var myString: String


    private var getContent = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { handleFileUri(it) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title = context?.getString(R.string.title_name).toString()
        this.activity?.title = title
        currentLang = Locale.getDefault().displayLanguage

        val prefs = context?.getSharedPreferences("fname_prefs", Context.MODE_PRIVATE)

        model = prefs?.let { FileNameModel(it) }!!
        myString = context?.getString(R.string.last_file).toString()

        presenter = FirstFragmentPresenter(this, model)

        view.findViewById<Button>(R.id.fileButton_1).setOnClickListener {
            presenter.onBlaBlaButtonClicked()
        }

        view.findViewById<Button>(R.id.button2).setOnClickListener{
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

    }

    override fun onStart() {
        super.onStart()

        presenter.onScreenOpened()
    }

    override fun openFileSelector() {
        getContent.launch("*/*")

    }

    override fun setFileNameTitle(fileName: String) {
        binding.tvFileName.text = buildString {
            append(myString)
            append(fileName)
        }

    }

    override fun setLocale(langKey: String) {
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(langKey))
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleFileUri(uri: Uri?) {
        uri?.let { selectedUri ->
            val path = context?.contentResolver?.query(selectedUri, null, null, null, null)
                ?.use { cursor ->
                    if (cursor.moveToFirst()) {
                        val pathIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                        val fileName = cursor.getString(pathIndex)
                        val file = selectedUri.path?.let { File(it) }
                        val fullPath = file?.absolutePath
                        return@use "$fullPath/$fileName"
                    }
                    null
                }
            if (path != null) {
                presenter.onFileNameSelected(path)
            }

        }
    }

}
