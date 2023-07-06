package com.bambi.studyenglishapp.model

class WordRepository(private val wordDataDao: WordDataDao) {
    suspend fun insert(word: WordData) = wordDataDao.insert(word)
}