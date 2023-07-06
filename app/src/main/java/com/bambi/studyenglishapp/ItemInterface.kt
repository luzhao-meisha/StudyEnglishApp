package com.bambi.studyenglishapp

import com.bambi.studyenglishapp.model.WordList
import retrofit2.http.GET

interface ItemInterface {
    @GET(BuildConfig.API_KEY)
    suspend fun getWordList(): WordList
}