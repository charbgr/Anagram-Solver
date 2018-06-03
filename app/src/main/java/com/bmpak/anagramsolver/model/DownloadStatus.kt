package com.bmpak.anagramsolver.model

import java.io.File

sealed class DownloadStatus {
  data class Success(val file: File) : DownloadStatus()
  object Pause : DownloadStatus()
  object Cancelled : DownloadStatus()
  data class Downloading(val bytesTransferred: Long, val totalByteCount: Long) : DownloadStatus() {
    val percentage: Float by lazy {
      if (totalByteCount == 0L) 0f else (bytesTransferred.toFloat() / totalByteCount) * 100
    }

    override fun toString(): String {
      return "Downloading(percentage=$percentage, totalByteCount=$totalByteCount, bytesTransferred=$bytesTransferred)"
    }
  }

  data class Failed(val throwable: Throwable) : DownloadStatus()
}
