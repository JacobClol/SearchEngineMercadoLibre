package com.example.searchenginemercadolibre.ui.models

data class ResponseView(
    val query: String,
    val siteId: String,
    val totalResults: Int,
    val itemsList: List<ItemView>
)
