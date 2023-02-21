package com.example.searchenginemercadolibre.data.services

import com.example.searchenginemercadolibre.data.models.APIItemResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SearchItemsService {

    @GET("sites/{SITE_ID}/search")
    suspend fun getItems(
        @Path("SITE_ID") name: String,
        @Query("q") query: String
    ): APIItemResponse

}