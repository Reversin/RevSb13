package com.revsb_11.models.dataclasses

/**
 * @param filePath Путь до файла зависящий от метода его поиска в файловой системе
 * @param fileSize Вес файла
 * @param longTermPath Долгосрочный путь до файла позволяющий полчить доступ после перезагрузки приложения но менее читаемый для пользователя
 * @param fileComments Комментарий пользователя к файлу
 */
data class SelectedFile(
    var filePath: String?,
    val fileSize: String?,
    val longTermPath: String,
    var fileComments: String
) {
    constructor() : this("",  "", "", "")
}

