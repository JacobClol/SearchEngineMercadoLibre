package com.example.searchenginemercadolibre.data.datasources

import com.example.searchenginemercadolibre.data.database.dao.AttributeDao
import com.example.searchenginemercadolibre.data.database.dao.ItemDao
import com.example.searchenginemercadolibre.data.database.entities.AttributesEntity
import com.example.searchenginemercadolibre.data.database.entities.ItemEntity
import com.example.searchenginemercadolibre.data.database.entities.ItemWithAttribute
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class ItemDataBaseDataSourceImplTest {
    @RelaxedMockK
    private lateinit var itemDao: ItemDao

    @RelaxedMockK
    private lateinit var attributeDao: AttributeDao

    private lateinit var itemDataBaseDataSourceImpl: ItemDataBaseDataSourceImpl

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        itemDataBaseDataSourceImpl = ItemDataBaseDataSourceImpl(itemDao, attributeDao)
    }

    private val itemEntityMock = ItemEntity(
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
    )

    private val attributeEntityMock = arrayListOf(
        AttributesEntity(
            id = "BRAND",
            itemId = "MCO811601010",
            name = "Marca",
            valueName = "Samsung"
        )
    )

    private val listItemWithAttributeEntityMock = listOf(
        ItemWithAttribute(
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
    )

    @Test
    fun `when saveItem is called to save item in data base then should call insertItem`() =
        runBlocking {
            //When
            itemDataBaseDataSourceImpl.saveItem(itemEntityMock)

            //Then
            coVerify(exactly = 1) {
                itemDao.insertItem(itemEntityMock)
            }
        }

    @Test
    fun `when deleteItem is called to delete item in data base then should call deleteItem`() =
        runBlocking {
            //When
            itemDataBaseDataSourceImpl.deleteItem(itemEntityMock)

            //Then
            coVerify(exactly = 1) {
                itemDao.deleteItem(itemEntityMock)
            }
        }

    @Test
    fun `when saveAttributes is called to save attibutes in data base then should call insertAll`() =
        runBlocking {
            //When
            itemDataBaseDataSourceImpl.saveAttributes(attributeEntityMock)

            //Then
            coVerify(exactly = 1) {
                attributeDao.insertAll(attributeEntityMock)
            }
        }

    @Test
    fun `when deleteAttributesByIdItem is called to delete attibutes in data base then should call deleteAttributeByIdItem`() =
        runBlocking {
            //Given
            val itemIdMock = "MCO811601010"

            //When
            itemDataBaseDataSourceImpl.deleteAttributesByIdItem(itemIdMock)

            //Then
            coVerify(exactly = 1) {
                attributeDao.deleteAttributeByIdItem(itemIdMock)
            }
        }

    @Test
    fun `when getItemWithAttributes is called to get list of items with attributes in data base then should call getItemsWithAttributes and return list of ItemWithAttribute`() =
        runBlocking {
            //Given
            coEvery { itemDao.getItemsWithAttributes() } returns listItemWithAttributeEntityMock

            //When
            val response = itemDataBaseDataSourceImpl.getItemWithAttributes()

            //Then
            coVerify(exactly = 1) {
                itemDao.getItemsWithAttributes()
            }

            assert(response == listItemWithAttributeEntityMock)
        }
}