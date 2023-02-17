package com.example.searchenginemercadolibre.domain.usecases

import com.example.searchenginemercadolibre.domain.repositories.ItemRepository
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DeleteAttributeDataBaseUseCaseTest {

    @RelaxedMockK
    private lateinit var itemRepository: ItemRepository

    private lateinit var deleteAttributeDataBaseUseCase: DeleteAttributeDataBaseUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        deleteAttributeDataBaseUseCase = DeleteAttributeDataBaseUseCase(itemRepository)
    }

    @Test
    fun `when the deleteAttributeDataBaseUseCase is invoke then call deleteAttributesFromDataBase from repository`() =
        runBlocking {
            //Given
            val itemId = "MCO811601010"

            //When
            deleteAttributeDataBaseUseCase(itemId)

            //Then
            coVerify(exactly = 1) { itemRepository.deleteAttributesFromDataBase(itemId) }
        }
}