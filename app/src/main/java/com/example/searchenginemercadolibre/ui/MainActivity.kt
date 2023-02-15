package com.example.searchenginemercadolibre.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.searchenginemercadolibre.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_SearchEngineMercadoLibre_NoActionBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}