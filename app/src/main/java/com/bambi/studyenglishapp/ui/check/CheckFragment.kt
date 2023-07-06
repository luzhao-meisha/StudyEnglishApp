package com.bambi.studyenglishapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.room.Room
import com.bambi.studyenglishapp.R
import com.bambi.studyenglishapp.databinding.FragmentCheckBinding
import com.bambi.studyenglishapp.db.WordDataDao
import com.bambi.studyenglishapp.db.WordDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class CheckFragment : Fragment() {

    private lateinit var binding: FragmentCheckBinding
    private lateinit var wordDataDao: WordDataDao


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,

        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCheckBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 非同期処理でデータを取得する
        lifecycleScope.launch {
            val db = Room.databaseBuilder(
                requireContext(), WordDatabase::class.java, "word_database"
            ).build()

            wordDataDao = db.wordDataDao()

            displayQuiz()
        }


        //ホームボタン
        binding.homeButton.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_checkFragment_to_menuFragment)
        }

        binding.nextButton.setOnClickListener {
            moveToNextQuiz()
        }


    }

    /** 問題を作成する **/
    private suspend fun displayQuiz() {
        withContext(Dispatchers.IO) {

            //データ数取得
            val size = wordDataDao.count()
            Log.d("レコードの総数", size.toString())


            //全ての英単語
            val wordList = wordDataDao.getAllWordData().toMutableList().apply { shuffle() }
            //ランダムに抽出した4つ
            val shuffledList = wordList.take(4)

            // 選択肢4つのButtonリスト作成
            val selectionList = mutableListOf(
                binding.word0,
                binding.word1,
                binding.word2,
                binding.word3,
            )

            //ランダムに選ばれた文字列をセット
            for (i in selectionList.indices) {
                selectionList[i].text = shuffledList[i].japanese
            }

            withContext(Dispatchers.Main) {

                //クイズ対象の英語
                val randomNum = Random.nextInt(0, 4)
                binding.word.text = shuffledList[randomNum].english

                //正誤チェック
                selectionList.forEach { wordButton ->
                    wordButton.setOnClickListener {
                        if (wordButton.text.toString() == shuffledList[randomNum].japanese) {
                            binding.correct.visibility = View.VISIBLE
                            binding.incorrect.visibility = View.GONE
                        } else {
                            binding.incorrect.visibility = View.VISIBLE
                            binding.correct.visibility = View.GONE

                        }
                    }
                }
            }
        }

    }

    /** 次のクイズを表示する **/
    private fun moveToNextQuiz() {
        lifecycleScope.launch {
            reset()
            displayQuiz()
        }
    }

    /** 初期化 **/
    private fun reset() {
        binding.correct.visibility = View.GONE
        binding.incorrect.visibility = View.GONE
    }
}

