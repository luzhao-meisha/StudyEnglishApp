package com.bambi.studyenglishapp.ui.wordbook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.addCallback
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bambi.studyenglishapp.R
import com.bambi.studyenglishapp.databinding.FragmentWordBookBinding
import com.bambi.studyenglishapp.model.WordData
import com.bambi.studyenglishapp.model.WordDataRepository
import com.bambi.studyenglishapp.model.WordDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class WordBookFragment : Fragment() {

    private lateinit var binding: FragmentWordBookBinding
    private lateinit var viewModel: WordBookViewModel
    private lateinit var wordListAdapter: WordListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().popBackStack()
        }

        val wordDataDao = WordDatabase.getInstance(requireContext()).wordDataDao()
        val wordDataRepository = WordDataRepository(wordDataDao)
        viewModel = WordBookViewModel(wordDataRepository)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWordBookBinding.inflate(layoutInflater)
        val view = binding.root

        binding.homeButton.setOnClickListener {
            findNavController().navigate(R.id.action_wordBookFragment_to_menuFragment)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var wordDataList: List<WordData> = emptyList()


        lifecycleScope.launch {

            withContext(Dispatchers.IO) {
                wordListAdapter = WordListAdapter()
                viewModel.fetchWordData()

            }

            //recyclerview描画
            withContext(Dispatchers.Main) {
                viewModel.wordData.observe(viewLifecycleOwner) { data ->
                    wordListAdapter.setWords(data)
                    wordDataList = data
                }

                binding.wordRv.apply {
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = wordListAdapter
                }

                //SearchViewの設定
                binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        newText?.let { searchQuery ->
                            val filteredList = wordDataList.filter { item ->
                                item.english.contains(searchQuery, ignoreCase = true)
                            }
                            wordListAdapter.setWords(filteredList.toMutableList())
                        }
                        return true
                    }
                })

                //Spinnerの設定
                binding.sortSpinner.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {

                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            viewModel.sortWordData(position)

                            when (position) {
                                ALPHABET_ORDER -> {
                                    viewModel.alphabetOrderData.observe(viewLifecycleOwner) { data ->
                                        wordListAdapter.setWords(data)
                                    }
                                }
                                ADD_DATE_ORDER -> {
                                    viewModel.addDateOrderData.observe(viewLifecycleOwner) { data ->
                                        wordListAdapter.setWords(data)
                                    }
                                }
                                INCORRECT_ORDER -> {
                                    viewModel.incorrectOrderData.observe(viewLifecycleOwner) { data ->
                                        wordListAdapter.setWords(data)
                                    }
                                }
                            }
                        }
                        override fun onNothingSelected(parent: AdapterView<*>?) {
                        }
                    }
            }
        }
    }

    companion object {
        const val ADD_DATE_ORDER = 0
        const val ALPHABET_ORDER = 1
        const val INCORRECT_ORDER = 2
    }

}