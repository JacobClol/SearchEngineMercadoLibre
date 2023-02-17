package com.example.searchenginemercadolibre.data.models

import com.example.searchenginemercadolibre.domain.models.AttributesModel
import com.example.searchenginemercadolibre.domain.models.Item
import com.example.searchenginemercadolibre.domain.models.ItemModel
import com.google.gson.annotations.SerializedName

data class APIItemResponse(
    val country_default_time_zone: String,
    val paging: Paging,
    val query: String,
    val results: List<Result>,
    val site_id: String
) {
    fun toItemModel(): ItemModel =
        ItemModel(
            query = query,
            siteId = site_id,
            totalResults = paging.totalResults,
            items = results.map {
                it.toItems()
            }
        )
}

data class Paging(
    val limit: Int,
    val offset: Int,
    val primary_results: Int,
    @SerializedName("total")
    val totalResults: Int
)

data class Result(
    @SerializedName("accepts_mercadopago")
    val acceptsMercadoPago: Boolean,
    val address: Address,
    val attributes: List<Attribute>,
    @SerializedName("available_quantity")
    val availableQuantity: Int,
    @SerializedName("buying_mode")
    val buyingMode: String,
    @SerializedName("catalog_listing")
    val catalogListing: Boolean,
    @SerializedName("catalog_product_id")
    val catalogProductId: String,
    @SerializedName("category_id")
    val categoryId: String,
    val condition: String,
    @SerializedName("currency_id")
    val currencyId: String,
    val discounts: String?,
    val domain_id: String,
    val id: String,
    val price: Int,
    val promotions: String?,
    @SerializedName("sale_price")
    val salePrice: String?,
    val seller: Seller,
    val seller_address: SellerAddress,
    val shipping: Shipping,
    @SerializedName("site_id")
    val siteId: String,
    @SerializedName("sold_quantity")
    val soldQuantity: Int,
    @SerializedName("stop_time")
    val stopTime: String,
    val tags: List<String>?,
    val thumbnail: String,
    @SerializedName("thumbnail_id")
    val thumbnailId: String,
    val title: String,
    @SerializedName("use_thumbnail_id")
    val useThumbnailId: Boolean,
    @SerializedName("winner_item_id")
    val winnerItemId: String?
) {
    fun toItems(): Item {
        return Item(
            itemId = id,
            title = title,
            sellerStatus = seller.sellerReputation.sellerStatus,
            price = price,
            currencyId = currencyId,
            availableQuantity = availableQuantity,
            soldQuantity = soldQuantity,
            condition = condition,
            thumbnail = thumbnail.replace("http:", "https:"),
            cityName = address.city_name,
            stateName = address.state_name,
            freeShipping = shipping.freeShipping,
            countrySellerName = seller_address.country.name,
            attributes = attributes.map {
                it.toAttributesModel()
            },
            categoryId = categoryId
        )
    }
}

data class Address(
    val city_id: String,
    val city_name: String,
    val state_id: String,
    val state_name: String
)

data class Attribute(
    @SerializedName("attribute_group_id")
    val attributeGroupId: String,
    @SerializedName("attribute_group_name")
    val attributeGroupName: String,
    val id: String,
    val name: String?,
    val source: String,
    @SerializedName("value_id")
    val valueId: String,
    @SerializedName("value_name")
    val valueName: String?,
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

data class Installments(
    val amount: Double,
    @SerializedName("currency_id")
    val currencyId: String,
    val quantity: Int,
    val rate: Int
)

data class Seller(
    @SerializedName("car_dealer")
    val carDealer: Boolean,
    @SerializedName("car_dealer_logo")
    val carDealerLogo: String,
    val id: Int,
    val nickname: String,
    val permalink: String,
    @SerializedName("real_estate_agency")
    val realEstateAgency: Boolean,
    val registration_date: String,
    @SerializedName("seller_reputation")
    val sellerReputation: SellerReputation,
    val tags: List<String>?
)

data class SellerAddress(
    @SerializedName("address_line")
    val addressLine: String,
    val comment: String,
    val country: Country,
    val id: String?,
    val latitude: String?,
    val longitude: String?,
    @SerializedName("zip_code")
    val zipCode: String
)

data class Shipping(
    @SerializedName("free_shipping")
    val freeShipping: Boolean,
    @SerializedName("logistic_type")
    val logisticType: String,
    val mode: String,
    val promise: String?,
    @SerializedName("store_pick_up")
    val storePickUp: Boolean,
    val tags: List<String>
)

data class Value(
    val id: String,
    val name: String?,
    val source: String,
    val struct: Any?
)

data class SellerReputation(
    @SerializedName("level_id")
    val levelId: String,
    @SerializedName("power_seller_status")
    val sellerStatus: String?
)

data class Metrics(
    val cancellations: Cancellations,
    val claims: Claims,
    @SerializedName("delayed_handling_time")
    val delayedHandlingTime: DelayedHandlingTime,
    val sales: Sales
)

data class Transactions(
    val canceled: Int,
    val completed: Int,
    val period: String,
    val ratings: Ratings,
    val total: Int
)

data class Cancellations(
    val period: String,
    val rate: Double,
    val value: Int
)

data class Claims(
    val period: String,
    val rate: Double,
    val value: Int
)

data class DelayedHandlingTime(
    val period: String,
    val rate: Double,
    val value: Int
)

data class Sales(
    val completed: Int,
    val period: String
)

data class Ratings(
    val negative: Double,
    val neutral: Double,
    val positive: Double
)

data class City(
    val id: String,
    val name: String
)

data class Country(
    val id: String,
    val name: String
)

data class State(
    val id: String,
    val name: String
)