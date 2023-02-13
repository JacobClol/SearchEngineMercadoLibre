package com.example.searchenginemercadolibre.data.datasources

import com.example.searchenginemercadolibre.data.models.APIItemResponse
import com.example.searchenginemercadolibre.domain.models.ItemsParams

interface ItemRemoteDataSource {
    suspend fun getRemoteItemsBySearch(params: ItemsParams): APIItemResponse
}