package com.bambi.studyenglishapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.bambi.studyenglishapp.adapter.WordListAdapter
import com.bambi.studyenglishapp.databinding.FragmentWordBookBinding


class WordBookFragment : Fragment() {

    private lateinit var binding: FragmentWordBookBinding
    private lateinit var getData: List<WordData>

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
        val db = Room.databaseBuilder(
            requireContext(),
            WordDatabase::class.java,
            "word_database"
        ).build()

        val wordDao = db.wordDataDao()
        val words = wordDao.getAllWordData()


        words.observe(viewLifecycleOwner) { wordData ->
            getData = wordData

            //recyclerview描画
            binding.wordRv.apply {
                adapter = WordListAdapter(getData)
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

}