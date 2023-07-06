package com.bambi.studyenglishapp.ui.wordbook

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bambi.studyenglishapp.model.WordData
import com.bambi.studyenglishapp.model.WordDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WordBookViewModel(private val wordDataRepository: WordDataRepository) : ViewModel() {

        private val _wordData = MutableLiveData<List<WordData>>()
        val wordData: LiveData<List<WordData>> get() = _wordData

        fun fetchWordData() {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    val data = wordDataRepository.getAllWordData()
                    _wordData.postValue(data)
                }
            }
        }
}