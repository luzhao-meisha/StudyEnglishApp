package com.bambi.studyenglishapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.bambi.studyenglishapp.ItemInterface
import com.bambi.studyenglishapp.model.WordData
import com.bambi.studyenglishapp.model.WordDatabase
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

            //Roomデータベースにデータが存在しない場合のみ追加する
            if (getData.isNotEmpty()) {
                //Roomにデータ書き込み
                writeWordDatabase(getData)
            }
        }
    }

    //データ取得
    private suspend fun getData(): List<WordData> {
        return try {
            withContext(Dispatchers.IO) {
                val wordList = service.getWordList().values

                // SpreadsheetDataオブジェクトのリストに変換する
                val dataList = mutableListOf<WordData>()
                wordList.forEachIndexed { index, row ->
                    if (index == 0) return@forEachIndexed //1列目はkeyのため格納しない
                    val data = WordData(
                        id = index.toLong(),
                        english = row.getOrNull(0) ?: "",
                        japanese = row.getOrNull(1) ?: "",
                        sentence = row.getOrNull(2) ?: "",
                        date = row.getOrNull(3) ?: "",
                    )
                    dataList.add(data)
                }

                dataList
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    //Room WordDatabaseに格納
    private suspend fun writeWordDatabase(getData: List<WordData>) {
        withContext(Dispatchers.IO) {
            //Roomを初期化
            val db = WordDatabase.getInstance(this@MainActivity)

            //dbから既存のデータを取得
            val existingData = db.wordDataDao().getAllWordData()

            //既存データのIDをSetに変換
            val existingIds = existingData.map { it.id }.toSet()

            //APIデータをループして差分のみを取得し、answersを除外して新しいデータのリストを作成
            val newData = getData.filter { apiItem ->
                apiItem.id !in existingIds
            }.map { apiItem ->
                apiItem.copy(answers = null) // answersをnullに設定して新しいデータを作成
            }

            //新しいデータをdbに追加する
            db.wordDataDao().insertAll(newData)
        }
    }
}