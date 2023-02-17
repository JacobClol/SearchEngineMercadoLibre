package com.example.searchenginemercadolibre.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item_table")
data class ItemEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val itemId: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "sellerStatus")
    val sellerStatus: String?,
    @ColumnInfo(name = "price")
    val price: Int,
    @ColumnInfo(name = "currencyId")
    val currencyId: String,
    @ColumnInfo(name = "availableQuantity")
    val availableQuantity: Int,
    @ColumnInfo(name = "soldQuantity")
    val soldQuantity: Int,
    @ColumnInfo(name = "condition")
    val condition: String,
    @ColumnInfo(name = "thumbnail")
    val thumbnail: String,
    @ColumnInfo(name = "cityName")
    val cityName: String,
    @ColumnInfo(name = "stateName")
    val stateName: String,
    @ColumnInfo(name = "freeShipping")
    val freeShipping: Boolean,
    @ColumnInfo(name = "countrySellerName")
    val countrySellerName: String,
    @ColumnInfo(name = "categoryId")
    val categoryId: String,
    @ColumnInfo(name = "is_save")
    var isSaveDB: Boolean
)

