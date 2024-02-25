package com.revsb_11.repository

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import com.google.api.services.drive.model.File
import com.google.api.services.drive.model.FileList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DriveRepository(private val context: Context) {

    private var googleAccount: GoogleSignInAccount? = null
    private var _pageToken: String? = null

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

    suspend fun getImages(): List<String> = withContext(Dispatchers.IO) {
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

        val result: MutableSet<String> = LinkedHashSet()
        do {
            val fileList: FileList = driveService.files().list()
                .setQ("mimeType contains 'image/'")  // Запрос только изображений
                .setSpaces("drive")
                .setFields("nextPageToken, files(id, name)")
                .setPageSize(35)  // Ограничение на количество возвращаемых файлов
                .setPageToken(_pageToken)  // Используйте сохраненный pageToken
                .execute()
            for (file in fileList.files) {
                result.add(file.name)
            }
            _pageToken = fileList.nextPageToken  // Сохраните новый pageToken
        } while (_pageToken != null && result.size < 35)

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

}
