package com.example.searchenginemercadolibre.di

import com.example.searchenginemercadolibre.data.datasources.ItemRemoteDataSource
import com.example.searchenginemercadolibre.data.datasources.ItemRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Reusable
    abstract fun bindItemRemoteDatasource(
        itemRemoteDataSourceImpl: ItemRemoteDataSourceImpl
    ): ItemRemoteDataSource
}