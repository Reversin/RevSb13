package com.example.revsb_11.extensions

import com.example.revsb_11.R

class FileIconMapper {

    fun getIconResourceId(mimeType: String?): Int {
        return when (mimeType) {
            "audio/mpeg", "audio/wav", "audio/mp3" -> R.drawable.baseline_audio_file_24
            "video/mp4", "video/3gpp", "video/avi" -> R.drawable.baseline_video_file_24
            "application/pdf" -> R.drawable.baseline_picture_as_pdf_24
            "application/zip" -> R.drawable.baseline_archive_24
            // Добавьте другие типы файлов и соответствующие иконки по необходимости
            else -> R.drawable.baseline_insert_drive_file_24 // По умолчанию
        }
    }
}