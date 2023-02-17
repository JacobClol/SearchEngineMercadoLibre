package com.example.searchenginemercadolibre.data.datasources

import com.example.searchenginemercadolibre.data.database.entities.AttributesEntity
import com.example.searchenginemercadolibre.data.database.entities.ItemEntity
import com.example.searchenginemercadolibre.data.database.entities.ItemWithAttribute

interface ItemDataBaseDataSource {

    suspend fun saveItem(item: ItemEntity)

    suspend fun deleteItem(item: ItemEntity)

    suspend fun saveAttributes(attributes: List<AttributesEntity>)

    suspend fun deleteAttributesByIdItem(itemId: String)

    suspend fun getItemWithAttributes(): List<ItemWithAttribute>
}