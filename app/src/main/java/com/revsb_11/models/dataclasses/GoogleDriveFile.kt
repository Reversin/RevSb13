package com.revsb_11.models.dataclasses

data class GoogleDriveFile(
    val id: String,
    val name: String,
    val mimeType: String,
    val size: Long,
    val createdTime: String,
    val modifiedTime: String,
    val owners: List<Owner>,
    val shared: Boolean,
    val webViewLink: String,
    val webContentLink: String?,
    val parents: List<String>?,
    val description: String?,
    val trashed: Boolean
) {
    data class Owner(
        val emailAddress: String,
        val displayName: String
    )
}