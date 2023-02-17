package com.example.searchenginemercadolibre.domain.usecases

import com.example.searchenginemercadolibre.domain.models.AttributesModel
import com.example.searchenginemercadolibre.domain.models.Item
import com.example.searchenginemercadolibre.domain.repositories.ItemRepository
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class InsertItemDataBaseUseCaseTest {
    @RelaxedMockK
    private lateinit var itemRepository: ItemRepository

    @RelaxedMockK
    private lateinit var insertAttributeDataBaseUseCase: InsertAttributeDataBaseUseCase

    private lateinit var insertItemDataBaseUseCase: InsertItemDataBaseUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        insertItemDataBaseUseCase =
            InsertItemDataBaseUseCase(itemRepository, insertAttributeDataBaseUseCase)
    }

    @Test
    fun `when insertItemDataBaseUseCase is invoke then call insertItemsToDataBase and invoke insertAttributeDataBaseUseCase`() =
        runBlocking {
            //Given
            val itemMock = Item(
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
                attributes = listOf(
                    AttributesModel(
                        id = "BRAND",
                        name = "Marca",
                        valueName = "Samsung"
                    )
                ),
                categoryId = "MCO12345"
            )

            //When
            insertItemDataBaseUseCase(itemMock)

            //Then
            coVerify(exactly = 1) {
                itemRepository.insertItemsToDataBase(itemMock)
                itemMock.attributes?.let { insertAttributeDataBaseUseCase(itemMock.itemId, it) }
            }
        }
}