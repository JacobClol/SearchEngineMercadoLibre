package com.example.searchenginemercadolibre.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchenginemercadolibre.domain.models.ItemsParams
import com.example.searchenginemercadolibre.domain.usecases.GetItemBySearchUseCase
import com.example.searchenginemercadolibre.ui.models.ItemView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getItemBySearch: GetItemBySearchUseCase
) : ViewModel() {

    val itemList = MutableLiveData<List<ItemView>>()
    val isLoading = MutableLiveData<Boolean>()
    val totalItemsResponse = MutableLiveData<String>()
    val error = MutableLiveData<String>()

    fun fetchItemList(query: String) {
        viewModelScope.launch {
            isLoading.postValue(true)
            val response = getItemBySearch(
                ItemsParams(
                    query = query
                )
            )
            if (response.itemsList.isNotEmpty()) {
                itemList.postValue(response.itemsList)
                totalItemsResponse.postValue(response.totalResults.toString())
                isLoading.postValue(false)
            } else {
                error.postValue("No se encontró items para la busqueda")
            }
        }
    }
}