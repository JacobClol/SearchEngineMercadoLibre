package com.example.searchenginemercadolibre.domain.usecases

import com.example.searchenginemercadolibre.domain.models.ItemModel
import com.example.searchenginemercadolibre.domain.models.ItemsParams
import com.example.searchenginemercadolibre.domain.repositories.ItemRepository
import javax.inject.Inject

class GetItemBySearch @Inject constructor(
    private val itemRepository: ItemRepository
) {
    suspend operator fun invoke(params: ItemsParams): ItemModel {
        return itemRepository.getListItems(params)
    }
}