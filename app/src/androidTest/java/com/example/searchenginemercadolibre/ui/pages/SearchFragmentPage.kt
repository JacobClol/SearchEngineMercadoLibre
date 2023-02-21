package com.example.searchenginemercadolibre.ui.pages

import androidx.test.core.app.launchActivity
import com.example.searchenginemercadolibre.ui.MainActivity
import com.example.searchenginemercadolibre.ui.fragment.SearchFragment
import com.example.searchenginemercadolibre.utils.Page
import com.example.searchenginemercadolibre.utils.isTextDisplayed
import com.example.searchenginemercadolibre.utils.launchFragmentInHiltContainer

class SearchFragmentPage : Page() {

    fun launchView(): SearchFragmentPage {
        launchActivity<MainActivity>()
        return this
    }

    fun launchFragmentView(): SearchFragmentPage {
        launchFragmentInHiltContainer<SearchFragment>()
        return this
    }

    fun checkDisplayTitle(): SearchFragmentPage {
        isTextDisplayed("Bienvenido al buscador de productos de Mercado Libre en Colombia")
        return this
    }

}