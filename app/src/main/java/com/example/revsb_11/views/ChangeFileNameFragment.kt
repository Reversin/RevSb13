package com.example.revsb_11.views


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.navigation.fragment.findNavController
import com.example.revsb_11.R
import com.example.revsb_11.contracts.ChangeFileNametContract
import com.example.revsb_11.databinding.ChangeFileNameFragmentBinding
import com.example.revsb_11.presenters.ChangeFileNamePresenter


class ChangeFileNameFragment : Fragment(), ChangeFileNametContract.View {

    private var _binding: ChangeFileNameFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var changeFileNamePresenter: ChangeFileNametContract.Presenter
    private val args: ChangeFileNameFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ChangeFileNameFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initPresenters()
        changeFileNamePresenter.onScreenOpened(args.changeFileNameArgument)
        setClickListeners()
        editTextListener()
        setTitle()
    }


    private fun initPresenters() {
        val contentResolver = context?.contentResolver
        changeFileNamePresenter = ChangeFileNamePresenter(this, contentResolver)
    }

    private fun setClickListeners() {
        binding.savingTheChangedNameButton.setOnClickListener {
            changeFileNamePresenter.onSaveButtonClicked()
        }
    }

    private fun setTitle() {
        requireActivity().title = activity?.getString(R.string.sTitle_name)
    }

    override fun disableSaveButton() {
        binding.savingTheChangedNameButton.isEnabled = false
        binding.savingTheChangedNameButton.alpha = 0.5F
    }

    override fun enableSaveButton() {
        binding.savingTheChangedNameButton.isEnabled = true
        binding.savingTheChangedNameButton.alpha = 1.0F
    }

    private fun editTextListener() {

        binding.editFileNameText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {/*Not used*/
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                changeFileNamePresenter.textHasBeenChanged(binding.editFileNameText.text.toString())
            }

            override fun afterTextChanged(s: Editable?) {/*Not used*/
            }
        })
    }

    override fun backToThePreviousFragmentWithChanges(originalFile: String, fileName: String) {
        val action =
            ChangeFileNameFragmentDirections.actionChangeFileNameFragmentToSelectedFilesFragment(
                originalFile,
                fileName
            )
        findNavController().navigate(action)
    }

    override fun showAlertDialog() {
        context?.let {
            AlertDialog.Builder(it)
                .setTitle(R.string.change_file_name)
                .setMessage(R.string.alert_message)
                .setPositiveButton(R.string.yes) { dialog, _ ->
                    changeFileNamePresenter.onConsentSaveButtonClicked(
                        binding.editFileNameText.text.toString()
                    )
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

    override fun setText(fileName: String, fileFormat: String) {
        binding.editFileNameText.setText(fileName)
        binding.fileFormatTextView.text = fileFormat
    }


}