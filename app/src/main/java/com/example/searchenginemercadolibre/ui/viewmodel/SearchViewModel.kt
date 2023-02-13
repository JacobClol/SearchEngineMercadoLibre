package com.example.searchenginemercadolibre.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.searchenginemercadolibre.domain.models.ItemModel
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class SearchViewModel : ViewModel() {

    val itemList = MutableLiveData<ItemModel>()

    fun fetchItemList(){

    }
}