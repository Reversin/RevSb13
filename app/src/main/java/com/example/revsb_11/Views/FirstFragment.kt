package com.example.revsb_11.Views


import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.example.revsb_11.Contracts.GetActionContract
import com.example.revsb_11.Models.FileNameModel
import com.example.revsb_11.Presenters.FirstFragmentPresenter
import com.example.revsb_11.R
import com.example.revsb_11.databinding.FragmentFirstBinding
import com.google.android.material.snackbar.Snackbar
import java.util.Locale


class FirstFragment : Fragment(), GetActionContract.View {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private lateinit var presenter: GetActionContract.Presenter
    private lateinit var model: GetActionContract.Model
    private lateinit var currentLang: String
    private lateinit var myString: String

    private var getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            val fileName: String? = uri?.let { uri ->
                context?.contentResolver?.query(uri, null, null, null, null)?.use { cursor ->
                    val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    cursor.moveToFirst()
                    cursor.getString(nameIndex)
                }
            }
            if (fileName != null) {
                presenter.processFileName(fileName)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }


    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val title = context?.getString(R.string.title_name).toString()
        this.activity?.title = title

        currentLang = Locale.getDefault().displayLanguage
        val infoSnackbar = view.let { Snackbar.make(it, "Click button", Snackbar.LENGTH_LONG) }
        infoSnackbar.show()

        val prefs = context?.getSharedPreferences("fname_prefs", Context.MODE_PRIVATE)

        model = prefs?.let { FileNameModel(it) }!!
        myString = context?.getString(R.string.last_file_name).toString()

        presenter = FirstFragmentPresenter(this, model)
        view.findViewById<LottieAnimationView?>(R.id.lottieButton_1)?.setOnClickListener {
            presenter.buttonFileDialogReaction()
        }
        val languageButton = view.findViewById<Switch>(R.id.language_switch)
        languageButton?.setOnClickListener {
            if (languageButton.isChecked) {
                setLocale("en")

            } else {
                setLocale("ru")
            }
        }

    }

    override fun onStart() {
        super.onStart()
        presenter.recoveryLastFileName()
    }

    override fun takeFileNameDialog() {
        getContent.launch("*/*")

    }

    override fun editTVFileName(text: String, key: Boolean) {


        if (key) {
            binding.tvFileName.text = buildString {
                append(myString)
                append(text)
            }
            val lottieFileFounded: LottieAnimationView? = view?.findViewById(R.id.lottieButton_1)
            if (lottieFileFounded != null) {
                lottieFileFounded.speed = 2f
                lottieFileFounded.playAnimation()
            }
        }
    }

    override fun setLocale(langKey: String) {
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(langKey))
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
//class FirstFragmentFiles: Fragment(), GetActionContract.Files{
//
//    private lateinit var getContentLauncher: ActivityResultLauncher<Intent>
//    private var selectedFile: Uri? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        getContentLauncher =
//            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//                if (result.resultCode == RESULT_OK) {
//                    selectedFile = result.data?.data
//                }
//            }
//
//    }
//
//    @SuppressLint("Range")
//    override fun takeFileNameDialog(firstFragmentPresenter: FirstFragmentPresenter) {
//        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
//            type = "*/*"
//        }
//        getContentLauncher.launch(Intent.createChooser(intent, "Select a file"))
//        selectedFile?.let { uri ->
//            val cursor = requireContext().contentResolver.query(uri, null, null, null, null)
//            val fileName = cursor?.use {
//                it.moveToFirst()
//                it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
//            }
//            cursor?.close()
//            if (fileName != null) {
//                firstFragmentPresenter.processFileName(fileName)
//            }
//        }
//    }
//}
// Call openFilePicker() to launch the file picker dialog


//        val intent = Intent()
//            .setType("*/*")
//            .setAction(Intent.ACTION_GET_CONTENT)
//            startActivityForResult(intent, 1) // TODO FragmentResultApi
//    }
