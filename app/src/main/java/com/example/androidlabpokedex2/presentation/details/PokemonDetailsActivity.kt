package com.example.androidlabpokedex2.presentation.details

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.androidlabpokedex2.R

public const val PARAM_POKEMON_ID = "Pockemon_Id"
class PokemonDetailsActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_details)

        val id = intent.extras?.getString(PARAM_POKEMON_ID)

        val nameTextView = findViewById<TextView>(R.id.name)
        nameTextView.text = id
    }
}