package com.example.searchenginemercadolibre.domain.usecases

import com.example.searchenginemercadolibre.domain.models.ItemModel
import com.example.searchenginemercadolibre.domain.models.ItemParams
import com.example.searchenginemercadolibre.domain.repositories.ItemRepository
import javax.inject.Inject

class GetItemBySearchFromApiUseCase @Inject constructor(
    private val itemRepository: ItemRepository
) {
    suspend operator fun invoke(params: ItemParams): ItemModel {
        return itemRepository.getListItemsFromAPI(params)
    }
}