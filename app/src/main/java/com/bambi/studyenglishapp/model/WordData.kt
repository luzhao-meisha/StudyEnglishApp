package com.bambi.studyenglishapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters

data class WordList(
    val values: List<List<String>>
)

@Entity(tableName = "word_data_table")
@TypeConverters(IntegerListConverter::class)
data class WordData(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "english")
    val english: String = "",

    @ColumnInfo(name = "japanese")
    var japanese: String = "",

    @ColumnInfo(name = "sentence")
    var sentence: String = "",

    @ColumnInfo(name = "date")
    var date: String = "",

    @ColumnInfo(name = "answers")
    var answers: MutableList<Int>? = null
)


class IntegerListConverter {
    @TypeConverter
    fun fromIntegerList(list: List<Int>?): String? {
        return list?.joinToString(",")
    }

    @TypeConverter
    fun toIntegerList(data: String?): List<Int>? {
        return data?.split(",")?.map { it.toInt() }
    }
}