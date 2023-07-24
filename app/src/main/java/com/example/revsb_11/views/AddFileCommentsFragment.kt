package com.example.revsb_11.views


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.navigation.fragment.findNavController
import com.example.revsb_11.R
import com.example.revsb_11.data.NewFileComment
import com.example.revsb_11.data.WorkingWithFiles
import com.example.revsb_11.databinding.AddFileCommentsFragmentBinding
import com.example.revsb_11.viewmodels.AddFileCommentsViewModel
import com.example.revsb_11.viewmodelsfactories.AddFileCommentsViewModelFactory


class AddFileCommentsFragment : Fragment() {

    private var _binding: AddFileCommentsFragmentBinding? = null
    private val binding get() = _binding!!
    private val args: AddFileCommentsFragmentArgs by navArgs()
    private lateinit var viewModel: AddFileCommentsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = AddFileCommentsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onScreenOpened()
        checkChangingValuesListeners()
        setClickListeners()
        editTextListener()
        setTitle()
    }

    private fun onScreenOpened() {
        val workingWithFiles = WorkingWithFiles(requireActivity().contentResolver)
        viewModel = ViewModelProvider(
            this,
            AddFileCommentsViewModelFactory(workingWithFiles)
        )[AddFileCommentsViewModel::class.java]
        viewModel.initViewModel(args.addFileCommentsArgument, args.addFileCommentsArgument2)
    }

    private fun checkChangingValuesListeners() {
        viewModel.fileNameLiveData.observe(viewLifecycleOwner) { fileName ->
            setFileNameTextView(fileName)
        }
        viewModel.fileCommentLiveData.observe(viewLifecycleOwner) { fileComment ->
            setFileComment(fileComment)
        }
        viewModel.fileImageLiveData.observe(viewLifecycleOwner) { fileImage ->
            setBitmapImageInImageView(fileImage)
        }
        viewModel.isSavingChangesButtonLiveData.observe(viewLifecycleOwner) { isButtonEnabled ->
            if (isButtonEnabled) enableSaveButton()
            else disableSaveButton()
        }
        viewModel.showConfirmationDialogLiveData.observe(viewLifecycleOwner) {
            showConfirmationOfTheChangesDialog()
        }
        viewModel.returnToPreviousScreenLiveData.observe(viewLifecycleOwner) { returnToPreviousScreen ->
            backToThePreviousFragmentWithChanges(returnToPreviousScreen)
        }
    }

    private fun setClickListeners() {
        binding.savingTheChangedNameButton.setOnClickListener {
            viewModel.onSaveButtonClicked()
        }
    }

    private fun setTitle() {
        requireActivity().title = activity?.getString(R.string.commentTitle_name)
    }

    private fun disableSaveButton() {
        binding.savingTheChangedNameButton.isEnabled = false
        binding.savingTheChangedNameButton.alpha = 0.5F
    }

    private fun enableSaveButton() {
        binding.savingTheChangedNameButton.isEnabled = true
        binding.savingTheChangedNameButton.alpha = 1.0F
    }

    private fun editTextListener() {
        binding.addFileCommentText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?, start: Int, count: Int, after: Int
            ) {/*Not used*/
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onTextHasBeenChanged(binding.addFileCommentText.text.toString())
            }

            override fun afterTextChanged(s: Editable?) {/*Not used*/
            }
        })
    }

    private fun backToThePreviousFragmentWithChanges(newFile: NewFileComment) {
        val action =
            AddFileCommentsFragmentDirections.actionAddFileCommentsFragmentToSelectedFilesFragment(
                newFile.originalFileUri, newFile.newFileComment
            )
        findNavController().navigate(action)
    }

    private fun showConfirmationOfTheChangesDialog() {
        context?.let {
            AlertDialog.Builder(it).setTitle(R.string.change_file_name)
                .setMessage(R.string.alert_message).setPositiveButton(R.string.yes) { dialog, _ ->
                    viewModel.onConsentSaveButtonClicked()
                    dialog.dismiss()
                }.setNegativeButton(R.string.no) { dialog, _ ->
                    dialog.dismiss()
                }.setCancelable(false).create().show()
        }
    }

    private fun setFileNameTextView(fileName: String?) {
        binding.fileNameTextView.text = fileName
    }

    private fun setFileComment(fileComments: String) {
        binding.addFileCommentText.setText(fileComments)
    }

    fun setDrawableImageInImageView(drawable: Drawable?) =
        binding.imageView.setImageDrawable(drawable)

    private fun setBitmapImageInImageView(bitmap: Bitmap?) =
        binding.imageView.setImageBitmap(bitmap)
}