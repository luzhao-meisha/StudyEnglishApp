package com.bambi.studyenglishapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.bambi.studyenglishapp.databinding.FragmentCheckBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class CheckFragment : Fragment() {

    private lateinit var binding: FragmentCheckBinding

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
            displayQuiz()
        }
    }

    private suspend fun displayQuiz() {
        withContext(Dispatchers.IO) {

            // Roomデータベースにあるデータを取得
            val db = WordDatabase.getInstance(requireContext())
            //データ数取得
            //Fixme:全体の総数がおかしい
            val size = db.wordDataDao().count()

            Log.d("kanuma", size.toString())

            //ランダムな数を生成
            val random = size.let { Random.nextInt(1, it) }


            Log.d("kanuma", random.toString())

            //ランダムに単語を表示
            binding.word.text = db.wordDataDao().getEnglishNameById(random)

            Log.d("kanuma", db.wordDataDao().getEnglishNameById(random))

        }
    }
}

