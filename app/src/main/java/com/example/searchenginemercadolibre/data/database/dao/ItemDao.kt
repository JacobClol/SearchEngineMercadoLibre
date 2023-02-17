package com.example.searchenginemercadolibre.data.database.dao

import androidx.room.Insert
import androidx.room.Delete
import androidx.room.Transaction
import androidx.room.Dao
import androidx.room.Query
import androidx.room.OnConflictStrategy
import com.example.searchenginemercadolibre.data.database.entities.ItemEntity
import com.example.searchenginemercadolibre.data.database.entities.ItemWithAttribute

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(items: ItemEntity)

    @Delete
    suspend fun deleteItem(item: ItemEntity)

    @Transaction
    @Query("SELECT * FROM item_table")
    suspend fun getItemsWithAttributes(): List<ItemWithAttribute>
}