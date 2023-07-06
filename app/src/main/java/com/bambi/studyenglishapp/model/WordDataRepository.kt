package com.bambi.studyenglishapp.model

class WordDataRepository(private val wordDataDao: WordDataDao) {
    suspend fun insert(word: WordData) = wordDataDao.insert(word)

     fun getAllWordData()  = wordDataDao.getAllWordData()

}