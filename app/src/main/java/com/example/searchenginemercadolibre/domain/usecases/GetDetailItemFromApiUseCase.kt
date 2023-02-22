package com.example.searchenginemercadolibre.domain.usecases

import com.example.searchenginemercadolibre.domain.models.DetailItemModel
import com.example.searchenginemercadolibre.domain.repositories.ItemRepository
import javax.inject.Inject

class GetDetailItemFromApiUseCase@Inject constructor(
    private val itemRepository: ItemRepository
) {
    suspend operator fun invoke(itemId: String): DetailItemModel {
        return itemRepository.getDetailItemFromAPI(itemId)
    }
}