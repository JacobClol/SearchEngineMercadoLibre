package com.example.searchenginemercadolibre.ui.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemView(
    val itemId: String,
    val title: String,
    val sellerStaus: String?,
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
    val attributes: List<AttributesView>,
    val categoryId: String
) : Parcelable

@Parcelize
data class AttributesView(
    val id: String,
    val name: String?,
    val valueName: String?,
) : Parcelable
