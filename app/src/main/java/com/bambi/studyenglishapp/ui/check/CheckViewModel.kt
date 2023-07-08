package com.bambi.studyenglishapp.ui.check

import android.util.Log
import android.view.View
import android.widget.Button
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bambi.studyenglishapp.model.WordData
import com.bambi.studyenglishapp.model.WordDataDao
import com.bambi.studyenglishapp.model.WordDataRepository
import kotlinx.coroutines.launch
import kotlin.random.Random

class CheckViewModel(private val wordDataRepository: WordDataRepository) : ViewModel() {

    private val _answers = MutableLiveData<List<Int>?>()
    val answers: LiveData<List<Int>?> get() = _answers

    /**
     * shuffleListを作成する
     * **/
    fun shuffleList(): List<WordData> {

        //データ数取得
        val size = wordDataRepository.count()
        Log.d("レコードの総数", size.toString())

        //全てのデータをシャッフル
        val wordList = wordDataRepository.getAllWordData().toMutableList().apply { shuffle() }

        //ランダムに抽出した4つ
        return wordList.take(4)
    }

    fun addAnswer(word: String, answer: Int) {
        viewModelScope.launch {
            val id = wordDataRepository.getIdByWord(word)
            wordDataRepository.addAnswerToWordData(id, answer)
            val answers = wordDataRepository.getAnswersById(id)
            _answers.value = answers
        }
    }


}