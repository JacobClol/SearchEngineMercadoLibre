package com.example.searchenginemercadolibre.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.searchenginemercadolibre.data.database.dao.AttributeDao
import com.example.searchenginemercadolibre.data.database.dao.ItemDao
import com.example.searchenginemercadolibre.data.database.entities.AttributesEntity
import com.example.searchenginemercadolibre.data.database.entities.ItemEntity

@Database(entities = [ItemEntity::class, AttributesEntity::class], version = 1)
abstract class ItemDataBase: RoomDatabase() {
    abstract fun getItemDao(): ItemDao
    abstract fun getAttributeDao(): AttributeDao
}