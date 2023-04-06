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

    @Query("SELECT * FROM word_data_table ORDER BY id DESC")
    fun getAllWordData(): LiveData<List<WordData>>

    @Query("SELECT japanese FROM word_data_table WHERE id = :key")
    fun getJapaneseNameById(key:Int): String

    @Query("SELECT english FROM word_data_table WHERE id = :key")
    fun getEnglishNameById(key:Int): String

    @Query("SELECT sentence FROM word_data_table WHERE id = :key")
    fun getSentenceById(key:Int): String

    @Query("SELECT english FROM word_data_table WHERE id = 8")
    fun getEnglishNameById(): String



    @Query("DELETE FROM word_data_table")
    suspend fun clear()



}