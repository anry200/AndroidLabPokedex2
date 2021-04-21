package com.example.androidlabpokedex2.presentation.details

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.androidlabpokedex2.R

class PokemonDetailsActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_details)

        val id = intent.extras?.getString("Pockemon_Id")

        val nameTextView = findViewById<TextView>(R.id.name)
        nameTextView.text = id
    }
}