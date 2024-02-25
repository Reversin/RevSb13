//import android.os.AsyncTask
//
//
//class ListFilesTask(private val credential: GoogleAccountCredential) : AsyncTask<Void, Void, List<String>>() {
//
//    override fun doInBackground(vararg params: Void): List<String> {
//        val driveService = Drive.Builder(
//            AndroidHttp.newCompatibleTransport(),
//            GsonFactory(),
//            credential
//        ).setApplicationName("Your Application Name")
//            .build()
//
//        val result: MutableList<String> = ArrayList()
//        var pageToken: String? = null
//        do {
//            val fileList: FileList = driveService.files().list()
//                .setQ("mimeType != 'application/vnd.google-apps.folder'")
//                .setSpaces("drive")
//                .setFields("nextPageToken, files(id, name)")
//                .setPageToken(pageToken)
//                .execute()
//            for (file in fileList.files) {
//                result.add(file.name)
//            }
//            pageToken = fileList.nextPageToken
//        } while (pageToken != null)
//        return result
//    }
//
//    override fun onPostExecute(result: List<String>) {
//        super.onPostExecute(result)
//        // Обработка результата
//    }
//}
