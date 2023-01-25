package com.bambi.studyenglishapp


data class WordData(
    val japanese: String,
    val english: String,
    val sentence: String
)
data class WordList(
    val wordlist: List<WordData>
)

