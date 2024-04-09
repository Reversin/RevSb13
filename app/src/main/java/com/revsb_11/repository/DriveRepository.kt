package com.revsb_11.repository

import android.content.Context
import android.text.format.Formatter.formatFileSize
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import com.google.api.services.drive.model.File
import com.google.api.services.drive.model.FileList
import com.revsb_11.models.dataclasses.SelectedFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DriveRepository(private val context: Context) {

    private var googleAccount: GoogleSignInAccount? = null
    private var _pageToken: String? = null

private val googleSignInClient: GoogleSignInClient by lazy {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestScopes(Scope(DriveScopes.DRIVE))
            .build()
        GoogleSignIn.getClient(context, gso)
    }

    fun authenticateUser(): GoogleSignInClient {
        return googleSignInClient
    }

    fun isDriveAccessGranted(): Boolean {
        return googleAccount?.grantedScopes?.contains(Scope(DriveScopes.DRIVE)) == true
    }

    fun setGoogleAccount(googleAccount: GoogleSignInAccount?) {
        this.googleAccount = googleAccount
    }

    fun restoreGoogleAccount() =
        setGoogleAccount(GoogleSignIn.getLastSignedInAccount(context))

    private fun getDriveService(): Drive {
        val credential = GoogleAccountCredential.usingOAuth2(
            context, listOf(DriveScopes.DRIVE_FILE)
        )
        credential.selectedAccount = googleAccount?.account
        return Drive.Builder(
            AndroidHttp.newCompatibleTransport(),
            JacksonFactory.getDefaultInstance(),
            credential
        )
            .setApplicationName("RevSb")
            .build()
    }


    suspend fun getFolders(): List<String> = withContext(Dispatchers.IO) {
        val credential = GoogleAccountCredential.usingOAuth2(
            context, listOf(DriveScopes.DRIVE)
        )
        credential.selectedAccount = googleAccount?.account
        val driveService = Drive.Builder(
            AndroidHttp.newCompatibleTransport(),
            JacksonFactory.getDefaultInstance(),
            credential
        )
            .setApplicationName("RevSb")
            .build()

        val result: MutableList<String> = ArrayList()
        var pageToken: String? = null
        do {
            val fileList: FileList = driveService.files().list()
                .setQ("mimeType='application/vnd.google-apps.folder'")
                .setSpaces("drive")
                .setFields("nextPageToken, files(id, name)")
                .setPageToken(pageToken)
                .execute()
            for (file in fileList.files) {
                result.add(file.name)
            }
            pageToken = fileList.nextPageToken
        } while (pageToken != null)

        return@withContext result
    }

    suspend fun getImages(): List<SelectedFile> = withContext(Dispatchers.IO) {
        val credential = GoogleAccountCredential.usingOAuth2(
            context, listOf(DriveScopes.DRIVE)
        )
        credential.selectedAccount = googleAccount?.account
        val driveService = Drive.Builder(
            AndroidHttp.newCompatibleTransport(),
            JacksonFactory.getDefaultInstance(),
            credential
        )
            .setApplicationName("RevSb")
            .build()

        val result: MutableSet<SelectedFile> = LinkedHashSet()
        do {
            val fileList: FileList = driveService.files().list()
                .setQ("mimeType contains 'image/'")  // Запрос только изображений
                .setSpaces("drive")
                .setFields("nextPageToken, files(id, name, thumbnailLink, webViewLink, mimeType, size)")
                .setPageSize(10)  // Ограничение на количество возвращаемых файлов
                .setPageToken(_pageToken)  // Используйте сохраненный pageToken
                .execute()
            for (file in fileList.files) {
                result.add(convertGoogleDriveFileToFile(file))
            }
            _pageToken = fileList.nextPageToken  // Сохраните новый pageToken
        } while (_pageToken != null && result.size < 10)

        return@withContext result.toList()
    }



    suspend fun createFolder(): String? = withContext(Dispatchers.IO) {
        val credential = GoogleAccountCredential.usingOAuth2(
            context, listOf(DriveScopes.DRIVE_FILE)
        )
        credential.selectedAccount = googleAccount?.account
        val driveService = Drive.Builder(
            AndroidHttp.newCompatibleTransport(),
            JacksonFactory.getDefaultInstance(),
            credential
        )
            .setApplicationName("YourAppName")
            .build()

        val metadata = File()
            .setName("Папка")
            .setMimeType("application/vnd.google-apps.folder")

        val folder = driveService.files().create(metadata).execute()

        return@withContext folder.id
    }

    private fun convertGoogleDriveFileToFile(googleDriveFile: File): SelectedFile {
        return SelectedFile(
            fileName = googleDriveFile.name,
            fileSize = formatFileSize(context, googleDriveFile.getSize()),
            fileId = googleDriveFile.id,
            fileMimeType = googleDriveFile.mimeType,
            fileThumbnail = googleDriveFile.thumbnailLink,
            fileViewLink = googleDriveFile.webViewLink,
        )
    }

    suspend fun getFileById(fileId: String): SelectedFile? = withContext(Dispatchers.IO) {
        val driveService = getDriveService()
        try {
            val file = driveService.files().get(fileId)
                .setFields("id, name, thumbnailLink, webViewLink, mimeType, size")
                .execute()
            return@withContext convertGoogleDriveFileToFile(file)
        } catch (e: Exception) {
            Log.e("DriveRepository", "Error getting file: $e")
            return@withContext null
        }
    }


}

/*
Ошибка SHA-1 возможна из за того что изменился серт SHA-1. Сделай так:
    Gradle(тот что справа) -> Execute Gradle Task -> введи gradle signingReport
    Скопируй SHA-1 и введи его в Android client 1 и API key 1 здесь: https://console.cloud.google.com/apis/credentials?authuser=1&project=revsb-415413
 Держись. До сюда дожили и дальше справимся*/
