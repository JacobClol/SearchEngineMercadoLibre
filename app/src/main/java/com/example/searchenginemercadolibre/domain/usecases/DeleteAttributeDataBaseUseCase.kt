package com.example.searchenginemercadolibre.domain.usecases

import com.example.searchenginemercadolibre.domain.repositories.ItemRepository
import javax.inject.Inject

class DeleteAttributeDataBaseUseCase @Inject constructor(
    private val repository: ItemRepository
) {
    suspend operator fun invoke(itemId: String) {
        repository.deleteAttributesFromDataBase(itemId)
    }
}