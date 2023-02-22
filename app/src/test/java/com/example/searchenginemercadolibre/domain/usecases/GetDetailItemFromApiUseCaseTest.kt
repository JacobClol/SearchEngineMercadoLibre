package com.example.searchenginemercadolibre.domain.usecases

import com.example.searchenginemercadolibre.domain.models.AttributesModel
import com.example.searchenginemercadolibre.domain.models.DetailItemModel
import com.example.searchenginemercadolibre.domain.models.PicturesModel
import com.example.searchenginemercadolibre.domain.repositories.ItemRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetDetailItemFromApiUseCaseTest {

    @RelaxedMockK
    private lateinit var itemRepository: ItemRepository

    private lateinit var getDetailItemFromApiUseCase: GetDetailItemFromApiUseCase

    private val detailItemModelMock = DetailItemModel(
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

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getDetailItemFromApiUseCase = GetDetailItemFromApiUseCase(itemRepository)
    }

    @Test
    fun `when getDetailItemFromApiUseCase is invoke then call getDetailItemFromAPI and return DetailItemModel`() = runBlocking {
        //Given
        val itemIdMock = "MCO811601010"
        coEvery { itemRepository.getDetailItemFromAPI(itemIdMock) } returns detailItemModelMock

        //When
        val response = getDetailItemFromApiUseCase(itemIdMock)

        //Then
        coVerify(exactly = 1) { itemRepository.getDetailItemFromAPI(itemIdMock) }
        assert(response == detailItemModelMock)
    }
}