package com.example.searchenginemercadolibre.data.datasources

import com.example.searchenginemercadolibre.data.database.dao.AttributeDao
import com.example.searchenginemercadolibre.data.database.dao.ItemDao
import com.example.searchenginemercadolibre.data.database.entities.AttributesEntity
import com.example.searchenginemercadolibre.data.database.entities.ItemEntity
import com.example.searchenginemercadolibre.data.database.entities.ItemWithAttribute
import javax.inject.Inject

class ItemDataBaseDataSourceImpl @Inject constructor(
    private val itemDao: ItemDao,
    private val attributeDao: AttributeDao
) : ItemDataBaseDataSource {
    override suspend fun saveItem(item: ItemEntity) {
        itemDao.insertItem(item)
    }

    override suspend fun deleteItem(item: ItemEntity) {
        itemDao.deleteItem(item)
    }

    override suspend fun saveAttributes(attributes: List<AttributesEntity>) {
        attributeDao.insertAll(attributes)
    }

    override suspend fun deleteAttributesByIdItem(itemId: String) {
        attributeDao.deleteAttributeByIdItem(itemId)
    }

    override suspend fun getItemWithAttributes(): List<ItemWithAttribute> {
        return itemDao.getItemsWithAttributes()
    }
}