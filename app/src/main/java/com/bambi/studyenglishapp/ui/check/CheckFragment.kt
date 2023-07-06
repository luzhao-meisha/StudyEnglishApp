package com.bambi.studyenglishapp.ui.check

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.bambi.studyenglishapp.R
import com.bambi.studyenglishapp.databinding.FragmentCheckBinding
import com.bambi.studyenglishapp.model.WordData
import com.bambi.studyenglishapp.model.WordDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class CheckFragment : Fragment() {

    private lateinit var binding: FragmentCheckBinding
    private lateinit var viewModel: CheckViewModel

    // 選択肢4つのButtonリスト作成
    private val selectionList = mutableListOf<Button>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,

        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCheckBinding.inflate(layoutInflater)
        val wordDataDao = WordDatabase.getInstance(requireContext()).wordDataDao()
        viewModel = CheckViewModel(wordDataDao)

        selectionList.add(binding.word0)
        selectionList.add(binding.word1)
        selectionList.add(binding.word2)
        selectionList.add(binding.word3)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        displayQuiz()

        //ホームボタン
        binding.homeButton.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_checkFragment_to_menuFragment)
        }

        //次へボタン
        binding.nextButton.setOnClickListener {
            moveToNextQuiz()
        }
    }

    /**
     * 問題作成
     * **/
    private fun displayQuiz() {

        lifecycleScope.launch {

            //データを取得しシャッフルする
            val shuffledList: List<WordData>
            withContext(Dispatchers.IO) {
                shuffledList = viewModel.shuffleList()
            }

            //ランダムに選ばれた文字列を選択肢ボタンにセット
            for (i in selectionList.indices) {
                selectionList[i].text = shuffledList[i].japanese
            }
            //クイズ対象の英語
            val randomNum = Random.nextInt(0, 4)
            binding.word.text = shuffledList[randomNum].english

            //正誤チェック
            selectionList.forEach { wordButton ->
                wordButton.setOnClickListener {
                    //正答
                    if (wordButton.text.toString() == shuffledList[randomNum].japanese) {
                        binding.correct.visibility = View.VISIBLE
                        binding.incorrect.visibility = View.GONE
                        wordButton.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.correct_color))
                    //誤答
                    } else {
                        binding.incorrect.visibility = View.VISIBLE
                        binding.correct.visibility = View.GONE
                        wordButton.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.incorrect_color))
                    }
                }
            }
        }
    }

    /** 次のクイズを表示する **/
    private fun moveToNextQuiz() {
        reset()
        displayQuiz()
    }

    /** 初期化 **/
    private fun reset() {
        binding.correct.visibility = View.GONE
        binding.incorrect.visibility = View.GONE
        selectionList.forEach { button ->
            button.backgroundTintList = ColorStateList.valueOf(Color.WHITE)
        }
    }


}

