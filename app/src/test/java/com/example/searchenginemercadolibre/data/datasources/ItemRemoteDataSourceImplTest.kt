package com.example.searchenginemercadolibre.data.datasources

import com.example.searchenginemercadolibre.data.models.*
import com.example.searchenginemercadolibre.data.services.DetailItemService
import com.example.searchenginemercadolibre.data.services.SearchItemsService
import com.example.searchenginemercadolibre.domain.models.ItemParams
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class ItemRemoteDataSourceImplTest {
    @RelaxedMockK
    private lateinit var searchItemsService: SearchItemsService

    @RelaxedMockK
    private lateinit var detailItemService: DetailItemService

    private lateinit var itemRemoteDataSourceImpl: ItemRemoteDataSourceImpl

    private val apiItemResponseMock =
        APIItemResponse(
            country_default_time_zone = "String",
            paging = Paging(
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
                    seller = Seller(
                        carDealer = false,
                        carDealerLogo = "",
                        id = 1,
                        nickname = "",
                        permalink = "",
                        realEstateAgency = false,
                        registration_date = "",
                        sellerReputation = SellerReputation(
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
                    address = Address(
                        city_id = "",
                        city_name = "Bogotá",
                        state_id = "",
                        state_name = "Cundinamarca"
                    ),
                    shipping = Shipping(
                        freeShipping = true,
                        logisticType = "",
                        mode = "",
                        promise = "",
                        storePickUp = false,
                        tags = listOf()
                    ),
                    seller_address = SellerAddress(
                        addressLine = "",
                        comment = "",
                        country = Country(
                            id = "",
                            name = "Colombia"
                        ),
                        id = "",
                        latitude = "",
                        longitude = "",
                        zipCode = ""
                    ),
                    attributes = listOf(
                        Attribute(
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

    private val aPIDetailItemResponse = APIDetailItemResponse

    private val aPIDetailItemResponseMock = APIDetailItemResponseItem(
        body = Body(
            attributes = listOf(
                APIAttribute(
                    id = "ASPECT_RATIO",
                    name = "Relación de aspecto",
                    valueId = "String",
                    valueName = "String",
                    valueType = "16:10"
                )
            ),
            availableQuantity = 1,
            basePrice = 1,
            buyingMode = "String",
            id = "String",
            initialQuantity = 1,
            listingType_id = "String",
            permalink = "String",
            pictures = listOf(
                APIPicture(
                    id = "String",
                    maxSize = "500x293",
                    secureUrl = "1017x596",
                    size = "https://http2.mlstatic.com/D_801112-MLA46516512347_062021-O.jpg"
                )
            ),
            startTime = "String",
            stopTime = "String",
            title = "String"
        ),
        code = 1
    )

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        itemRemoteDataSourceImpl = ItemRemoteDataSourceImpl(searchItemsService, detailItemService)
    }

    @Test
    fun `Given ItemParams when getRemoteItemsBySearch is called then should call getItems from service and return APIItemResponse`() =
        runBlocking {
            //Given
            val itemParamsMock = ItemParams(
                query = "Car"
            )
            coEvery {
                searchItemsService.getItems(itemParamsMock.siteId, itemParamsMock.query)
            } returns apiItemResponseMock

            //When
            val response = itemRemoteDataSourceImpl.getRemoteItemsBySearch(itemParamsMock)

            //Then
            coVerify(exactly = 1) {
                searchItemsService.getItems(itemParamsMock.siteId, itemParamsMock.query)
            }

            assert(response == apiItemResponseMock)
        }

    @Test
    fun `Given itemId when getDetailItemsById is called then should call getDetailItem from service and return APIDetailItemResponse`() =
        runBlocking {
            //Given
            val itemIdMock = "MCO811601010"
            aPIDetailItemResponse.add(aPIDetailItemResponseMock)

            coEvery {
                itemRemoteDataSourceImpl.getDetailItemsById(itemIdMock)
            } returns aPIDetailItemResponse

            //When
            val response = itemRemoteDataSourceImpl.getDetailItemsById(itemIdMock)

            coVerify(exactly = 1) {
                detailItemService.getDetailItem(itemIdMock)
            }

            assert(response == aPIDetailItemResponse)
        }
}