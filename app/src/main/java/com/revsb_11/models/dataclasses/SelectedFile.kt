package com.revsb_11.models.dataclasses

/**
 * @param fileName Имя файла
 * @param filePath Путь до файла зависящий от метода его поиска в файловой системе
 * @param fileSize Вес файла
 * @param longTermPath Долгосрочный путь до файла позволяющий получить доступ после перезагрузки приложения, но менее читаемый для пользователя
 * @param fileComments Комментарий пользователя к файлу
 * @param fileId Идентификатор файла в Google Drive
 * @param fileModifiedTime Время последнего изменения файла
 * @param fileOwner Владелец файла
 * @param fileMimeType MIME-тип файла
 * @param fileThumbnail Ссылка на миниатюру файла
 * @param fileContentLink Ссылка для загрузки файла
 * @param fileCommentChanged Флаг изменения комментария
 */
data class SelectedFile(
    val fileName: String = "",
    var filePath: String = "",
    val fileSize: String = "",
    val longTermPath: String = "",
    var fileComments: String = "",
    val fileId: String = "",
    val fileModifiedTime: String = "",
    val fileOwner: String = "",
    val fileMimeType: String = "",
    val fileThumbnail: String = "",
    val fileContentLink: String = "",
    val fileCommentChanged: Boolean = false,
)
