package com.example.searchenginemercadolibre.domain.models

import com.example.searchenginemercadolibre.ui.models.AttributesView
import com.example.searchenginemercadolibre.ui.models.ItemView
import com.example.searchenginemercadolibre.ui.models.ResponseView

data class ItemModel(
    val query: String,
    val siteId: String,
    val totalResults: Int,
    val items: List<Item>
) {
    fun toResponseView(): ResponseView {
        return ResponseView(
            query = query,
            siteId = siteId,
            totalResults = totalResults,
            itemsList = items.map {
                it.toItemView()
            }
        )
    }
}

data class Item(
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
    val attributes: List<Attributes>,
    val categoryId: String
) {
    fun toItemView(): ItemView {
        return ItemView(
            itemId = itemId,
            title = title,
            sellerStaus = sellerStaus,
            price = price,
            currencyId = currencyId,
            availableQuantity = availableQuantity,
            soldQuantity = soldQuantity,
            condition = condition,
            thumbnail = thumbnail,
            cityName = cityName,
            stateName = stateName,
            freeShipping = freeShipping,
            countrySellerName = countrySellerName,
            attributes = attributes.map {
                it.toAttributeView()
            },
            categoryId = categoryId
        )
    }
}

data class Attributes(
    val id: String,
    val name: String?,
    val valueName: String?,
) {
    fun toAttributeView(): AttributesView {
        return AttributesView(
            id = id,
            name = name,
            valueName = valueName
        )
    }
}