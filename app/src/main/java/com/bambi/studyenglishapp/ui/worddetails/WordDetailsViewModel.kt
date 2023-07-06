package com.bambi.studyenglishapp.ui.worddetails

import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bambi.studyenglishapp.model.WordDataRepository
import com.bambi.studyenglishapp.model.WordDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WordDetailsViewModel(private val wordDataRepository: WordDataRepository) : ViewModel() {
    private val _word = MutableLiveData<String>()
    val word: LiveData<String> get() = _word

    private val _meaning = MutableLiveData<String>()
    val meaning: LiveData<String> get() = _meaning

    private val _sentence = MutableLiveData<String>()
    val sentence: LiveData<String> get() = _sentence

    fun loadDetails(position: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val word = wordDataRepository.getJapaneseNameById(position)
                val meaning = wordDataRepository.getEnglishNameById(position)
                val sentence = wordDataRepository.getSentenceById(position)

                _word.postValue(word)
                _meaning.postValue(meaning)
                _sentence.postValue(sentence)
            }
        }
    }
}
