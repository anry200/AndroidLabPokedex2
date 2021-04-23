package com.example.androidlabpokedex2.presentation.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.androidlabpokedex2.R
import com.squareup.picasso.Picasso

class PokemonDetailsActivity: AppCompatActivity() {
    private val viewModel = PokemonDetailsViewModel()

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

        if (id != null) {
            loadPokemonData(id)
        } else {
            Log.d("TAG, ","Error, pokemon with id=$id not found")
            finish()
        }
    }

    private fun loadPokemonData(id: String) {
        viewModel.loadPokemonById(id)

        val progressView = findViewById<ProgressBar>(R.id.progress)
        val contentView = findViewById<View>(R.id.content_group)
        val errorView = findViewById<TextView>(R.id.error_message_text)

        viewModel.viewState().observe(this) { viewState ->
            when(viewState) {
                PokemonDetailsViewState.Loading -> {
                    progressView.isVisible = true
                    contentView.isVisible = false
                    errorView.isVisible = false
                }
                is PokemonDetailsViewState.Data -> {
                    progressView.isVisible = false
                    contentView.isVisible = true
                    errorView.isVisible = false

                    showDataState(state = viewState)
                }
                is PokemonDetailsViewState.Error -> {
                    progressView.isVisible = false
                    contentView.isVisible = false
                    errorView.isVisible = true
                }
            }
        }
    }

    private fun showDataState(state: PokemonDetailsViewState.Data) {
        val nameTextView = findViewById<TextView>(R.id.name)
        val imagePreview = findViewById<ImageView>(R.id.image)
        val abilitiesTextView = findViewById<TextView>(R.id.abilities)

        nameTextView.text = state.name

        Picasso.get().load(state.imageUrl).into(imagePreview)

        abilitiesTextView.text = state.abilities.joinToString(separator = ",") { it }
    }
}