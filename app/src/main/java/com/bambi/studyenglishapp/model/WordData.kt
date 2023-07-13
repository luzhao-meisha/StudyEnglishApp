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
    val id: Long = 0L,

    @ColumnInfo(name = "english")
    val english: String = "",

    @ColumnInfo(name = "japanese")
    val japanese: String = "",

    @ColumnInfo(name = "sentence")
    val sentence: String = "",

    @ColumnInfo(name = "date")
    val date: String = "",

    @ColumnInfo(name = "answers")
    @TypeConverters
    var answers: List<Int>? = emptyList(),

    @ColumnInfo(name = "pass")
    var pass: Boolean = false
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