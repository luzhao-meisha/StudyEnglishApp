package com.bambi.studyenglishapp.model

import androidx.room.Query

class WordDataRepository(private val wordDataDao: WordDataDao) {
    /**データ挿入*/
    suspend fun insert(word: WordData) = wordDataDao.insert(word)

    /**全てのデータ取得*/
     fun getAllWordData()  = wordDataDao.getAllWordData()

    /**特定の日本語を取得*/
    fun getJapaneseNameById(index: Int)  = wordDataDao.getJapaneseNameById(index)

    /**特定の英語を取得*/
    fun getEnglishNameById(index: Int)  = wordDataDao.getEnglishNameById(index)

    /**特定の例文を取得*/
    fun getSentenceById(index: Int)  = wordDataDao.getSentenceById(index)

}