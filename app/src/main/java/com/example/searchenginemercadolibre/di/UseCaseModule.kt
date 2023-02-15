package com.example.searchenginemercadolibre.di

import com.example.searchenginemercadolibre.domain.repositories.ItemRepository
import com.example.searchenginemercadolibre.domain.usecases.GetItemBySearchUseCase
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
    fun provideGetComicsByHeroUseCase(
        itemRepository: ItemRepository
    ) = GetItemBySearchUseCase(itemRepository)
}