package com.example.searchenginemercadolibre.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchenginemercadolibre.domain.models.AttributesModel
import com.example.searchenginemercadolibre.domain.models.Item
import com.example.searchenginemercadolibre.domain.usecases.DeleteItemDataBaseUseCase
import com.example.searchenginemercadolibre.domain.usecases.GetDetailItemFromApiUseCase
import com.example.searchenginemercadolibre.domain.usecases.InsertItemDataBaseUseCase
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailItemViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val insertItemDataBaseUseCase: InsertItemDataBaseUseCase,
    private val deleteItemDataBaseUseCase: DeleteItemDataBaseUseCase,
    private val getDetailItemFromApiUseCase: GetDetailItemFromApiUseCase
) : ViewModel() {

    val succesDB = MutableLiveData<String>()
    val error = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>()
    val itemDetail = MutableLiveData<Item>()
    val pictures = MutableLiveData<List<String>>()
    val attributeDetail = MutableLiveData<List<AttributesModel>>()

    init {
        val itemArgs: Item? = savedStateHandle["item"]
        itemArgs?.let {
            itemDetail.postValue(it)
            fetchDetailItem(it.itemId)
        }
    }

    private fun fetchDetailItem(itemId: String) {
        viewModelScope.launch {
            isLoading.postValue(true)
            val response = getDetailItemFromApiUseCase(itemId)
            if (response.pictures.isNotEmpty()) {
                val listPictures: List<String> = response.pictures.map {
                    it.secureUrl
                }
                pictures.postValue(listPictures)
            } else {
                error.postValue("No se encontraron imágenes del producto")
            }
            if (response.attributes.isNotEmpty()) {
                attributeDetail.postValue(response.attributes)
            }
            isLoading.postValue(false)
        }
    }

    fun insertItemDB(item: Item) {
        viewModelScope.launch {
            isLoading.postValue(true)
            try {
                insertItemDataBaseUseCase(item)
                succesDB.postValue("Favorito guardado")
            } catch (e: Exception) {
                error.postValue("No se puede guardar el favorito")
//                Firebase.crashlytics.recordException(e)
            }
            isLoading.postValue(false)
        }
    }

    fun deleteItemDB(item: Item) {
        viewModelScope.launch {
            isLoading.postValue(true)
            try {
                deleteItemDataBaseUseCase(item)
                succesDB.postValue("Favorito borrado")
            } catch (e: Exception) {
                error.postValue("No se pudo borrar el favorito")
     //           Firebase.crashlytics.recordException(e)
            }
            isLoading.postValue(false)
        }
    }
}