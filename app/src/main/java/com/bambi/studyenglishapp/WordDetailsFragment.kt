package com.bambi.studyenglishapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bambi.studyenglishapp.databinding.FragmentWordBookBinding
import com.bambi.studyenglishapp.databinding.FragmentWordDetailsBinding

class WordDetailsFragment : Fragment() {

    private lateinit var binding: FragmentWordDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWordDetailsBinding.inflate(layoutInflater)

        val word = arguments?.getString("word")
        val meaning = arguments?.getString("meaning")
        val sentence = arguments?.getString("sentence")

        binding.word.text = word
        binding.meaning.text = meaning
        binding.sentence.text = sentence


        return binding.root
    }


}