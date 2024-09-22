package com.pankajjangid.cryptotest.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pankajjangid.cryptotest.R
import com.pankajjangid.cryptotest.local.entity.CurrencyEntity
import com.pankajjangid.cryptotest.local.repository.CurrencyRepository
import com.pankajjangid.cryptotest.utils.Constant.CURRENCY_TYPE_CRYPTO
import com.pankajjangid.cryptotest.utils.Constant.CURRENCY_TYPE_FIAT
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Collections.emptyList


class CurrencyViewModel(val repository: CurrencyRepository) : ViewModel() {

    companion object {
        const val TAG = "CurrencyViewModel"
    }

    private var _message = MutableStateFlow<Int?>(null)
    var message = _message.asStateFlow()

    var currencySetA: List<CurrencyEntity> = ArrayList()
    var currencySetB: List<CurrencyEntity> = ArrayList()

    private var _progress = MutableStateFlow(false)
    var progress = _progress.asStateFlow()

    private val _searchText = MutableStateFlow("")
    var searchText = _searchText.asStateFlow()

    var _currencyList = MutableStateFlow(emptyList<CurrencyEntity>())


    // For clear the search query
    fun clearSearch() {
        _searchText.value = ""
    }

    //Perform search
    val currency = searchText
        .combine(_currencyList) { text, currency ->
            if (text.isBlank()) {
                currency
            } else {
                currency.filter {
                    it.doesMatchSearchQuery(text)
                }
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _currencyList.value
        )

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }


    // Get the currency list for UI
    fun getCurrency() = _currencyList


    // Insert the data
    fun insertToDatabase() {
        shouldShowLoader(true)

        try {
            viewModelScope.launch {
                repository.deleteAll()
                currencySetA.onEach { it.type = CURRENCY_TYPE_CRYPTO }
                currencySetB.onEach { it.type = CURRENCY_TYPE_FIAT }

                repository.insertAll(currencySetA + currencySetB)
                shouldShowLoader(false)
                _message.update { R.string.insert_success }
            }

        } catch (error: Exception) {
            shouldShowLoader(false)
            _message.update { R.string.insert_failure }

            Log.e(TAG, error.message.toString())
        }
    }

    // Clear the database
    fun clearDB() {
        shouldShowLoader(true)

        viewModelScope.launch {
            try {
                repository.deleteAll()
                _message.update { R.string.clear_success }

                loadAllCurrencyFromDatabase()
                shouldShowLoader(false)
            } catch (error: Exception) {
                Log.e(TAG, error.message.toString())
                shouldShowLoader(false)
                _message.update { R.string.clear_failure }

            }

        }

    }


    // Load the currency list by it's type
    fun loadCurrencyListByType(type: String) {


        try {
            shouldShowLoader(true)

            viewModelScope.launch {

            _currencyList.update {
                    repository.getCurrencyListByType(type).stateIn(viewModelScope).value
                }
                shouldShowLoader(false)

            }
        } catch (error: Exception) {
            Log.e(TAG, error.message.toString())
            shouldShowLoader(false)

        }


    }

    // Load all the currency list
    fun loadAllCurrencyFromDatabase() {
        shouldShowLoader(true)

        try {
            viewModelScope.launch {
                _currencyList.update {
                    repository.getAllCurrencyInfoFromDb().stateIn(viewModelScope).value
                }
                shouldShowLoader(false)

            }
        } catch (error: Exception) {
            Log.e(TAG, error.message.toString())
            shouldShowLoader(false)

        }

    }

    // Show the loader
    private fun shouldShowLoader(state: Boolean) {
        _progress.update { state }

    }

    // Clear the error message
    fun clearErrorMessage() {
        _message.update { null }
    }

}
