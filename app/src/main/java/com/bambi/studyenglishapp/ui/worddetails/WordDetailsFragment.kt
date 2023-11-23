package com.bambi.studyenglishapp.ui.worddetails

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bambi.studyenglishapp.R
import com.bambi.studyenglishapp.model.WordDatabase
import com.bambi.studyenglishapp.databinding.FragmentWordDetailsBinding
import com.bambi.studyenglishapp.model.WordDataRepository

class WordDetailsFragment : Fragment() {

    private lateinit var binding: FragmentWordDetailsBinding
    private lateinit var viewModel: WordDetailsViewModel
    private val imageList = mutableListOf<Drawable>()
    private val navController: NavController get() = findNavController()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWordDetailsBinding.inflate(layoutInflater)
        val wordDataDao = WordDatabase.getInstance(requireContext()).wordDataDao()
        val wordDataRepository = WordDataRepository(wordDataDao)
        viewModel = WordDetailsViewModel(wordDataRepository)


        binding.answers.apply {
            layoutManager = GridLayoutManager(requireContext(), 5)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val position = arguments?.getInt("position", -1)
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
            answers?.forEach {
                when (it) {
                    1 -> ContextCompat.getDrawable(requireContext(), R.drawable.answer_correct)
                        ?.let { correct -> imageList.add(correct) }

                    0 -> ContextCompat.getDrawable(requireContext(), R.drawable.answer_incorrect)
                        ?.let { incorrect -> imageList.add(incorrect) }
                }
            }

//            for(i in 1..2){
//                ContextCompat.getDrawable(requireContext(), R.drawable.answer_correct)?.let { correct -> imageList.add(correct) }
//            }
//            for(i in 1..9){
//                ContextCompat.getDrawable(requireContext(), R.drawable.answer_correct)?.let { correct -> imageList.add(correct) }
//            }

            binding.answers.adapter = AnswersRecyclerView(requireContext(), imageList.toList())
        }

        binding.backButton.setOnClickListener {
            navController.popBackStack()
        }
    }
}