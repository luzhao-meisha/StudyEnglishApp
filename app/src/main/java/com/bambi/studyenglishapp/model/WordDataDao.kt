package com.bambi.studyenglishapp.model

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

    /**特定の回答を更新*/
    @Query("UPDATE word_data_table SET answers = :newAnswers WHERE id = :id")
    fun updateAnswers(id: Int, newAnswers: String)

    /**特定のpassデータを更新*/
    @Query("UPDATE word_data_table SET pass = :pass WHERE id = :id")
    fun updatePassData(id: Int, pass: Boolean)

    /**全てのデータ取得*/
    @Query("SELECT * FROM word_data_table ORDER BY id ASC")
    fun getAllWordData(): List<WordData>

    /**レコード数取得*/
    @Query("SELECT COUNT(*) FROM word_data_table")
    fun count(): Int

    /**全ての日本語のデータ取得*/
    @Query("SELECT japanese FROM word_data_table ORDER BY id DESC")
    fun getAllJapaneseData(): List<String>

    /**全ての英語のデータ取得*/
    @Query("SELECT english FROM word_data_table ORDER BY id ASC")
    fun getAllEnglishData(): List<String>

    /**特定のワードデータを取得*/
    @Query("SELECT * from word_data_table WHERE id = :key")
    fun get(key: Int): WordData

    /**特定のidを取得*/
    @Query("SELECT id FROM word_data_table WHERE english = :word")
    fun getIdByWord(word: String): Int

    /**特定の日本語を取得*/
    @Query("SELECT japanese FROM word_data_table WHERE id = :key")
    fun getJapaneseNameById(key: Int): String

    /**特定の英語を取得*/
    @Query("SELECT english FROM word_data_table WHERE id = :key")
    fun getEnglishNameById(key: Int): String

    /**特定の例文を取得*/
    @Query("SELECT sentence FROM word_data_table WHERE id = :key")
    fun getSentenceById(key: Int): String

    /**特定の追加日を取得*/
    @Query("SELECT date FROM word_data_table WHERE id = :key")
    fun getDateById(key: Int): String

    /**特定の回答を取得*/
    @Query("SELECT answers FROM word_data_table WHERE id = :key")
    fun getAnswersById(key: Int): String?

    /**特定のデータを消去*/
    @Query("DELETE FROM word_data_table WHERE id = :key")
    suspend fun clearByID(key: Int)

    /**消去*/
    @Query("DELETE FROM word_data_table")
    suspend fun clear()

}