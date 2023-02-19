package com.example.searchenginemercadolibre.data.datasources

import com.example.searchenginemercadolibre.data.datasources.models.APIItemResponse
import com.example.searchenginemercadolibre.domain.models.ItemParams

interface ItemRemoteDataSource {
    suspend fun getRemoteItemsBySearch(params: ItemParams): APIItemResponse
}