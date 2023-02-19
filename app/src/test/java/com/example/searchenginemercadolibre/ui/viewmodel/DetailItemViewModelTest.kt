package com.example.searchenginemercadolibre.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.example.searchenginemercadolibre.domain.models.AttributesModel
import com.example.searchenginemercadolibre.domain.models.Item
import com.example.searchenginemercadolibre.domain.usecases.DeleteItemDataBaseUseCase
import com.example.searchenginemercadolibre.domain.usecases.InsertItemDataBaseUseCase
import com.example.searchenginemercadolibre.utils.MainDispatcherRule
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailItemViewModelTest {
    @RelaxedMockK
    private lateinit var insertItemDataBaseUseCase: InsertItemDataBaseUseCase

    @RelaxedMockK
    private lateinit var deleteItemDataBaseUseCase: DeleteItemDataBaseUseCase

    private lateinit var detailItemViewModel: DetailItemViewModel

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
        detailItemViewModel =
            DetailItemViewModel(savedStateHandle, insertItemDataBaseUseCase, deleteItemDataBaseUseCase)
    }

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
        attributes = listOf(
            AttributesModel(
                id = "BRAND",
                name = "Marca",
                valueName = "Samsung"
            )
        ),
        categoryId = "MCO12345"
    )

    @Test
    fun `when DetailItemViewModel is init then call savedStateHandle and return the Item with search value`() = runTest {
        //Given
        val savedStateHandle = SavedStateHandle().apply {
            set("search", itemMock)
        }
        //When
        detailItemViewModel = DetailItemViewModel(savedStateHandle, insertItemDataBaseUseCase, deleteItemDataBaseUseCase)
        //Then
        assert(savedStateHandle.get<Item>("search") == itemMock)
    }

    @Test
    fun `Given a Item for save in data base when insertItemDataBaseUseCase is called then post success message`() =
        runTest {
            //When
            detailItemViewModel.inserItemDB(itemMock)

            //Then
            coVerify(exactly = 1) {
                insertItemDataBaseUseCase(itemMock)
            }
            coVerify(exactly = 0) {
                deleteItemDataBaseUseCase(itemMock)
            }
            assert(detailItemViewModel.succesDB.value == "Favorito guardado")
            assert(detailItemViewModel.error.value == null)
            assert(detailItemViewModel.isLoading.value == false)
        }


    @Test
    fun `Given a Item for delete in data base when deleteItemDB is called then success message`() =
        runTest {
            //When
            detailItemViewModel.deleteItemDB(itemMock)

            //Then
            coVerify(exactly = 1) {
                deleteItemDataBaseUseCase(itemMock)
            }
            coVerify(exactly = 0) {
                insertItemDataBaseUseCase(itemMock)
            }
            assert(detailItemViewModel.succesDB.value == "Favorito borrado")
            assert(detailItemViewModel.error.value == null)
            assert(detailItemViewModel.isLoading.value == false)
        }

    @Test
    fun `Given a Item for save in data base when insertItemDataBaseUseCase is called and return exception then post error message`() =
        runTest {
            //When
            detailItemViewModel.inserItemDB(itemMock)

            //Then
            coVerify(exactly = 1) {
                insertItemDataBaseUseCase(itemMock)
            }
            coVerify(exactly = 0) {
                deleteItemDataBaseUseCase(itemMock)
            }
            assert(detailItemViewModel.succesDB.value == "Favorito guardado")
            assert(detailItemViewModel.error.value == null)
            assert(detailItemViewModel.isLoading.value == false)
        }

}