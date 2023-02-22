package com.example.searchenginemercadolibre.data.models

import com.example.searchenginemercadolibre.domain.models.AttributesModel
import com.example.searchenginemercadolibre.domain.models.DetailItemModel
import com.example.searchenginemercadolibre.domain.models.PicturesModel
import com.google.gson.annotations.SerializedName

object APIDetailItemResponse : ArrayList<APIDetailItemResponseItem>()

data class APIDetailItemResponseItem(
    val body: Body,
    val code: Int
) {
    fun toDetailItemModel(): DetailItemModel {
        return DetailItemModel(
            attributes = body.attributes.map {
                it.toAttributesModel()
            },
            pictures = body.pictures.map {
                it.toPicturesModel()
            }
        )
    }
}

data class Body(
    val attributes: List<APIAttribute>,
    @SerializedName("available_quantity")
    val availableQuantity: Int,
    @SerializedName("base_price")
    val basePrice: Int,
    @SerializedName("buying_mode")
    val buyingMode: String,
    val id: String,
    @SerializedName("initial_quantity")
    val initialQuantity: Int,
    @SerializedName("listing_type_id")
    val listingType_id: String,
    val permalink: String,
    val pictures: List<APIPicture>,
    @SerializedName("start_time")
    val startTime: String,
    @SerializedName("stop_time")
    val stopTime: String,
    val title: String
)

data class APIAttribute(
    val id: String,
    val name: String,
    @SerializedName("value_id")
    val valueId: String,
    @SerializedName("value_name")
    val valueName: String,
    @SerializedName("value_type")
    val valueType: String
) {
    fun toAttributesModel(): AttributesModel {
        return AttributesModel(
            id = id,
            name = name,
            valueName = valueName
        )
    }
}

data class APIPicture(
    val id: String,
    @SerializedName("max_size")
    val maxSize: String,
    @SerializedName("secure_url")
    val secureUrl: String,
    val size: String
) {
    fun toPicturesModel(): PicturesModel {
        return PicturesModel(
            size = size,
            maxSize = maxSize,
            secureUrl = secureUrl
        )
    }
}
