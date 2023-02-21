package com.example.searchenginemercadolibre.di

import com.example.searchenginemercadolibre.data.services.SearchItemsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchServiceModule {

    @Provides
    @Singleton
    fun provideSearchService(retrofit: Retrofit): SearchItemsService {
        return retrofit.create(SearchItemsService::class.java)
    }
}
