package com.example.searchenginemercadolibre.di

import android.content.Context
import androidx.room.Room
import com.example.searchenginemercadolibre.data.database.ItemDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    const val ITEM_DATABASE_NAME = "item_database"

    @Provides
    @Singleton
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, ItemDataBase::class.java, ITEM_DATABASE_NAME).build()

    @Provides
    @Singleton
    fun provideItemDato(db: ItemDataBase) = db.getItemDao()

    @Provides
    @Singleton
    fun provideAttributeDato(db: ItemDataBase) = db.getAttributeDao()
}