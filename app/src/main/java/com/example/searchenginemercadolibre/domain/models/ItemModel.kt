package com.example.searchenginemercadolibre.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class ItemModel(
    val query: String, val siteId: String, val totalResults: Int, val items: List<Item>
)

@Parcelize
data class Item(
    val itemId: String,
    val title: String,
    val sellerStaus: String,
    val price: Int,
    val currencyId: String,
    val availableQuantity: Int,
    val soldQuantity: Int,
    val condition: String,
    val thumbnail: String,
    val cityName: String,
    val stateName: String,
    val freeShipping: Boolean,
    val countrySellerName: String,
    val attributes: List<Attributes>,
    val categoryId: String
) : Parcelable

@Parcelize
data class Attributes(
    val id: String,
    val name: String,
    val valueName: String,
) : Parcelable