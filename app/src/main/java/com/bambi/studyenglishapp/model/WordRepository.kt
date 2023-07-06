package com.bambi.studyenglishapp.db

class WordRepository(private val wordDataDao: WordDataDao) {
    suspend fun insert(word: WordData) = wordDataDao.insert(word)
}