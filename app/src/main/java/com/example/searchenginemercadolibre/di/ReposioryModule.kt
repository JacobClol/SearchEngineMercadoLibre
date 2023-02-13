package com.example.searchenginemercadolibre.di

import com.example.searchenginemercadolibre.data.repositories.GetItemRepositoryImpl
import com.example.searchenginemercadolibre.domain.repositories.ItemRepository
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ReposioryModule {

    @Binds
    @Reusable
    abstract fun bindComicRepository(
        getItemRepositoryImpl: GetItemRepositoryImpl
    ): ItemRepository
}