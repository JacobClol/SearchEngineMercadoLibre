package com.example.searchenginemercadolibre.domain.repositories

import com.example.searchenginemercadolibre.domain.models.ItemModel
import com.example.searchenginemercadolibre.domain.models.ItemsParams

interface ItemRepository {
    suspend fun getListItems(params: ItemsParams): ItemModel
}