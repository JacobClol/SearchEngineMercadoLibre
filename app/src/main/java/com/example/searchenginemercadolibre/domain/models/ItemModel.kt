package com.example.searchenginemercadolibre.domain.models

import android.os.Parcelable
import com.example.searchenginemercadolibre.data.database.entities.AttributesEntity
import com.example.searchenginemercadolibre.data.database.entities.ItemEntity
import kotlinx.parcelize.Parcelize

data class ItemModel(
    val query: String,
    val siteId: String,
    val totalResults: Int,
    val items: List<Item>
)

@Parcelize
data class Item(
    val itemId: String,
    val title: String,
    val sellerStatus: String?,
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
    val attributes: List<AttributesModel>?,
    val categoryId: String,
    val isSaveDB: Boolean = false
) : Parcelable {
    fun toItemEntity(): ItemEntity {
        return ItemEntity(
            itemId = itemId,
            title = title,
            sellerStatus = sellerStatus,
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
            categoryId = categoryId,
            isSaveDB = true
        )
    }
}

@Parcelize
data class AttributesModel(
    val id: String,
    val name: String?,
    val valueName: String?,
) : Parcelable {
    fun toAttributeEntity(itemId: String): AttributesEntity{
        return AttributesEntity(
            id,
            itemId,
            name,
            valueName
        )
    }
}