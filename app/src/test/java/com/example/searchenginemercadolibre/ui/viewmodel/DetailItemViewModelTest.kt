package com.example.searchenginemercadolibre.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.example.searchenginemercadolibre.domain.models.AttributesModel
import com.example.searchenginemercadolibre.domain.models.DetailItemModel
import com.example.searchenginemercadolibre.domain.models.Item
import com.example.searchenginemercadolibre.domain.models.PicturesModel
import com.example.searchenginemercadolibre.domain.usecases.DeleteItemDataBaseUseCase
import com.example.searchenginemercadolibre.domain.usecases.GetDetailItemFromApiUseCase
import com.example.searchenginemercadolibre.domain.usecases.InsertItemDataBaseUseCase
import com.example.searchenginemercadolibre.utils.MainDispatcherRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailItemViewModelTest {
    @RelaxedMockK
    private lateinit var insertItemDataBaseUseCase: InsertItemDataBaseUseCase

    @RelaxedMockK
    private lateinit var deleteItemDataBaseUseCase: DeleteItemDataBaseUseCase

    @RelaxedMockK
    private lateinit var getDetailItemFromApiUseCase: GetDetailItemFromApiUseCase

    private lateinit var detailItemViewModel: DetailItemViewModel

    @get:Rule(order = 0)
    var rul: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule(order = 1)
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        val savedStateHandle = SavedStateHandle().apply {
            set("item", null)
        }
        detailItemViewModel =
            DetailItemViewModel(
                savedStateHandle,
                insertItemDataBaseUseCase,
                deleteItemDataBaseUseCase,
                getDetailItemFromApiUseCase
            )
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

    private var detailItemModelMock = DetailItemModel(
        listOf(
            AttributesModel(
                id = "ASPECT_RATIO",
                name = "Relación de aspecto",
                valueName = "16:10"
            )
        ),
        listOf(
            PicturesModel(
                size = "500x293",
                maxSize = "1017x596",
                secureUrl = "https://http2.mlstatic.com/D_801112-MLA46516512347_062021-O.jpg"
            )
        )
    )

    @Test
    fun `when DetailItemViewModel is init then call savedStateHandle and return the Item with search value`() =
        runTest {
            //Given
            val savedStateHandle = SavedStateHandle().apply {
                set("item", itemMock)
            }

            //When
            detailItemViewModel = DetailItemViewModel(
                savedStateHandle,
                insertItemDataBaseUseCase,
                deleteItemDataBaseUseCase,
                getDetailItemFromApiUseCase
            )
            //Then
            assert(savedStateHandle.get<Item>("item") == itemMock)
        }

    @Test
    fun `when DetailItemViewModel is init then call fetchDetailItem and post value to show pictures and add more attributes`() =
        runTest {
            //Given
            val savedStateHandle = SavedStateHandle().apply {
                set("item", itemMock)
            }
            coEvery {
                getDetailItemFromApiUseCase(itemMock.itemId)
            } returns detailItemModelMock

            //When
            detailItemViewModel = DetailItemViewModel(
                savedStateHandle,
                insertItemDataBaseUseCase,
                deleteItemDataBaseUseCase,
                getDetailItemFromApiUseCase
            )
            //Then
            coVerify(exactly = 1) {
                getDetailItemFromApiUseCase("MCO811601010")
            }

            assert(detailItemViewModel.itemDetail.value == itemMock)
            assert(detailItemViewModel.pictures.value == listOf("https://http2.mlstatic.com/D_801112-MLA46516512347_062021-O.jpg"))
            assert(detailItemViewModel.attributeDetail.value == detailItemModelMock.attributes)
            assert(detailItemViewModel.error.value == null)
            assert(detailItemViewModel.isLoading.value == false)
        }

    @Test
    fun `Given picture empty list when DetailItemViewModel is init then call fetchDetailItem and post value in error livedata`() =
        runTest {
            //Given
            val savedStateHandle = SavedStateHandle().apply {
                set("item", itemMock)
            }

            detailItemModelMock = detailItemModelMock.copy(pictures = listOf())

            coEvery {
                getDetailItemFromApiUseCase(itemMock.itemId)
            } returns detailItemModelMock

            //When
            detailItemViewModel = DetailItemViewModel(
                savedStateHandle,
                insertItemDataBaseUseCase,
                deleteItemDataBaseUseCase,
                getDetailItemFromApiUseCase
            )
            //Then
            coVerify(exactly = 1) {
                getDetailItemFromApiUseCase("MCO811601010")
            }

            assert(detailItemViewModel.itemDetail.value == itemMock)
            assert(detailItemViewModel.pictures.value == null)
            assert(detailItemViewModel.attributeDetail.value == detailItemModelMock.attributes)
            assert(detailItemViewModel.error.value == "No se encontraron imágenes del producto")
            assert(detailItemViewModel.isLoading.value == false)
        }

    @Test
    fun `Given a Item for save in data base when insertItemDataBaseUseCase is called then post success message`() =
        runTest {
            //When
            detailItemViewModel.insertItemDB(itemMock)

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
            //Given
            coEvery { detailItemViewModel.insertItemDB(itemMock) }.throws(NullPointerException())

            //When
            detailItemViewModel.insertItemDB(itemMock)

            //Then
            coVerify(exactly = 1) {
                insertItemDataBaseUseCase(itemMock)
            }
            coVerify(exactly = 0) {
                deleteItemDataBaseUseCase(itemMock)
            }
            assert(detailItemViewModel.error.value == "No se puede guardar el favorito")
            assert(detailItemViewModel.isLoading.value == false)
        }

    @Test
    fun `Given a Item for delete in data base when deleteItemDB is called and return exception then post error message`() =
        runTest {
            //Given
            coEvery { detailItemViewModel.deleteItemDB(itemMock) }.throws(NullPointerException())

            //When
            detailItemViewModel.deleteItemDB(itemMock)

            //Then
            coVerify(exactly = 1) {
                deleteItemDataBaseUseCase(itemMock)
            }
            coVerify(exactly = 0) {
                insertItemDataBaseUseCase(itemMock)
            }
            assert(detailItemViewModel.error.value == "No se pudo borrar el favorito")
            assert(detailItemViewModel.isLoading.value == false)
        }

}