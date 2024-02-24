package com.revsb_11.mappers

import com.revsb_11.R

class FileIconMapper {

    fun getIconResourceId(mimeType: String?): Int =
        when (mimeType) {
            MPEG, WAV, MP3 -> R.drawable.baseline_audio_file_24
            MP4, THREE_GP, AVI -> R.drawable.baseline_video_file_24
            PDF -> R.drawable.baseline_picture_as_pdf_24
            ZIP -> R.drawable.baseline_archive_24
            // Добавьте другие типы файлов и соответствующие иконки по необходимости
            else -> R.drawable.baseline_insert_drive_file_24 // По умолчанию
        }

    companion object {
        const val MPEG = "audio/mpeg"
        const val WAV = "audio/wav"
        const val MP3 = "audio/mp3"
        const val MP4 = "video/mp4"
        const val THREE_GP = "video/3gpp"
        const val AVI = "video/avi"
        const val PDF = "application/pdf"
        const val ZIP = "application/zip"
    }
}