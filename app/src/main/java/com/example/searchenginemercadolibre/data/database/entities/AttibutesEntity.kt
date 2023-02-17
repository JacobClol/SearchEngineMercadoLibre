package com.example.searchenginemercadolibre.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.searchenginemercadolibre.domain.models.AttributesModel

@Entity(tableName = "attribute_table")
data class AttributesEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "id_item")
    val itemId: String,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "valueName")
    val valueName: String?
) {
    fun toAttributes(): AttributesModel {
        return AttributesModel(
            id = id,
            name = name,
            valueName = valueName
        )
    }
}

