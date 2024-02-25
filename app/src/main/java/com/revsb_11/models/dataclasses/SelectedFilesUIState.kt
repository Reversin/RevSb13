package com.revsb_11.models.dataclasses

import com.revsb_11.utils.Event

data class SelectedFilesUIState(
    val savedSelectedFilesList: List<SelectedFile>? = null,
    val onFindFileButtonClicked: Event<Unit>? = null,
    val selectedFile: SelectedFile? = null,
)
