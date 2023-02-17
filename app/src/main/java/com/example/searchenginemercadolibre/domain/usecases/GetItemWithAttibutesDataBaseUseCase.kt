package com.example.searchenginemercadolibre.domain.usecases

import com.example.searchenginemercadolibre.domain.models.Item
import com.example.searchenginemercadolibre.domain.repositories.ItemRepository
import javax.inject.Inject

class GetItemWithAttibutesDataBaseUseCase @Inject constructor(
    private val repository: ItemRepository
) {
    suspend operator fun invoke(): List<Item> {
       return repository.getItemsWithAttributesFromDataBase()
    }
}