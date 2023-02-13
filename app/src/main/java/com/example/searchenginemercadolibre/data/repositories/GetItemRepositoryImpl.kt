package com.example.searchenginemercadolibre.data.repositories

import com.example.searchenginemercadolibre.data.datasources.ItemRemoteDataSource
import com.example.searchenginemercadolibre.domain.models.ItemModel
import com.example.searchenginemercadolibre.domain.models.ItemsParams
import com.example.searchenginemercadolibre.domain.repositories.ItemRepository
import javax.inject.Inject

class GetItemRepositoryImpl @Inject constructor(
    private val itemRemoteDataSource: ItemRemoteDataSource
) : ItemRepository {
    override suspend fun getListItems(params: ItemsParams): ItemModel {
        return itemRemoteDataSource.getRemoteItemsBySearch(params).toItemModel()
    }
}