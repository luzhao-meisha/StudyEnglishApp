package com.bambi.studyenglishapp

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface WordDataDao {

    /**データ挿入*/
    @Insert
    suspend fun insert(wordData: WordData)

    /**全て挿入*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(data: List<WordData>)

    /**更新*/
    @Update
    suspend fun update(wordData: WordData)

    /**特定のワードデータを取得*/
    @Query("SELECT * from word_data_table WHERE id = :key")
    suspend fun get(key: Long): WordData?

    /**全てのデータ取得*/
    @Query("SELECT * FROM word_data_table ORDER BY id DESC")
    fun getAllWordData(): LiveData<List<WordData>>

    /**レコード数取得*/
    @Query("SELECT COUNT(*) FROM word_data_table")
    fun count(): Int

    /**全ての日本語のデータ取得*/
    @Query("SELECT japanese FROM word_data_table ORDER BY id DESC")
    fun getAllJapaneseData(): LiveData<List<String>>

    /**特定の日本語を取得*/
    @Query("SELECT japanese FROM word_data_table WHERE id = :key")
    fun getJapaneseNameById(key:Int): String

    /**特定の英語を取得*/
    @Query("SELECT english FROM word_data_table WHERE id = :key")
    fun getEnglishNameById(key:Int): String

    /**特定の例文を取得*/
    @Query("SELECT sentence FROM word_data_table WHERE id = :key")
    fun getSentenceById(key:Int): String

    /**消去*/
    @Query("DELETE FROM word_data_table")
    suspend fun clear()



}