package com.example.searchenginemercadolibre.domain.repositories

import com.example.searchenginemercadolibre.domain.models.*

interface ItemRepository {
    suspend fun getListItemsFromAPI(params: ItemParams): ItemModel

    suspend fun getDetailItemFromAPI(itemId: String): DetailItemModel

    suspend fun insertItemsToDataBase(item: Item)

    suspend fun deleteItemsFromDataBase(item: Item)

    suspend fun insertAttributesToDataBase(itemId: String, attributes: List<AttributesModel>)

    suspend fun deleteAttributesFromDataBase(itemId: String)

    suspend fun getItemsWithAttributesFromDataBase(): List<Item>
}