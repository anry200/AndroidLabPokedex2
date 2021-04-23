package com.example.androidlabpokedex2.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidlabpokedex2.R
import com.example.androidlabpokedex2.presentation.list.PokemonListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, PokemonListFragment())
            .commit()
    }
}