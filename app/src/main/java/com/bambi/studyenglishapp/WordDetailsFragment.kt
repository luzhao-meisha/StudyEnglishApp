package com.bambi.studyenglishapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.bambi.studyenglishapp.databinding.FragmentWordBookBinding
import com.bambi.studyenglishapp.databinding.FragmentWordDetailsBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WordDetailsFragment : Fragment() {

    private lateinit var binding: FragmentWordDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWordDetailsBinding.inflate(layoutInflater)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 非同期処理でデータを取得する
        lifecycleScope.launch {
                displayDetails()
        }
        binding.backButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }


    private suspend fun displayDetails() {
        withContext(Dispatchers.IO) {
            val position = arguments?.getInt("position")

            // Roomデータベースにあるデータを取得
            val db = WordDatabase.getInstance(requireContext())

            position?.let {
                binding.word.text = db.wordDataDao().getEnglishNameById(it)
                binding.meaning.text = db.wordDataDao().getJapaneseNameById(it)
                binding.sentence.text = db.wordDataDao().getSentenceById(it)
            }

        }
    }

}