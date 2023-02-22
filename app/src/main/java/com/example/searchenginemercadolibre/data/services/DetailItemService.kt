package com.example.searchenginemercadolibre.data.services

import com.example.searchenginemercadolibre.data.models.APIDetailItemResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DetailItemService {

    @GET("items")
    suspend fun getDetailItem(
        @Query("ids") id: String
    ): APIDetailItemResponse
}