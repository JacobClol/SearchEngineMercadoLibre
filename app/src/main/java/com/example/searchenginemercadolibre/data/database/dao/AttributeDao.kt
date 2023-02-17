package com.example.searchenginemercadolibre.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.searchenginemercadolibre.data.database.entities.AttributesEntity

@Dao
interface AttributeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(attributes: List<AttributesEntity>)

    @Query("DELETE FROM attribute_table WHERE id_item == :idItem")
    suspend fun deleteAttributeByIdItem(idItem: String)
}