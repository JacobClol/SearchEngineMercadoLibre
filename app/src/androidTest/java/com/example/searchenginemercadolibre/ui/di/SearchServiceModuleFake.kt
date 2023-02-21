package com.example.searchenginemercadolibre.ui.di

import com.example.searchenginemercadolibre.data.datasources.models.*
import com.example.searchenginemercadolibre.data.models.*
import com.example.searchenginemercadolibre.data.services.SearchItemsService
import com.example.searchenginemercadolibre.di.SearchServiceModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.coEvery
import io.mockk.mockk
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [SearchServiceModule::class]
)
object SearchServiceModuleFake {

    @Provides
    @Singleton
    fun provideSearchService(): SearchItemsService = mockk()
}

fun SearchItemsService.stubGetItems(){
    coEvery { getItems(any(), any()) } answers { apiItemResponseMock }
}

private val apiItemResponseMock =
    _root_ide_package_.com.example.searchenginemercadolibre.data.models.APIItemResponse(
        country_default_time_zone = "String",
        paging = _root_ide_package_.com.example.searchenginemercadolibre.data.models.Paging(
            limit = 1,
            offset = 1,
            primary_results = 1,
            totalResults = 1
        ),
        query = "Car",
        results = listOf(
            Result(
                acceptsMercadoPago = false,
                id = "MCO811601010",
                title = "Samsung Galaxy J4+ Dual Sim 32 Gb Negro (2 Gb Ram)",
                seller = _root_ide_package_.com.example.searchenginemercadolibre.data.models.Seller(
                    carDealer = false,
                    carDealerLogo = "",
                    id = 1,
                    nickname = "",
                    permalink = "",
                    realEstateAgency = false,
                    registration_date = "",
                    sellerReputation = _root_ide_package_.com.example.searchenginemercadolibre.data.models.SellerReputation(
                        levelId = "",
                        sellerStatus = null
                    ),
                    tags = listOf()
                ),
                price = 10000,
                currencyId = "COP",
                availableQuantity = 1,
                soldQuantity = 1,
                condition = "new",
                thumbnail = "http://mco-s1-p.mlstatic.com/943469-MLA31002769183_062019-I.jpg",
                address = _root_ide_package_.com.example.searchenginemercadolibre.data.models.Address(
                    city_id = "",
                    city_name = "Bogotá",
                    state_id = "",
                    state_name = "Cundinamarca"
                ),
                shipping = _root_ide_package_.com.example.searchenginemercadolibre.data.models.Shipping(
                    freeShipping = true,
                    logisticType = "",
                    mode = "",
                    promise = "",
                    storePickUp = false,
                    tags = listOf()
                ),
                seller_address = _root_ide_package_.com.example.searchenginemercadolibre.data.models.SellerAddress(
                    addressLine = "",
                    comment = "",
                    country = _root_ide_package_.com.example.searchenginemercadolibre.data.models.Country(
                        id = "",
                        name = "Colombia"
                    ),
                    id = "",
                    latitude = "",
                    longitude = "",
                    zipCode = ""
                ),
                attributes = listOf(
                    _root_ide_package_.com.example.searchenginemercadolibre.data.models.Attribute(
                        attributeGroupId = "",
                        attributeGroupName = "",
                        id = "BRAND",
                        name = "Marca",
                        source = "",
                        valueId = "",
                        valueName = "Samsung",
                        valueType = ""
                    )
                ),
                categoryId = "MCO12345",
                buyingMode = "",
                catalogListing = false,
                catalogProductId = "",
                discounts = "",
                domain_id = "",
                promotions = "",
                salePrice = "",
                siteId = "",
                winnerItemId = "",
                thumbnailId = "",
                useThumbnailId = false,
                stopTime = "",
                tags = listOf()
            )
        ),
        site_id = "MCO"
    )