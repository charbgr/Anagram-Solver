package com.bmpak.anagramsolver.framework.repository.dictionary

import com.bmpak.anagramsolver.model.Dictionary
import com.bmpak.anagramsolver.model.Dictionary.ENGLISH
import com.bmpak.anagramsolver.model.Dictionary.FRANCE
import com.bmpak.anagramsolver.model.Dictionary.GERMAN
import com.bmpak.anagramsolver.model.Dictionary.GREEK
import com.bmpak.anagramsolver.model.DownloadStatus
import com.google.android.gms.tasks.OnCanceledListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FileDownloadTask.TaskSnapshot
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.OnPausedListener
import com.google.firebase.storage.OnProgressListener
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.channels.ReceiveChannel
import kotlinx.coroutines.experimental.channels.RendezvousChannel
import kotlinx.coroutines.experimental.launch
import timber.log.Timber
import java.io.File
import java.lang.Exception
import kotlin.coroutines.experimental.CoroutineContext

object FirebaseDictionaryRepository : FetchDictionaryRepository {

  private val storage = FirebaseStorage.getInstance()

  override fun fetch(dictionary: Dictionary): ReceiveChannel<DownloadStatus> {
    val localFile = File.createTempFile(dictionary::class.java.simpleName, ".txt")
    val dectionaryRef = dictionaryRef(dictionary)
    return dectionaryRef.getFile(localFile).asChannel(CommonPool, localFile)
  }

  private class DownloadTaskReceiveChannel(
      private val context: CoroutineContext,
      private val channel: Channel<DownloadStatus>,
      private val file: File
  ) : OnSuccessListener<FileDownloadTask.TaskSnapshot>,
      OnPausedListener<FileDownloadTask.TaskSnapshot>,
      OnProgressListener<FileDownloadTask.TaskSnapshot>,
      OnFailureListener,
      OnCanceledListener,
      ReceiveChannel<DownloadStatus> by channel {

    override fun onSuccess(snapshot: TaskSnapshot) {
      Timber.d("Download of ${file.nameWithoutExtension} finished.")
      notify(DownloadStatus.Success(file))
    }

    override fun onPaused(snapshot: TaskSnapshot) {
      Timber.d("Download of ${file.nameWithoutExtension} paused.")
      notify(DownloadStatus.Pause)
    }

    override fun onProgress(snapshot: TaskSnapshot) {
      Timber.d("Download of ${file.nameWithoutExtension} in progress.")
      notify(DownloadStatus.Downloading(snapshot.bytesTransferred, snapshot.totalByteCount))
    }

    override fun onFailure(exception: Exception) {
      Timber.d("Download of ${file.nameWithoutExtension} failed: ${exception}.")
      notify(DownloadStatus.Failed(exception))
    }

    override fun onCanceled() {
      Timber.d("Download of ${file.nameWithoutExtension} cancelled.")
      notify(DownloadStatus.Cancelled)
      channel.cancel()
    }

    private fun notify(downloadStatus: DownloadStatus) = launch(context) {
      channel.send(downloadStatus)
    }
  }

  private fun FileDownloadTask.asChannel(context: CoroutineContext,
      file: File
  ): ReceiveChannel<DownloadStatus> {
    val channel = RendezvousChannel<DownloadStatus>()
    val listenerChannel = DownloadTaskReceiveChannel(context, channel, file)

    addOnSuccessListener(listenerChannel)
    addOnPausedListener(listenerChannel)
    addOnProgressListener(listenerChannel)
    addOnFailureListener(listenerChannel)
    addOnCanceledListener(listenerChannel)
    return listenerChannel
  }

  private fun dictionaryRef(dictionary: Dictionary) = when (dictionary) {
    ENGLISH -> storage.getReference("en_dictionary")
    GREEK -> storage.getReferenceFromUrl(
        "https://firebasestorage.googleapis.com/v0/b/anagram-solver-dev.appspot.com/o/el_dictionary?alt=media&token=36378009-357b-400e-84ff-5b8e529c4dc8")
    FRANCE -> storage.getReference("fr_dictionary")
    GERMAN -> storage.getReference("ge_dictionary")
  }
}
