package com.bambi.studyenglishapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bambi.studyenglishapp.databinding.FragmentMenuBinding
import com.bambi.studyenglishapp.databinding.FragmentWordBookBinding

class WordBookFragment : Fragment() {

    private lateinit var binding: FragmentWordBookBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWordBookBinding.inflate(layoutInflater)
        val view = binding.root

        binding.homeButton.setOnClickListener {
            findNavController().navigate(R.id.action_wordBookFragment_to_menuFragment)

        }

        return view
    }


}