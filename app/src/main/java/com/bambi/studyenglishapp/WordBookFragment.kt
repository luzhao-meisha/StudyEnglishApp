package com.bambi.studyenglishapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.bambi.studyenglishapp.adapter.WordListAdapter
import com.bambi.studyenglishapp.databinding.FragmentWordBookBinding
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.*

interface ItemInterface {

    @GET(BuildConfig.API_KEY)
    suspend fun getWordList(): WordList
}


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

        val baseApiUrl = "https://sheets.googleapis.com/v4/spreadsheets/"

        val httpLogging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClientBuilder = OkHttpClient.Builder().addInterceptor(httpLogging)

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseApiUrl)
            .client(httpClientBuilder.build())
            .build()


        val service: ItemInterface = retrofit.create(ItemInterface::class.java)


        //Roomを初期化
//        val db = Initialize.database
        val db = WordDatabase.getInstance(requireContext())

        // 非同期処理でデータを取得する
        lifecycleScope.launch {
            val wordList = service.getWordList().values

            // SpreadsheetDataオブジェクトのリストに変換する
            val dataList = mutableListOf<WordData>()
            wordList.forEachIndexed { index, row ->
                val data = WordData(
                    id = index.toLong(),
                    english = row[0],
                    japanese = row[1],
                    sentence = row[2],
                )
                dataList.add(data)
            }

            // Roomデータベースにデータを保存する
            db.wordDataDao().insertAll(dataList)

//            // Roomデータベースにあるデータを取得
//            val getData = db.wordDataDao().getAllWordData()


            binding.wordRv.apply {
                layoutManager = LinearLayoutManager(requireContext())
//                binding.wordRv.apply {
//                    adapter = WordListAdapter(getData).apply {
//                        setOnItemClickListener(object :
//                            WordListAdapter.OnItemClickListener {
//                            override fun onItemClickListener(view: View, position: Int) {
//                                val bundle = bundleOf("position" to position)
//                                findNavController().navigate(
//                                    R.id.action_wordBookFragment_to_wordDetailsFragment,
//                                    bundle
//                                )
//                            }
//                        })
//                    }
//                    layoutManager = LinearLayoutManager(requireContext())
//                }
            }
        }


        return view
    }
}