package com.bambi.studyenglishapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.bambi.studyenglishapp.adapter.WordListAdapter
import com.bambi.studyenglishapp.databinding.FragmentWordBookBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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


lateinit var service: ItemInterface

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


        service = retrofit.create(ItemInterface::class.java)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 非同期処理でデータを取得する
        lifecycleScope.launch {
            //データ取得
            val getData = getData()

            //recyclerviewを描画
            displayList(getData)

            //Roomにデータ書き込み
            writeWordDatabase(getData)
        }
    }

    //データ取得
    private suspend fun getData(): List<WordData> {

        var returnList: List<WordData>

        withContext(Dispatchers.IO) {
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

            returnList = dataList
        }

        return returnList

    }


    //recyclerview描画
    private suspend fun displayList(getData: List<WordData>) {
        binding.wordRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            binding.wordRv.apply {
                adapter = WordListAdapter(getData).apply {
                    setOnItemClickListener(object :
                        WordListAdapter.OnItemClickListener {
                        override fun onItemClickListener(view: View, position: Int) {
                            val bundle = bundleOf("position" to position)
                            findNavController().navigate(
                                R.id.action_wordBookFragment_to_wordDetailsFragment,
                                bundle
                            )
                        }
                    })
                }
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

    //Room WordDatabaseに格納
    suspend fun writeWordDatabase(getData: List<WordData>) {

        withContext(Dispatchers.IO) {
            //Roomを初期化
            val db = WordDatabase.getInstance(requireContext())
            // Roomデータベースにデータを保存する
            db.wordDataDao().insertAll(getData)
        }

    }


}