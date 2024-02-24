package com.revsb_11.models.dataclasses

/**
 * @param originalFileUri Путь до комментируемого файла
 * @param newFileComment Комментарий к файлу
 */
class NewFileComment(
    val originalFileUri: String,
    val newFileComment: String
)