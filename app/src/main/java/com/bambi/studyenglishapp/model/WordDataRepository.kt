package com.bambi.studyenglishapp.model

class WordDataRepository(private val wordDataDao: WordDataDao) {
    /**データ挿入*/
    suspend fun insert(word: WordData) = wordDataDao.insert(word)

    /**全てのデータ取得*/
     fun getAllWordData()  = wordDataDao.getAllWordData()

    /**全てのデータ取得（アルファベット順）*/
    fun getAlphabeticalOrder()  = wordDataDao.getAllWordData().sortedBy { it.english }

    /**全てのデータ取得（追加日順）*/
    fun getAddDateOrder()  = wordDataDao.getAllWordData().sortedBy { it.date }
    
    //TODO:間違え順の関数追加

    /**特定の日本語を取得*/
    fun getJapaneseNameById(index: Int)  = wordDataDao.getJapaneseNameById(index)

    /**特定の英語を取得*/
    fun getEnglishNameById(index: Int)  = wordDataDao.getEnglishNameById(index)

    /**特定の例文を取得*/
    fun getSentenceById(index: Int)  = wordDataDao.getSentenceById(index)

    /**特定の追加日を取得*/
    fun getDateById(index: Int)  = wordDataDao.getDateById(index)
}