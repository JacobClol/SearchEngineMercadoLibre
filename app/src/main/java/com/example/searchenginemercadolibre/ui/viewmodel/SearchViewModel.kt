package com.example.searchenginemercadolibre.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchenginemercadolibre.domain.models.Item
import com.example.searchenginemercadolibre.domain.models.ItemParams
import com.example.searchenginemercadolibre.domain.usecases.GetItemBySearchFromApiUseCase
import com.example.searchenginemercadolibre.domain.usecases.GetItemWithAttibutesDataBaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getItemBySearch: GetItemBySearchFromApiUseCase,
    private val getItemWithAttibutesDataBaseUseCase: GetItemWithAttibutesDataBaseUseCase
) : ViewModel() {

    val itemListRemote = MutableLiveData<List<Item>>()
    val itemListLocal =  MutableLiveData<List<Item>>()
    val isLoading = MutableLiveData<Boolean>()
    val totalItemsResponse = MutableLiveData<String>()
    val error = MutableLiveData<String>()

    init {
        val query: String? = savedStateHandle["search"]
        if(!query.isNullOrEmpty()){
            fetchItemList(query)
        }
    }

    fun fetchItemList(query: String) {
        viewModelScope.launch {
            isLoading.postValue(true)
            val response = getItemBySearch(
                ItemParams(
                    query = query
                )
            )
            if (response.items.isNotEmpty()) {
                itemListRemote.postValue(response.items)
                totalItemsResponse.postValue(response.totalResults.toString())
            } else {
               // Firebase.crashlytics.log("No found item by search")
                error.postValue("No se encontró items para la busqueda")
            }
            isLoading.postValue(false)
        }
    }

    fun getItemFromDataBase() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val items = getItemWithAttibutesDataBaseUseCase()
            if (items.isNotEmpty()) {
                itemListLocal.postValue(items)
                totalItemsResponse.postValue(items.size.toString())
            } else {
                itemListLocal.postValue(listOf())
                totalItemsResponse.postValue("0")
            }
            isLoading.postValue(false)
        }
    }
}