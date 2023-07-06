package com.bambi.studyenglishapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.bambi.studyenglishapp.R
import com.bambi.studyenglishapp.db.WordData
import com.bambi.studyenglishapp.db.WordDatabase
import com.bambi.studyenglishapp.adapter.WordListAdapter
import com.bambi.studyenglishapp.databinding.FragmentWordBookBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class WordBookFragment : Fragment() {

    private lateinit var binding: FragmentWordBookBinding
    private lateinit var wordData: List<WordData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().popBackStack()
        }
    }

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Roomからwordデータ取得
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val db = Room.databaseBuilder(
                    requireContext(),
                    WordDatabase::class.java,
                    "word_database"
                ).build()

                val wordDao = db.wordDataDao()
                wordData = wordDao.getAllWordData()
            }

            //recyclerview描画
            withContext(Dispatchers.Main) {
                binding.wordRv.apply {
                    adapter = WordListAdapter(wordData)
                    layoutManager = LinearLayoutManager(requireContext())
                }
            }
        }
    }

}