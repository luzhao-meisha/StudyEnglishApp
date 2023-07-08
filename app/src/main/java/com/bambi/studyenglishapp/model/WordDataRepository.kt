package com.bambi.studyenglishapp.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WordDataRepository(private val wordDataDao: WordDataDao) {
    /**データ挿入*/
    suspend fun insert(word: WordData) = wordDataDao.insert(word)

    /**回答更新*/
     suspend fun addAnswerToWordData(id: Int, answer: Int) {
        withContext(Dispatchers.IO) {
            val wordData = wordDataDao.get(id)
            val newAnswers = wordData.answers.toMutableList()
            newAnswers.let {
                it.add(answer)
                wordDataDao.updateAnswers(id, newAnswers.joinToString())
            }
        }
    }

    /**全てのデータ取得*/
    fun getAllWordData() = wordDataDao.getAllWordData()

    /**レコード数取得*/
    fun count(): Int = wordDataDao.count()

    /**全てのデータ取得（アルファベット順）*/
    fun getAlphabeticalOrder() = wordDataDao.getAllWordData().sortedBy { it.english }

    /**全てのデータ取得（追加日順）*/
    fun getAddDateOrder() = wordDataDao.getAllWordData().sortedBy { it.date }

    //TODO:　間違え順の関数追加

    /**特定の日本語を取得*/
    fun getJapaneseNameById(index: Int) = wordDataDao.getJapaneseNameById(index)

    /**特定の英語を取得*/
    fun getEnglishNameById(index: Int) = wordDataDao.getEnglishNameById(index)

    /**特定の例文を取得*/
    fun getSentenceById(index: Int) = wordDataDao.getSentenceById(index)

    /**特定の追加日を取得*/
    fun getDateById(index: Int) = wordDataDao.getDateById(index)

    /**特定の回答を取得*/
    suspend fun getAnswersById(index: Int): List<Int> {
        return withContext(Dispatchers.IO) {
            wordDataDao.get(index).answers
        }
    }

    /**特定のidを取得*/
    suspend fun getIdByWord(word: String): Int {
        return withContext(Dispatchers.IO) {
            wordDataDao.getIdByWord(word)
        }
    }
}