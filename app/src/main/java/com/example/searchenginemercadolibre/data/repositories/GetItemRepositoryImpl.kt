package com.example.searchenginemercadolibre.data.repositories

import com.example.searchenginemercadolibre.data.datasources.ItemDataBaseDataSource
import com.example.searchenginemercadolibre.data.datasources.ItemRemoteDataSource
import com.example.searchenginemercadolibre.domain.models.AttributesModel
import com.example.searchenginemercadolibre.domain.models.Item
import com.example.searchenginemercadolibre.domain.models.ItemModel
import com.example.searchenginemercadolibre.domain.models.ItemParams
import com.example.searchenginemercadolibre.domain.repositories.ItemRepository
import javax.inject.Inject

class GetItemRepositoryImpl @Inject constructor(
    private val itemRemoteDataSource: ItemRemoteDataSource,
    private val itemDataBaseDataSource: ItemDataBaseDataSource
) : ItemRepository {
    override suspend fun getListItemsFromAPI(params: ItemParams): ItemModel {
        return itemRemoteDataSource.getRemoteItemsBySearch(params).toItemModel()
    }

    override suspend fun insertItemsToDataBase(item: Item) {
        itemDataBaseDataSource.saveItem(item.toItemEntity())
    }

    override suspend fun deleteItemsFromDataBase(item: Item) {
        itemDataBaseDataSource.deleteItem(item.toItemEntity())
    }

    override suspend fun insertAttributesToDataBase(itemId:String, attributes: List<AttributesModel>) {
        itemDataBaseDataSource.saveAttributes(attributes.map {
            it.toAttributeEntity(itemId)
        })
    }

    override suspend fun deleteAttributesFromDataBase(itemId: String) {
        itemDataBaseDataSource.deleteAttributesByIdItem(itemId)
    }

    override suspend fun getItemsWithAttributesFromDataBase(): List<Item> {
        return itemDataBaseDataSource.getItemWithAttributes().map {
            it.toItems()
        }
    }
}