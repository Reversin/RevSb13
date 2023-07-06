package com.example.revsb_11.views


import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.text.InputFilter
import android.text.Spanned
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.navigation.fragment.findNavController
import com.example.revsb_11.R
import com.example.revsb_11.contracts.SecondFragmentContract
import com.example.revsb_11.data.WorkingWithFiles
import com.example.revsb_11.databinding.FragmentSecondBinding
import com.example.revsb_11.presenters.SecondFragmentPresenter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import java.io.File


class SecondFragment : Fragment(), SecondFragmentContract.View {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private lateinit var secondPresenter: SecondFragmentContract.Presenter
    private val args: SecondFragmentArgs by navArgs()




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initPresenters()

        secondPresenter.secondFragmentInitialized(args)
        setClickListeners()
        editTextListener()
    }



    private fun initPresenters() {
        secondPresenter = SecondFragmentPresenter(this)
    }

    private fun setClickListeners() {
        binding.backArrow.setOnClickListener {
            secondPresenter.onBackArrowClicked()
        }
        binding.savingTheChangedNameButton.setOnClickListener {

        }
    }

    private fun editTextListener() {

        binding.savingTheChangedNameButton.isEnabled = false
        binding.savingTheChangedNameButton.alpha = 0.5F

        binding.editFileNameText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {


            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val currentText = s.toString()
                val dotIndex = currentText.lastIndexOf(".")
                if (dotIndex >= 0 && dotIndex < currentText.length - 1) {
                    // Разделяем текст на имя файла и расширение
                    val fileName = currentText.substring(0, dotIndex)
                    val fileExtension = currentText.substring(dotIndex)

                    // Выполняем нужные операции с именем файла (fileName), кроме формата (fileExtension)
                    // Например, обновление других элементов пользовательского интерфейса

                    // Формируем новый текст с обновленным именем файла и оставляем формат без изменений
                    val newText = "$fileName$fileExtension"

                    // Обновляем текст в EditText без вызова onTextChanged()
                    binding.editFileNameText.removeTextChangedListener(this)
                    binding.editFileNameText.setText(newText)
                    binding.editFileNameText.setSelection(newText.length)
                    binding.editFileNameText.addTextChangedListener(this)
                }
                val hasChanges = s?.isNotBlank() ?: false
                binding.savingTheChangedNameButton.isEnabled = hasChanges
                binding.savingTheChangedNameButton.alpha = if (hasChanges) 1.0F else 0.5f
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    override fun backToThePreviousFragment() =
        findNavController().popBackStack()

    override fun onTextChanged() {
        TODO("Not yet implemented")
    }


    override fun onStart() {
        super.onStart()
        requireActivity().title = activity?.getString(R.string.sTitle_name)
    }

    override fun setText(dataFragmentArgument: SecondFragmentArgs) {
        val filePath = dataFragmentArgument.secondFragmentArgument.toUri()
        val fileName: String?
        filePath.let { selectedUri ->
            val contentResolver = context?.contentResolver
            fileName = contentResolver?.let { WorkingWithFiles().getFileNameFromUri(it, selectedUri) }
        }
        binding.editFileNameText.setText(fileName)

    }
    override fun onStop() {
        super.onStop()
        binding.editFileNameText.text.clear()
    }
}