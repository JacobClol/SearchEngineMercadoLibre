package com.example.searchenginemercadolibre.domain.usecases

import com.example.searchenginemercadolibre.domain.models.Item
import com.example.searchenginemercadolibre.domain.repositories.ItemRepository
import javax.inject.Inject

class DeleteItemDataBaseUseCase @Inject constructor(
    private val repository: ItemRepository,
    private val deleteAttributeDataBaseUseCase: DeleteAttributeDataBaseUseCase
) {
    suspend operator fun invoke(item: Item) {
        deleteAttributeDataBaseUseCase.invoke(item.itemId)
        repository.deleteItemsFromDataBase(item)
    }
}