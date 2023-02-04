package com.bambi.studyenglishapp

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bambi.studyenglishapp.adapter.WordListAdapter
import com.bambi.studyenglishapp.databinding.FragmentWordBookBinding
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.*

interface ItemInterface {

    @GET(BuildConfig.API_KEY)
    fun items(): Call<WordList>
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


        service.items().enqueue(object : Callback<WordList> {
            override fun onResponse(call: Call<WordList?>, response: retrofit2.Response<WordList?>) {

                if (response.isSuccessful) {
                    response.body()?.let {

                        //データ取得
                        val demo = response.body()?.values

                        //リストに格納
                        val wordDataList = mutableListOf<WordData>()
                        for (i in demo!!.iterator().withIndex()) {
                            wordDataList.add(i.index, WordData(i.value[0], i.value[1], i.value[2]))
                        }
                        Log.d("d", wordDataList.toString())

                        //RecyclerViewで表示

                        val wordrv = binding.wordRv
                        wordrv.adapter = WordListAdapter(wordDataList)
                        wordrv.layoutManager = LinearLayoutManager(requireContext())


                    }
                }

            }

            override fun onFailure(call: Call<WordList?>, t: Throwable) {

            }
        })
        return view
    }
}

