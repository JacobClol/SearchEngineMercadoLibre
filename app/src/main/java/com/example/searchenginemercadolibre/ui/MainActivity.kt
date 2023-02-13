package com.example.searchenginemercadolibre.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.searchenginemercadolibre.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_MarvelComicsList_NoActionBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}