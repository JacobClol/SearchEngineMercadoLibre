package com.example.searchenginemercadolibre.di

import com.example.searchenginemercadolibre.domain.repositories.ItemRepository
import com.example.searchenginemercadolibre.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Reusable
    fun provideGetComicsByHeroFromApiUseCase(
        itemRepository: ItemRepository
    ) = GetItemBySearchFromApiUseCase(itemRepository)

    @Provides
    @Reusable
    fun privideInsertItemToDataBase(
        itemRepository: ItemRepository,
        insertAttributeDataBaseUseCase: InsertAttributeDataBaseUseCase
    ) = InsertItemDataBaseUseCase(itemRepository, insertAttributeDataBaseUseCase)

    @Provides
    @Reusable
    fun privideDeteleItemToDataBase(
        itemRepository: ItemRepository,
        deleteAttributeDataBaseUseCase: DeleteAttributeDataBaseUseCase
    ) = DeleteItemDataBaseUseCase(itemRepository, deleteAttributeDataBaseUseCase)

    @Provides
    @Reusable
    fun privideDeteleAttributeToDataBase(
        itemRepository: ItemRepository
    ) = DeleteAttributeDataBaseUseCase(itemRepository)

    @Provides
    @Reusable
    fun privideInsertAttributeToDataBase(
        itemRepository: ItemRepository
    ) = InsertAttributeDataBaseUseCase(itemRepository)

    @Provides
    @Reusable
    fun privideGetItemWithAttributtesFromDataBase(
        itemRepository: ItemRepository
    ) = GetItemWithAttibutesDataBaseUseCase(itemRepository)

}