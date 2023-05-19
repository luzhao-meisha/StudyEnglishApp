package com.bambi.studyenglishapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.bambi.studyenglishapp.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

lateinit var service: ItemInterface

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //API通信
        val baseApiUrl = "https://sheets.googleapis.com/v4/spreadsheets/"

        val httpLogging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClientBuilder = OkHttpClient.Builder().addInterceptor(httpLogging)

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseApiUrl)
            .client(httpClientBuilder.build())
            .build()


        service = retrofit.create(ItemInterface::class.java)

        // 非同期処理でデータを取得する
        lifecycleScope.launch {
            //データ取得
            val getData = getData()

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
                if(index == 0) return@forEachIndexed //1列目はkeyのためは格納しない
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


    //Room WordDatabaseに格納
    private suspend fun writeWordDatabase(getData: List<WordData>) {

        withContext(Dispatchers.IO) {
            //Roomを初期化
            val db = WordDatabase.getInstance(this@MainActivity)
            // Roomデータベースにデータを保存する
            db.wordDataDao().insertAll(getData)
        }
    }

}