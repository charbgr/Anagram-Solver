package com.bmpak.anagramsolver.ui.search.arch.repository

import com.bmpak.anagramsolver.utils.Either
import com.google.firebase.FirebaseException
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.experimental.suspendCancellableCoroutine

object FirebaseRepository : AnagramRepository {

  private val database = FirebaseDatabase.getInstance().also {
    it.setPersistenceEnabled(true)
  }

  private val greekRef = database.getReference("greek")

  override suspend fun fetch(query: CharSequence): Either<List<String>, Throwable> {
    val queryRef = greekRef.child(query.toString())
    return suspendCancellableCoroutine { continuation ->
      val listener = object : ValueEventListener {
        override fun onCancelled(error: DatabaseError) {
          val exception = when (error.toException()) {
            is FirebaseException -> error.toException()
            else -> Exception("Query($query) cancelled in Firebase.")
          }

          continuation.resume(Either.Right(exception))
        }

        override fun onDataChange(snapshot: DataSnapshot) {
          try {
            val data: String? = snapshot.getValue(String::class.java)
            val dataSplitted = data?.split(";") ?: emptyList()
            continuation.resume(Either.Left(dataSplitted))
          } catch (exception: Exception) {
            continuation.resume(Either.Right(exception))
          }
        }
      }

      continuation.invokeOnCompletion { queryRef.removeEventListener(listener) }
      queryRef.addListenerForSingleValueEvent(listener)
    }
  }
}
