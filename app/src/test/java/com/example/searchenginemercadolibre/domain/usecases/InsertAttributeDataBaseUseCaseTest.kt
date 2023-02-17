package com.example.searchenginemercadolibre.domain.usecases

import com.example.searchenginemercadolibre.domain.models.AttributesModel
import com.example.searchenginemercadolibre.domain.repositories.ItemRepository
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class InsertAttributeDataBaseUseCaseTest {
    @RelaxedMockK
    private lateinit var itemRepository: ItemRepository

    private lateinit var insertAttributeDataBaseUseCase: InsertAttributeDataBaseUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        insertAttributeDataBaseUseCase = InsertAttributeDataBaseUseCase(itemRepository)
    }

    @Test
    fun `when insertAttributeDataBaseUseCase is invoke then call insertAttributesToDataBase with the list attributes`() =
        runBlocking {
            //Given
            val itemId = "MCO811601010"
            val attributes = listOf(
                AttributesModel(
                    id = "BRAND",
                    name = "Marca",
                    valueName = "Samsung"
                )
            )
            //When
            insertAttributeDataBaseUseCase(itemId, attributes)

            //Then
            coVerify(exactly = 1) {
                itemRepository.insertAttributesToDataBase(itemId, attributes)
            }
        }
}