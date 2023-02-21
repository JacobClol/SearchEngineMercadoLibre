package com.example.searchenginemercadolibre.data.repositories

import com.example.searchenginemercadolibre.data.database.entities.AttributesEntity
import com.example.searchenginemercadolibre.data.database.entities.ItemEntity
import com.example.searchenginemercadolibre.data.database.entities.ItemWithAttribute
import com.example.searchenginemercadolibre.data.datasources.ItemDataBaseDataSource
import com.example.searchenginemercadolibre.data.datasources.ItemRemoteDataSource
import com.example.searchenginemercadolibre.data.datasources.models.*
import com.example.searchenginemercadolibre.domain.models.AttributesModel
import com.example.searchenginemercadolibre.domain.models.Item
import com.example.searchenginemercadolibre.domain.models.ItemModel
import com.example.searchenginemercadolibre.domain.models.ItemParams
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetItemRepositoryImplTest {
    @RelaxedMockK
    private lateinit var itemRemoteDataSource: ItemRemoteDataSource

    @RelaxedMockK
    private lateinit var itemDataBaseDataSource: ItemDataBaseDataSource

    private lateinit var getItemRepositoryImpl: GetItemRepositoryImpl

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
                _root_ide_package_.com.example.searchenginemercadolibre.data.models.Result(
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

    private val itemModelMock = ItemModel(
        query = "Car",
        siteId = "MCO",
        totalResults = 1,
        arrayListOf(
            Item(
                itemId = "MCO811601010",
                title = "Samsung Galaxy J4+ Dual Sim 32 Gb Negro (2 Gb Ram)",
                sellerStatus = null,
                price = 10000,
                currencyId = "COP",
                availableQuantity = 1,
                soldQuantity = 1,
                condition = "new",
                thumbnail = "http://mco-s1-p.mlstatic.com/943469-MLA31002769183_062019-I.jpg",
                cityName = "Bogotá",
                stateName = "Cundinamarca",
                freeShipping = true,
                countrySellerName = "Colombia",
                attributes = arrayListOf(
                    AttributesModel(
                        id = "BRAND",
                        name = "Marca",
                        valueName = "Samsung"
                    )
                ),
                categoryId = "MCO12345"
            )
        )
    )

    private val itemMock = Item(
        itemId = "MCO811601010",
        title = "Samsung Galaxy J4+ Dual Sim 32 Gb Negro (2 Gb Ram)",
        sellerStatus = null,
        price = 10000,
        currencyId = "COP",
        availableQuantity = 1,
        soldQuantity = 1,
        condition = "new",
        thumbnail = "http://mco-s1-p.mlstatic.com/943469-MLA31002769183_062019-I.jpg",
        cityName = "Bogotá",
        stateName = "Cundinamarca",
        freeShipping = true,
        countrySellerName = "Colombia",
        attributes = arrayListOf(
            AttributesModel(
                id = "BRAND",
                name = "Marca",
                valueName = "Samsung"
            )
        ),
        categoryId = "MCO12345",
        isSaveDB = true
    )

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getItemRepositoryImpl =
            GetItemRepositoryImpl(itemRemoteDataSource, itemDataBaseDataSource)
    }

    @Test
    fun `Given a ItemParams when getListItemsFromAPI is called then should call getRemoteItemsBySearch return ItemModel`() =
        runBlocking {
            //Given
            val itemParamsMock = ItemParams(
                query = "Car"
            )

            coEvery {
                itemRemoteDataSource.getRemoteItemsBySearch(itemParamsMock)
            } returns apiItemResponseMock

            //When
            val response = getItemRepositoryImpl.getListItemsFromAPI(itemParamsMock)

            //then
            coVerify(exactly = 1) {
                itemRemoteDataSource.getRemoteItemsBySearch(itemParamsMock)
            }
            assert(response == apiItemResponseMock.toItemModel())
        }

    @Test
    fun `Given Item when insertItemsToDataBase is called then should call saveItem to save in data base`() =
        runBlocking {
            //When
            getItemRepositoryImpl.insertItemsToDataBase(itemMock)

            //Then
            coVerify(exactly = 1) {
                itemDataBaseDataSource.saveItem(itemMock.toItemEntity())
            }
        }

    @Test
    fun `Given a Item when delteItemsFromDataBase is called then should call deleteItem to delte in data base`() =
        runBlocking {
            //When
            getItemRepositoryImpl.deleteItemsFromDataBase(itemMock)

            //then
            coVerify(exactly = 1) {
                itemDataBaseDataSource.deleteItem(itemMock.toItemEntity())
            }
        }

    @Test
    fun `Given a attributes when insertAttributesToDataBase is called then should call saveAttributes to save in data base`() =
        runBlocking {
            //Given
            val itemIdMock = "MCO811601010"
            val attributesModelMock = listOf(
                AttributesModel(
                    id = "BRAND",
                    name = "Marca",
                    valueName = "Samsung"
                )
            )
            //When
            getItemRepositoryImpl.insertAttributesToDataBase(itemIdMock, attributesModelMock)

            //Then
            coVerify(exactly = 1) {
                itemDataBaseDataSource.saveAttributes(attributesModelMock.map {
                    it.toAttributeEntity(itemIdMock)
                })
            }
        }

    @Test
    fun `Given a itemId when deleteAttributesFromDataBase is called then should call delteAttributesByIdItem to delete in data base`() =
        runBlocking {
            //Given
            val itemIdMock = "MCO123453"

            //When
            getItemRepositoryImpl.deleteAttributesFromDataBase(itemIdMock)

            //Then
            coVerify(exactly = 1) {
                itemDataBaseDataSource.deleteAttributesByIdItem(itemIdMock)
            }
        }

    @Test
    fun `when getItemsWithAttributesFromDataBase is called then should return a list of Item`() =
        runBlocking {
            //Given
            val itemWithAttributeMock = ItemWithAttribute(
                ItemEntity(
                    itemId = "MCO811601010",
                    title = "Samsung Galaxy J4+ Dual Sim 32 Gb Negro (2 Gb Ram)",
                    sellerStatus = null,
                    price = 10000,
                    currencyId = "COP",
                    availableQuantity = 1,
                    soldQuantity = 1,
                    condition = "new",
                    thumbnail = "http://mco-s1-p.mlstatic.com/943469-MLA31002769183_062019-I.jpg",
                    cityName = "Bogotá",
                    stateName = "Cundinamarca",
                    freeShipping = true,
                    countrySellerName = "Colombia",
                    categoryId = "MCO12345",
                    isSaveDB = true
                ),
                attributes = listOf(
                    AttributesEntity(
                        id = "BRAND",
                        itemId = "MCO811601010",
                        name = "Marca",
                        valueName = "Samsung"
                    )
                )
            )
            coEvery { itemDataBaseDataSource.getItemWithAttributes() } returns listOf(
                itemWithAttributeMock
            )
            //When
            val response = getItemRepositoryImpl.getItemsWithAttributesFromDataBase()
            //Then
            coVerify {
                itemDataBaseDataSource.getItemWithAttributes()
            }
            assert(response == listOf(itemMock))
        }

}