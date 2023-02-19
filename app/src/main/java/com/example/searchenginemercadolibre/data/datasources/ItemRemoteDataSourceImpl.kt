package com.example.searchenginemercadolibre.data.datasources

import com.example.searchenginemercadolibre.data.datasources.models.APIItemResponse
import com.example.searchenginemercadolibre.data.services.SearchItemsService
import com.example.searchenginemercadolibre.domain.models.ItemParams
import javax.inject.Inject

class ItemRemoteDataSourceImpl @Inject constructor(
    private val searchItemsService: SearchItemsService
) : ItemRemoteDataSource {
    override suspend fun getRemoteItemsBySearch(params: ItemParams): APIItemResponse {
        return searchItemsService.getItems(
            params.siteId,
            params.query
        )
    }
}