package com.bambi.studyenglishapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class WordList(
    val values: List<List<String>>
)

@Entity(tableName = "word_data_table")
data class WordData(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "english")
    val english: String = "",

    @ColumnInfo(name = "japanese")
    var japanese: String = "",

    @ColumnInfo(name = "sentence")
    var sentence: String = ""
)