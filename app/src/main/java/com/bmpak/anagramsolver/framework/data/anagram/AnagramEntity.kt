package com.bmpak.anagramsolver.framework.data.anagram

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anagrams")
data class AnagramEntity(

    @PrimaryKey
    val quarable: String,

    @ColumnInfo(name = "word_origin")
    val wordOrigin: String,

    @ColumnInfo(name = "dictionary")
    val dictionary: String
)
