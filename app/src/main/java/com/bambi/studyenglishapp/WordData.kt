package com.bambi.studyenglishapp


data class WordData(
    val english: String,
    val japanese: String,
    val sentence: String
)
data class WordList(
    val values: List<List<String>>
)
