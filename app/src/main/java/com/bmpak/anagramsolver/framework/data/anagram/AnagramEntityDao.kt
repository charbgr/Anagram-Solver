package com.bmpak.anagramsolver.framework.data.anagram

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single

@Dao
interface AnagramEntityDao {

  @Query("SELECT * FROM anagrams WHERE quarable IN (:quarable) AND dictionary IS (:dictionary)")
  fun fetch(quarable: String, dictionary: String): Single<List<AnagramEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun put(anagramEntity: AnagramEntity)
}
