package com.example.searchenginemercadolibre.domain.models


data class DetailItemModel (
    val attributes: List<AttributesModel>,
    val pictures: List<PicturesModel>
)

data class PicturesModel(
    val size: String,
    val maxSize: String,
    val secureUrl: String
)