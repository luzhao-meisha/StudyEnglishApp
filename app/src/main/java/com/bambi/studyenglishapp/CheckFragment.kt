package com.bambi.studyenglishapp

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.room.Room
import com.bambi.studyenglishapp.databinding.FragmentCheckBinding
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

    /**
     * 問題を作成する
     * */
    private suspend fun displayQuiz() {
        withContext(Dispatchers.IO) {

            //データ数取得
            val size = wordDataDao.count()
            Log.d("レコードの総数", size.toString())


            val randomNumberList = mutableListOf<Int>()

            /**ランダムに問題となる単語とその意味を取得*/
            //ランダムな数を生成
            val correctRandom = size.let { Random.nextInt(1, it) }
            randomNumberList.add(correctRandom)
            //英単語
            val englishWord = wordDataDao.getEnglishNameById(correctRandom)
            //意味
            val japaneseWordList = mutableListOf<String>()
            japaneseWordList.add(wordDataDao.getJapaneseNameById(correctRandom))

            /**ランダムに意味を取得*/
            for (i in 0..2) {
                val random = size.let { Random.nextInt(1, it) }
                randomNumberList.add(random)
                japaneseWordList.add(wordDataDao.getJapaneseNameById(random))
            }

            // 選択肢4つのButtonリスト作成
            val selectionList = mutableListOf(
                binding.word0,
                binding.word1,
                binding.word2,
                binding.word3,
            )


            withContext(Dispatchers.Main) {
                binding.word.text = englishWord

                //重複を許さずに要素を選択するために、ランダムなインデックスのリストを作成し
                val uniqueIndices = mutableListOf<Int>()
                while (uniqueIndices.size < selectionList.size) {
                    val randomIndex = Random.nextInt(japaneseWordList.size)
                    if (uniqueIndices.contains(randomIndex).not()) {
                        uniqueIndices.add(randomIndex)
                    }
                }

                // ランダムに選ばれた文字列をセット
                for (i in selectionList.indices) {
                    val randomIndex = uniqueIndices[i]
                    val randomWord = japaneseWordList[randomIndex]
                    selectionList[i].text = randomWord
                }

                //正誤チェック
                selectionList.forEachIndexed { index, word ->
                    word.setOnClickListener {
                        if (word.text.toString() == japaneseWordList[0]) {
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

    //次のクイズを表示する
    private fun moveToNextQuiz() {
        lifecycleScope.launch {
            binding.correct.visibility = View.GONE
            binding.incorrect.visibility = View.GONE
            displayQuiz()
        }
    }
}

