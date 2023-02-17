package com.example.searchenginemercadolibre.data.database.entities

import androidx.room.Embedded
import androidx.room.Relation
import com.example.searchenginemercadolibre.domain.models.Item

data class ItemWithAttribute(
    @Embedded val item: ItemEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id_item"
    )
    val attributes: List<AttributesEntity>
) {
    fun toItems(): Item {
        return Item(
            itemId = item.itemId,
            title = item.title,
            sellerStatus = item.sellerStatus,
            price = item.price,
            currencyId = item.currencyId,
            availableQuantity = item.availableQuantity,
            soldQuantity = item.soldQuantity,
            condition = item.condition,
            thumbnail = item.thumbnail,
            cityName = item.cityName,
            stateName = item.stateName,
            freeShipping = item.freeShipping,
            countrySellerName = item.countrySellerName,
            attributes = attributes.map {
                it.toAttributes()
            },
            categoryId = item.categoryId,
            isSaveDB = item.isSaveDB
        )
    }
}