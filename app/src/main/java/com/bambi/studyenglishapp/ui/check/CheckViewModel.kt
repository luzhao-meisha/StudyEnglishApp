package com.bambi.studyenglishapp.ui.check

import android.util.Log
import android.view.View
import android.widget.Button
import androidx.lifecycle.ViewModel
import com.bambi.studyenglishapp.model.WordData
import com.bambi.studyenglishapp.model.WordDataDao
import kotlin.random.Random

class CheckViewModel(private val wordDataDao: WordDataDao) : ViewModel() {

    /**
     * shuffleListを作成する
     * **/
    fun shuffleList(): List<WordData> {

        //データ数取得
        val size = wordDataDao.count()
        Log.d("レコードの総数", size.toString())

        //全てのデータをシャッフル
        val wordList = wordDataDao.getAllWordData().toMutableList().apply { shuffle() }

        //ランダムに抽出した4つ
        return wordList.take(4)
    }


}