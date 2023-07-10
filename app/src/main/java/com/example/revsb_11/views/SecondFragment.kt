package com.example.revsb_11.views


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.navigation.fragment.findNavController
import com.example.revsb_11.R
import com.example.revsb_11.contracts.SecondFragmentContract
import com.example.revsb_11.data.WorkingWithFiles
import com.example.revsb_11.databinding.FragmentSecondBinding
import com.example.revsb_11.presenters.SecondFragmentPresenter


class SecondFragment : Fragment(), SecondFragmentContract.View {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private lateinit var secondPresenter: SecondFragmentContract.Presenter
    private val args: SecondFragmentArgs by navArgs()
    private val dot = "."


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
        secondPresenter.secondFragmentInitialized()
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
            secondPresenter.onSaveButtonClicked()
        }
    }

    private fun editTextListener() {
        binding.savingTheChangedNameButton.isEnabled = false
        binding.savingTheChangedNameButton.alpha = 0.5F

        binding.editFileNameText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val hasChanges = s?.isNotBlank() ?: false
                binding.savingTheChangedNameButton.isEnabled = hasChanges
                binding.savingTheChangedNameButton.alpha = if (hasChanges) 1.0F else 0.5f
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    override fun backToThePreviousFragment(): Boolean =
        findNavController().popBackStack()

    override fun backToThePreviousFragmentWithChanges() {
        val action = SecondFragmentDirections.actionSecondFragmentToFirstFragment(
            args.secondFragmentArgument,
            "${binding.editFileNameText.text}.jpg"
        )
        findNavController().navigate(action)
    }

    override fun showAlertDialog() {
        context?.let {
            AlertDialog.Builder(it)
                .setTitle(R.string.change_file_name)
                .setMessage(R.string.change_file_name)
                .setPositiveButton(R.string.yes) { dialog, _ ->
                    secondPresenter.onConsentSaveButton()
                    dialog.dismiss()
                }
                .setNegativeButton(R.string.no) { dialog, _ ->
                    dialog.dismiss()
                }
                .setCancelable(false)
                .create()
                .show()
        }
    }


    override fun onStart() {
        super.onStart()
        requireActivity().title = activity?.getString(R.string.sTitle_name)
    }

    override fun setText() {
        val fileUri = args.secondFragmentArgument.toUri()
        val file: Pair<String, String>?
        fileUri.let { selectedUri ->
            val contentResolver = context?.contentResolver
            file = contentResolver?.let { WorkingWithFiles().getFileNameFromUri(it, selectedUri) }

        }

        val fileFormat = ".${file?.second?.substringAfter("/")}"
        val indexFormat = file?.first?.lastIndexOf(dot)
        val fileName = indexFormat?.let { file.first.substring(0, it) }

        binding.editFileNameText.setText(fileName)
        binding.fileFormatTextView.text = fileFormat

    }

}