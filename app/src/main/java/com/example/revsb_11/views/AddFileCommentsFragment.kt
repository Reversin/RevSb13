package com.example.revsb_11.views


import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.navigation.fragment.findNavController
import com.example.revsb_11.R
import com.example.revsb_11.dataclasses.NewFileComment
import com.example.revsb_11.databinding.AddFileCommentsFragmentBinding
import com.example.revsb_11.extensions.onTextChanged
import com.example.revsb_11.viewmodels.AddFileCommentsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class AddFileCommentsFragment : Fragment() {

    private var _binding: AddFileCommentsFragmentBinding? = null
    private val binding get() = _binding!!
    private val args: AddFileCommentsFragmentArgs by navArgs()
    private val viewModel: AddFileCommentsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = AddFileCommentsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onScreenOpened()
        observeScreenState()
        setClickListeners()
        editTextListener()
        setTitle()
    }

    private fun onScreenOpened() {
        viewModel.initViewModel(args.originalFileUri, args.fileComments)
    }

    private fun observeScreenState() {
        viewModel.screenState.fileNameLiveData.observe(viewLifecycleOwner) { fileName ->
            setFileNameTextView(fileName)
        }
        viewModel.screenState.fileImageLiveData.observe(viewLifecycleOwner) { fileImage ->
            setBitmapImageInImageView(fileImage)
        }
        viewModel.screenState.fileIconResourcesLiveData.observe(viewLifecycleOwner) { fileIconResources ->
            setDefaultFileIconInImageView(fileIconResources)
        }
        viewModel.screenState.fileCommentLiveData.observe(viewLifecycleOwner) { fileComment ->
            setFileComment(fileComment)
        }
        viewModel.screenState.fileImageLiveData.observe(viewLifecycleOwner) { fileImage ->
            setBitmapImageInImageView(fileImage)
        }
        viewModel.screenState._isSavingChangesButtonEnabledLiveData.observe(viewLifecycleOwner) { isButtonEnabled ->
            if (isButtonEnabled) {
                enableSaveButton()
            } else {
                disableSaveButton()
            }
        }
        viewModel.screenState.showConfirmationDialogLiveData.observe(viewLifecycleOwner) {
            showConfirmationOfTheChangesDialog()
        }
        viewModel.screenState.returnToPreviousScreenLiveData.observe(viewLifecycleOwner) { returnToPreviousScreen ->
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
        binding.addFileCommentText.onTextChanged { newText ->
            viewModel.onTextHasBeenChanged(newText)
        }
    }

    private fun backToThePreviousFragmentWithChanges(newFile: NewFileComment) {
        val action =
            AddFileCommentsFragmentDirections.actionAddFileCommentsFragmentToSelectedFilesFragment(
                newFile.originalFileUri, newFile.newFileComment
            )
        findNavController().navigate(action)
    }

    private fun showConfirmationOfTheChangesDialog() {
        context?.let { context ->
            AlertDialog.Builder(context).setTitle(R.string.change_file_name)
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

    fun setDefaultFileIconInImageView(iconResourceId: Int) =
        binding.imageView.setImageResource(iconResourceId)

    private fun setBitmapImageInImageView(bitmap: Bitmap?) =
        binding.imageView.setImageBitmap(bitmap)
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}