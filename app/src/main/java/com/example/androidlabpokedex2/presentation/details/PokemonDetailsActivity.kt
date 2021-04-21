package com.example.androidlabpokedex2.presentation.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.androidlabpokedex2.R

class PokemonDetailsActivity: AppCompatActivity() {

    companion object {
        private const val PARAM_POKEMON_ID = "Pockemon_Id"

        fun intent(context: Context, id: String): Intent {
            val intent = Intent(context, PokemonDetailsActivity::class.java)
            return intent.putExtra(PARAM_POKEMON_ID, id)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_details)

        val id = intent.extras?.getString(PARAM_POKEMON_ID)

        val nameTextView = findViewById<TextView>(R.id.name)
        nameTextView.text = id
    }
}