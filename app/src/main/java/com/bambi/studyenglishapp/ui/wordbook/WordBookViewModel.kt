package com.bambi.studyenglishapp.ui.wordbook

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bambi.studyenglishapp.model.WordData
import com.bambi.studyenglishapp.model.WordDataRepository
import com.bambi.studyenglishapp.ui.wordbook.WordBookFragment.Companion.ADD_DATE_ORDER
import com.bambi.studyenglishapp.ui.wordbook.WordBookFragment.Companion.ALPHABET_ORDER
import com.bambi.studyenglishapp.ui.wordbook.WordBookFragment.Companion.INCORRECT_ORDER
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WordBookViewModel(private val wordDataRepository: WordDataRepository) : ViewModel() {

    private val _wordData = MutableLiveData<List<WordData>>()
    private val _alphabetOrderData = MutableLiveData<List<WordData>>()
    private val _addDateOrderData = MutableLiveData<List<WordData>>()
    private val _incorrectOrderData = MutableLiveData<List<WordData>>()

    val wordData: LiveData<List<WordData>> get() = _wordData
    val alphabetOrderData :LiveData<List<WordData>> get() = _alphabetOrderData
    val addDateOrderData :LiveData<List<WordData>> get() = _addDateOrderData
    val incorrectOrderData :LiveData<List<WordData>> get() = _incorrectOrderData

    fun fetchWordData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val data = wordDataRepository.getAllWordData()
                _wordData.postValue(data)
            }
        }
    }

    fun sortWordData(sortType :Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                when(sortType) {
                    ADD_DATE_ORDER -> _addDateOrderData.postValue(wordDataRepository.getAddDateOrder())
                    ALPHABET_ORDER -> _alphabetOrderData.postValue(wordDataRepository.getAlphabeticalOrder())
                    //TODO:間違え順に直す
                    INCORRECT_ORDER-> _incorrectOrderData.postValue(wordDataRepository.getAlphabeticalOrder())
                }
            }
        }
    }
}