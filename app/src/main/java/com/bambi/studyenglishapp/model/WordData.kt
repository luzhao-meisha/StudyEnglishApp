package com.bambi.studyenglishapp.model

import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class WordList(
    val values: List<List<String>>
)

@Entity(tableName = "word_data_table")
@TypeConverters(IntListConverter::class)
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
    @TypeConverters
    var answers: List<Int> = emptyList()
)


class IntListConverter {
    @TypeConverter
    fun fromList(list: List<Int>?): String? {
        return list?.joinToString(",")
    }

    @TypeConverter
    fun toList(data: String?): List<Int> {
        return if (data.isNullOrEmpty()) {
            emptyList()
        } else {
            data.trim().split(",").map { it.trim().toInt() }
        }
    }
}