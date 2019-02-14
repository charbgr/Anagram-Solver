package com.bmpak.anagramsolver.framework.repository.dictionary

import com.bmpak.anagramsolver.model.Dictionary
import com.bmpak.anagramsolver.model.Dictionary.*
import com.bmpak.anagramsolver.model.DownloadStatus
import com.google.android.gms.tasks.OnCanceledListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.OnPausedListener
import com.google.firebase.storage.OnProgressListener
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableEmitter
import io.reactivex.FlowableOnSubscribe
import timber.log.Timber
import java.io.File

object FirebaseDictionaryRepository : FetchDictionaryRepository {

  private val storage = FirebaseStorage.getInstance()

  override fun fetch(dictionary: Dictionary): Flowable<DownloadStatus> {
    val localFile = File.createTempFile(dictionary::class.java.simpleName, ".txt")
    val dectionaryRef = dictionaryRef(dictionary)
    val fileDownloadTask = dectionaryRef.getFile(localFile)

    val subscriber = object : FlowableOnSubscribe<DownloadStatus> {
      override fun subscribe(emitter: FlowableEmitter<DownloadStatus>) {
        val notifier = DownloadTaskNotifier(emitter, localFile)

        fileDownloadTask.apply {
          addOnSuccessListener(notifier)
          addOnPausedListener(notifier)
          addOnProgressListener(notifier)
          addOnFailureListener(notifier)
          addOnCanceledListener(notifier)
        }

        emitter.setCancellable {
          if (!emitter.isCancelled) {
            fileDownloadTask.cancel()
          }
        }
      }
    }

    return Flowable.create(subscriber, BackpressureStrategy.LATEST)
  }

  class DownloadTaskNotifier(
    private val emitter: FlowableEmitter<DownloadStatus>,
    private val file: File
  ) : OnSuccessListener<FileDownloadTask.TaskSnapshot>,
    OnPausedListener<FileDownloadTask.TaskSnapshot>,
    OnProgressListener<FileDownloadTask.TaskSnapshot>,
    OnFailureListener, OnCanceledListener {

    override fun onSuccess(snapshot: FileDownloadTask.TaskSnapshot) {
      Timber.d("Download of ${file.nameWithoutExtension} finished.")
      notify(DownloadStatus.Success(file))
    }

    override fun onPaused(snapshot: FileDownloadTask.TaskSnapshot) {
      Timber.d("Download of ${file.nameWithoutExtension} paused.")
      notify(DownloadStatus.Pause)
    }

    override fun onProgress(snapshot: FileDownloadTask.TaskSnapshot) {
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
    }

    private fun notify(downloadStatus: DownloadStatus) = emitter.onNext(downloadStatus)
  }

  private fun dictionaryRef(dictionary: Dictionary) = when (dictionary) {
    ENGLISH -> storage.getReference("en_dictionary")
    GREEK -> storage.getReferenceFromUrl(
      "https://firebasestorage.googleapis.com/v0/b/anagram-solver-dev.appspot.com/o/el_dictionary?alt=media&token=36378009-357b-400e-84ff-5b8e529c4dc8")
    FRANCE -> storage.getReference("fr_dictionary")
    GERMAN -> storage.getReference("ge_dictionary")
  }
}
