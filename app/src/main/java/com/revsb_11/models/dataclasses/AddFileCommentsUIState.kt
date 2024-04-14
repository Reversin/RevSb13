package com.revsb_11.models.dataclasses

data class AddFileCommentsUIState(
    val selectedFile: SelectedFile? = null,
    val isSavingChangesButtonEnabled: Boolean = false,
    val changedComment: String = "",
)