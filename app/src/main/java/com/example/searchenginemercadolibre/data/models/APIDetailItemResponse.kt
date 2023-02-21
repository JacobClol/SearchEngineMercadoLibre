package com.example.searchenginemercadolibre.data.models

class APIDetailItemResponse : ArrayList<APIDetailItemResponseItem>()

data class APIDetailItemResponseItem(
    val body: Body,
    val code: Int
)

data class Body(
    val attributes: List<APIAttribute>,
    val available_quantity: Int,
    val base_price: Int,
    val buying_mode: String,
    val id: String,
    val initial_quantity: Int,
    val listing_type_id: String,
    val permalink: String,
    val pictures: List<Picture>,
    val start_time: String,
    val stop_time: String,
    val title: String
)

data class APIAttribute(
    val id: String,
    val name: String,
    val value_id: String,
    val value_name: String,
    val value_type: String,
)

data class Picture(
    val id: String,
    val max_size: String,
    val secure_url: String,
    val size: String
)
