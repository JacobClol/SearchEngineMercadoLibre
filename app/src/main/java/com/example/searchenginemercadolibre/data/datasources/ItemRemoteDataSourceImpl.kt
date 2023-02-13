package com.example.searchenginemercadolibre.data.datasources

import com.example.searchenginemercadolibre.data.models.APIItemResponse
import com.example.searchenginemercadolibre.data.services.SearchItemsService
import com.example.searchenginemercadolibre.domain.models.ItemsParams
import javax.inject.Inject

class ItemRemoteDataSourceImpl @Inject constructor(
    private val searchItemsService: SearchItemsService
) : ItemRemoteDataSource {
    override suspend fun getRemoteItemsBySearch(params: ItemsParams): APIItemResponse {
        return searchItemsService.getItems(
            params.siteId,
            params.reseach
        )
    }
}