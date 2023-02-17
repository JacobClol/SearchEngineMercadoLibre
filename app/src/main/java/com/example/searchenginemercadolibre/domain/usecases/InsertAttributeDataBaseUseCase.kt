package com.example.searchenginemercadolibre.domain.usecases

import com.example.searchenginemercadolibre.domain.models.AttributesModel
import com.example.searchenginemercadolibre.domain.repositories.ItemRepository
import javax.inject.Inject

class InsertAttributeDataBaseUseCase @Inject constructor(
    private val repository: ItemRepository
) {
    suspend operator fun invoke(itemId: String, attributes: List<AttributesModel>){
        repository.insertAttributesToDataBase(itemId, attributes)
    }
}