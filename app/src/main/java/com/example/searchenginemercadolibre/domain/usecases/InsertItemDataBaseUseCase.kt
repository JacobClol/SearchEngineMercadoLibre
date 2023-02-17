package com.example.searchenginemercadolibre.domain.usecases

import com.example.searchenginemercadolibre.domain.models.Item
import com.example.searchenginemercadolibre.domain.repositories.ItemRepository
import javax.inject.Inject

class InsertItemDataBaseUseCase @Inject constructor(
    private val itemRepository: ItemRepository,
    private val insertAttributeDataBaseUseCase: InsertAttributeDataBaseUseCase
) {
    suspend operator fun invoke(item: Item){
        itemRepository.insertItemsToDataBase(item)
        item.attributes?.let {
            insertAttributeDataBaseUseCase.invoke(item.itemId, it)
        }
    }
}