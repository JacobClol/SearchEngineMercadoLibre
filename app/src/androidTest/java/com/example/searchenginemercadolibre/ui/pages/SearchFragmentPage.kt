package com.example.searchenginemercadolibre.ui.pages

import androidx.fragment.app.testing.launchFragmentInContainer
import com.example.searchenginemercadolibre.ui.fragment.SearchFragment
import com.example.searchenginemercadolibre.utils.Page
import com.example.searchenginemercadolibre.utils.isTextDisplayed

class SearchFragmentPage : Page() {

    fun launchView(): SearchFragmentPage {
        launchFragmentInContainer<SearchFragment>()
        return this
    }

    fun checkDisplayTitle(): SearchFragmentPage {
        isTextDisplayed("Bienvenido al buscador de productos de Mercado Libre en Colombia")
        return this
    }

}