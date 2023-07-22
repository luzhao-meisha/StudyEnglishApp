package com.bambi.studyenglishapp.ui.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bambi.studyenglishapp.R
import com.bambi.studyenglishapp.databinding.FragmentMenuBinding
import com.bambi.studyenglishapp.model.WordDataRepository
import com.bambi.studyenglishapp.model.WordDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MenuFragment : Fragment() {

    private lateinit var binding: FragmentMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        binding = FragmentMenuBinding.inflate(layoutInflater)
        val view = binding.root

        val wordDataDao = WordDatabase.getInstance(requireContext()).wordDataDao()
        val wordDataRepository = WordDataRepository(wordDataDao)

        binding.wordBookButton.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_wordBookFragment)

        }

        binding.checkButton.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_checkFragment)

        }

        lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    val totalCount = wordDataRepository.count()
                    val completedCount = wordDataRepository.getAllWordData().count { it.pass }
                    val leftCount = wordDataRepository.getAllWordData().count { it.pass.not() }

                    withContext(Dispatchers.Main) {
                        binding.total.text = getString(R.string.total, totalCount.toString())
                        binding.complete.text = getString(R.string.complete, completedCount.toString())
                        binding.left.text = getString(R.string.left, leftCount.toString())
                    }
                }
        }

        return view
    }

}