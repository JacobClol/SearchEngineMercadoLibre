package com.example.searchenginemercadolibre.domain.usecases

import com.example.searchenginemercadolibre.domain.models.ItemsParams
import com.example.searchenginemercadolibre.domain.repositories.ItemRepository
import com.example.searchenginemercadolibre.ui.models.ResponseView
import javax.inject.Inject

class GetItemBySearchUseCase @Inject constructor(
    private val itemRepository: ItemRepository
) {
    suspend operator fun invoke(params: ItemsParams): ResponseView {
        return itemRepository.getListItems(params).toResponseView()
    }
}