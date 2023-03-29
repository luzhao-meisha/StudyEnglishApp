package com.bambi.studyenglishapp

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface WordDataDao {

    @Insert
    suspend fun insert(wordData: WordData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(data: List<WordData>)

    @Update
    suspend fun update(wordData: WordData)

    @Query("SELECT * from word_data_table WHERE id = :key")
    suspend fun get(key: Long): WordData?

    @Query("DELETE FROM word_data_table")
    suspend fun clear()

    @Query("SELECT * FROM word_data_table ORDER BY id DESC")
    fun getAllWordData(): LiveData<List<WordData>>

}