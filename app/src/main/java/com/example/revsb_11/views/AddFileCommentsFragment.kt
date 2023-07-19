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
import androidx.navigation.fragment.navArgs
import androidx.navigation.fragment.findNavController
import com.example.revsb_11.R
import com.example.revsb_11.contracts.AddFileCommentsContract
import com.example.revsb_11.data.WorkingWithFiles
import com.example.revsb_11.databinding.AddFileCommentsFragmentBinding
import com.example.revsb_11.viewmodels.AddFileCommentsViewModel


class AddFileCommentsFragment : Fragment(), AddFileCommentsContract.View {

    private var _binding: AddFileCommentsFragmentBinding? = null
    private val binding get() = _binding!!
    private val args: AddFileCommentsFragmentArgs by navArgs()
    private val viewModel: AddFileCommentsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = AddFileCommentsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changingValuesListeners()
        onScreenOpened()
        setClickListeners()
        editTextListener()
        setTitle()
    }

    private fun changingValuesListeners() {
        viewModel.originalFile.observe(viewLifecycleOwner) { filePath ->
            val fileName = context?.let {
                WorkingWithFiles().getFileNameFromUri(
                    it.contentResolver,
                    filePath.toUri()
                )
            }
            setFileNameHint(fileName)
            viewModel.saveFileImage(getBitmapImageFromUri(filePath.toUri()))

        }
        viewModel.fileComment.observe(viewLifecycleOwner) { fileComment ->
            setFileComment(fileComment)
        }
        viewModel.fileImage.observe(viewLifecycleOwner) { fileImage ->
            setBitmapImageInImageView(fileImage)
        }
        viewModel.isButtonEnabled.observe(viewLifecycleOwner) { isButtonEnabled ->
            if (isButtonEnabled) enableSaveButton()
            else disableSaveButton()
        }
        viewModel.showConfirmationDialog.observe(viewLifecycleOwner) {
            showConfirmationOfTheChangesDialog()
        }
        viewModel.saveChangedComment.observe(viewLifecycleOwner) {
            backToThePreviousFragmentWithChanges()
        }
    }

    private fun onScreenOpened() {
        viewModel.processFirstArgument(args.addFileCommentsArgument)
        viewModel.processSecondArgument(args.addFileCommentsArgument2)
    }

    private fun setClickListeners() {
        binding.savingTheChangedNameButton.setOnClickListener {
            viewModel.onSaveButtonClicked()
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

    override fun backToThePreviousFragmentWithChanges() {
        val action =
            AddFileCommentsFragmentDirections.actionAddFileCommentsFragmentToSelectedFilesFragment(
                args.addFileCommentsArgument, binding.addFileCommentText.text.toString()
            )
        findNavController().navigate(action)
    }

    override fun showConfirmationOfTheChangesDialog() {
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

    override fun processingLinkToFile(fileUri: Uri): String? =
        context?.let { WorkingWithFiles().getFileNameFromUri(it.contentResolver, fileUri) }

    override fun processingImageFile(fileUri: Uri): Bitmap =
        context?.contentResolver?.openInputStream(fileUri)
            .use { inputStream -> BitmapFactory.decodeStream(inputStream) }

    override fun setFileNameHint(fileName: String?) {
        binding.fileCommentsInputLayout.hint = fileName
    }

    override fun getBitmapImageFromUri(fileUri: Uri): Bitmap =
        context?.contentResolver?.openInputStream(fileUri).use { inputStream ->
            BitmapFactory.decodeStream(inputStream)
        }


    override fun setFileComment(fileComments: String) {
        binding.addFileCommentText.setText(fileComments)
    }

    override fun setDrawableImageInImageView(drawable: Drawable?) =
        binding.imageView.setImageDrawable(drawable)

    override fun setBitmapImageInImageView(bitmap: Bitmap?) =
        binding.imageView.setImageBitmap(bitmap)
}