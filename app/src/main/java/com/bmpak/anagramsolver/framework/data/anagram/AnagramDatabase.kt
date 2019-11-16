package com.bmpak.anagramsolver.framework.data.anagram

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [AnagramEntity::class], version = 1)
abstract class AnagramDatabase : RoomDatabase() {
  abstract fun anagramEntityDao(): AnagramEntityDao
}
