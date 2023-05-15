package com.bambi.studyenglishapp

import retrofit2.http.GET

interface ItemInterface {

    @GET(BuildConfig.API_KEY)
    suspend fun getWordList(): WordList
}