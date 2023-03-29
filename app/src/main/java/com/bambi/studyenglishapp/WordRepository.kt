package com.bambi.studyenglishapp

class WordRepository(private val wordDataDao: WordDataDao) {

    suspend fun insert(word:WordData) = wordDataDao.insert(word)
}