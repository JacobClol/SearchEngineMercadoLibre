package com.example.searchenginemercadolibre.ui.fragment

import androidx.test.espresso.intent.Intents
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.searchenginemercadolibre.data.services.SearchItemsService
import com.example.searchenginemercadolibre.ui.di.stubGetItems
import com.example.searchenginemercadolibre.ui.pages.SearchFragmentPage
import com.example.searchenginemercadolibre.utils.Page.Companion.on
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class SearchFragmentTest {

    @Inject lateinit var searchItemsService: SearchItemsService

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun init() {
        hiltRule.inject()
    }

    

    @Test
    fun shouldSeeTitleInitAndListFavoritoInSearchFragment(){
        searchItemsService.stubGetItems()
        on<SearchFragmentPage>()
            .launchView()
    }

}