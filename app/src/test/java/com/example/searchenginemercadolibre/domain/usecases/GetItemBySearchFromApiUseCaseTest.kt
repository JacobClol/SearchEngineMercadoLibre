package com.example.searchenginemercadolibre.domain.usecases

import com.example.searchenginemercadolibre.domain.models.AttributesModel
import com.example.searchenginemercadolibre.domain.models.Item
import com.example.searchenginemercadolibre.domain.models.ItemModel
import com.example.searchenginemercadolibre.domain.models.ItemParams
import com.example.searchenginemercadolibre.domain.repositories.ItemRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetItemBySearchFromApiUseCaseTest {

    @RelaxedMockK
    private lateinit var itemRepository: ItemRepository

    private lateinit var getItemBySearchFromApiUseCase: GetItemBySearchFromApiUseCase

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        getItemBySearchFromApiUseCase = GetItemBySearchFromApiUseCase(itemRepository)
    }

    private  val itemMock = ItemModel(
        query = "Car",
        siteId = "MCO",
        totalResults = 1,
        listOf(
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
                attributes = listOf(
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

    private val paramsMock = ItemParams(
        query = "Car"
    )

    @Test
    fun `when the getItemBySearchFromApiUseCase is invoke then call getListItemsFromAPI and return ItemModel`() = runBlocking {
        //Given
        coEvery { itemRepository.getListItemsFromAPI(paramsMock) } returns itemMock

        //When
        val response = getItemBySearchFromApiUseCase(paramsMock)

        //Then
        coVerify(exactly = 1) { itemRepository.getListItemsFromAPI(paramsMock) }
        assert(response == itemMock)
    }
}