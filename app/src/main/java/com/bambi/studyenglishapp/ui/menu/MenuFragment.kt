package com.bambi.studyenglishapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bambi.studyenglishapp.R
import com.bambi.studyenglishapp.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {

    private lateinit var binding: FragmentMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        binding = FragmentMenuBinding.inflate(layoutInflater)
        val view = binding.root

        binding.wordBookButton.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_wordBookFragment)

        }

        binding.checkButton.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_checkFragment)

        }

        return view
    }

}