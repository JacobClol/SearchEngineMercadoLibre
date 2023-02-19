package com.example.searchenginemercadolibre.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.example.searchenginemercadolibre.domain.models.AttributesModel
import com.example.searchenginemercadolibre.domain.models.Item
import com.example.searchenginemercadolibre.domain.models.ItemModel
import com.example.searchenginemercadolibre.domain.models.ItemParams
import com.example.searchenginemercadolibre.domain.usecases.GetItemBySearchFromApiUseCase
import com.example.searchenginemercadolibre.domain.usecases.GetItemWithAttibutesDataBaseUseCase
import com.example.searchenginemercadolibre.utils.MainDispatcherRule
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {
    @RelaxedMockK
    private lateinit var getItemBySearch: GetItemBySearchFromApiUseCase

    @RelaxedMockK
    private lateinit var getItemWithAttibutesDataBaseUseCase: GetItemWithAttibutesDataBaseUseCase

    private lateinit var searchViewModel: SearchViewModel

    @get:Rule
    var rul: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        val savedStateHandle = SavedStateHandle().apply {
            set("search", null)
        }
        searchViewModel = SearchViewModel(savedStateHandle, getItemBySearch, getItemWithAttibutesDataBaseUseCase)
    }

    private var itemMock = ItemModel(
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

    private  val itemParamsMock = ItemParams(
        query = "Motorola"
    )

    @Test
    fun `when SearchViewModel is init then call savedStateHandle and return string with search value`() = runTest {
       //Given
        val savedStateHandle = SavedStateHandle().apply {
            set("search", "Motorola")
        }
        //When
        searchViewModel = SearchViewModel(savedStateHandle, getItemBySearch, getItemWithAttibutesDataBaseUseCase)
        //Then
        assert(savedStateHandle.get<String>("search") == "Motorola")
    }


    @Test
    fun `when SearchViewModel is created and call fetchItemList then should get a ItemModel and set the values`() =
        runTest {
            //Given
            coEvery { getItemBySearch(itemParamsMock) } returns itemMock

            //When
            searchViewModel.fetchItemList("Motorola")

            //Then
            coVerify {
                getItemBySearch(itemParamsMock)
            }

            coVerify(exactly = 0) {
                getItemWithAttibutesDataBaseUseCase()
            }

            assert(searchViewModel.itemListRemote.value == itemMock.items)
            assert(searchViewModel.totalItemsResponse.value == itemMock.totalResults.toString())
            assert(searchViewModel.error.value == null)
            assert(searchViewModel.isLoading.value == false)
        }

    @Test
    fun `when SearchViewModel is created and call fetchItemList and return list empty then should post error`() =
        runTest {
            //Given
            itemMock = itemMock.copy(items = listOf())

            coEvery { getItemBySearch(itemParamsMock) } returns itemMock

            //When
            searchViewModel.fetchItemList("Motorola")

            //Then
            coVerify(exactly = 1) {
                getItemBySearch(itemParamsMock)
            }
            coVerify(exactly = 0) {
                getItemWithAttibutesDataBaseUseCase()
            }
            assert(searchViewModel.error.value == "No se encontró items para la busqueda")
            assert(searchViewModel.itemListRemote.value == null)
            assert(searchViewModel.totalItemsResponse.value == null)
            assert(searchViewModel.isLoading.value == false)
        }

    @Test
    fun `when SearchViewModel is created and call getItemFromDataBase then should get list item and set the values`() =
        runTest {
            //Given
            coEvery { getItemWithAttibutesDataBaseUseCase() } returns itemMock.items

            //When
            searchViewModel.getItemFromDataBase()

            //Then
            coVerify(exactly = 1) {
                getItemWithAttibutesDataBaseUseCase()
            }
            coVerify(exactly = 0) {
                getItemBySearch(itemParamsMock)
            }
            assert(searchViewModel.itemListLocal.value == itemMock.items)
            assert(searchViewModel.totalItemsResponse.value == itemMock.items.size.toString())
            assert(searchViewModel.error.value == null)
            assert(searchViewModel.isLoading.value == false)
        }

    @Test
    fun `when SearchViewModel is created, call getItemFromDataBase and return list empty then should post empty list and total item equal 0`() =
        runTest {
            //Given
            coEvery { getItemWithAttibutesDataBaseUseCase() } returns listOf()
            itemMock = itemMock.copy(items = listOf())

            //When
            searchViewModel.getItemFromDataBase()

            //Then
            coVerify(exactly = 1) {
                getItemWithAttibutesDataBaseUseCase()
            }
            coVerify(exactly = 0) {
                getItemBySearch(itemParamsMock)
            }
            assert(searchViewModel.itemListLocal.value == itemMock.items)
            assert(searchViewModel.totalItemsResponse.value == "0")
            assert(searchViewModel.error.value == null)
            assert(searchViewModel.isLoading.value == false)
        }

}