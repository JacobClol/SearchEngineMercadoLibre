package com.example.searchenginemercadolibre.domain.repositories

import com.example.searchenginemercadolibre.domain.models.AttributesModel
import com.example.searchenginemercadolibre.domain.models.Item
import com.example.searchenginemercadolibre.domain.models.ItemModel
import com.example.searchenginemercadolibre.domain.models.ItemParams

interface ItemRepository {
    suspend fun getListItemsFromAPI(params: ItemParams): ItemModel

    suspend fun insertItemsToDataBase(item: Item)

    suspend fun deleteItemsFromDataBase(item: Item)

    suspend fun insertAttributesToDataBase(itemId: String, attributes: List<AttributesModel>)

    suspend fun deleteAttributesFromDataBase(itemId: String)

    suspend fun getItemsWithAttributesFromDataBase(): List<Item>
}