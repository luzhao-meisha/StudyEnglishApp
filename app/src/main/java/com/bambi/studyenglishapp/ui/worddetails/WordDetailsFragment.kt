package com.bambi.studyenglishapp.ui.worddetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bambi.studyenglishapp.R
import com.bambi.studyenglishapp.model.WordDatabase
import com.bambi.studyenglishapp.databinding.FragmentWordDetailsBinding
import com.bambi.studyenglishapp.model.WordDataRepository

class WordDetailsFragment : Fragment() {

    private lateinit var binding: FragmentWordDetailsBinding
    private lateinit var viewModel: WordDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWordDetailsBinding.inflate(layoutInflater)
        val wordDataDao = WordDatabase.getInstance(requireContext()).wordDataDao()
        val wordDataRepository = WordDataRepository(wordDataDao)
        viewModel = WordDetailsViewModel(wordDataRepository)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val position = arguments?.getInt("position",-1)
        position?.let {
            viewModel.loadDetails(it)
            viewModel.getAnswers(it)
        }

        viewModel.word.observe(viewLifecycleOwner) { word ->
            binding.word.text = word
        }

        viewModel.meaning.observe(viewLifecycleOwner) { meaning ->
            binding.meaning.text = meaning
        }

        viewModel.sentence.observe(viewLifecycleOwner) { sentence ->
            binding.sentence.text = sentence
        }

        viewModel.date.observe(viewLifecycleOwner) { date ->
            binding.date.text = requireContext().getString(R.string.date, date)
        }

        viewModel.answers.observe(viewLifecycleOwner) { answers ->
            binding.answers.text = answers?.toString()
        }

        binding.backButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }



    }


}