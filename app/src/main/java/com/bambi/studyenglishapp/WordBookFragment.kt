package com.bambi.studyenglishapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bambi.studyenglishapp.databinding.FragmentWordBookBinding
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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
                val demo: WordList? = response.body()
            }

            override fun onFailure(call: Call<WordList?>, t: Throwable) {

            }
        })
        return view
    }
}

