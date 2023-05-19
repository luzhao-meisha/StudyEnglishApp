package com.bambi.studyenglishapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.room.Room
import com.bambi.studyenglishapp.databinding.FragmentCheckBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.properties.Delegates
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
    }

    private suspend fun displayQuiz() {
        withContext(Dispatchers.IO) {

            //データ数取得
            val size = wordDataDao.count()
            Log.d("レコードの総数", size.toString())
            //ランダムな数を生成
            val random = size.let { Random.nextInt(1, it) }
            //ランダムに単語を表示
            val englishName = wordDataDao.getEnglishNameById(random)

            withContext(Dispatchers.Main) {
                binding.word.text = englishName
            }
        }
    }
}

