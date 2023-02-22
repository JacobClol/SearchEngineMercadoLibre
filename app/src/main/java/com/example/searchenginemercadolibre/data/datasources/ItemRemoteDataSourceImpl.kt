package com.example.searchenginemercadolibre.data.datasources

import com.example.searchenginemercadolibre.data.models.APIDetailItemResponse
import com.example.searchenginemercadolibre.data.models.APIItemResponse
import com.example.searchenginemercadolibre.data.services.DetailItemService
import com.example.searchenginemercadolibre.data.services.SearchItemsService
import com.example.searchenginemercadolibre.domain.models.ItemParams
import javax.inject.Inject

class ItemRemoteDataSourceImpl @Inject constructor(
    private val searchItemsService: SearchItemsService,
    private val detailItemService: DetailItemService
) : ItemRemoteDataSource {
    override suspend fun getRemoteItemsBySearch(params: ItemParams): APIItemResponse {
        return searchItemsService.getItems(
            params.siteId,
            params.query
        )
    }

    override suspend fun getDetailItemsById(id: String): APIDetailItemResponse {
       return detailItemService.getDetailItem(id)
    }
}