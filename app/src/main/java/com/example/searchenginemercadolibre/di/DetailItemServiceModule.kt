package com.example.searchenginemercadolibre.di

import com.example.searchenginemercadolibre.data.services.DetailItemService
import com.example.searchenginemercadolibre.data.services.SearchItemsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DetailItemServiceModule {

    @Provides
    @Singleton
    fun provideDetailItemService(retrofit: Retrofit): DetailItemService {
        return retrofit.create(DetailItemService::class.java)
    }
}
