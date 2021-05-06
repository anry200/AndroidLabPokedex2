package com.example.androidlabpokedex2.presentation.details

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.androidlabpokedex2.R
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokemonDetailsFragment: Fragment(R.layout.fragment_pokemon_details) {
    private val viewModel: PokemonDetailsViewModel by viewModel()

    companion object {
        private const val PARAM_POKEMON_ID = "Pockemon_Id"

        fun newInstance(id: String): Fragment = PokemonDetailsFragment().apply {
            arguments = bundleOf(
                PARAM_POKEMON_ID to id
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getString(PARAM_POKEMON_ID)

        if (id != null) {
            loadPokemonData(view, id)
        } else {
            Log.d("TAG, ","Error, pokemon with id=$id not found")
        }
    }

    private fun loadPokemonData(view: View, id: String) {
        viewModel.loadPokemonById(id)

        val progressView = view.findViewById<ProgressBar>(R.id.progress)
        val contentView = view.findViewById<View>(R.id.content_group)
        val errorView = view.findViewById<TextView>(R.id.error_message_text)

        viewModel.viewState().observe(viewLifecycleOwner) { viewState ->
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

                    showDataState(view = view, state = viewState)
                }
                is PokemonDetailsViewState.Error -> {
                    progressView.isVisible = false
                    contentView.isVisible = false
                    errorView.isVisible = true
                }
            }
        }
    }

    private fun showDataState(view: View, state: PokemonDetailsViewState.Data) {
        val nameTextView = view.findViewById<TextView>(R.id.name)
        val imagePreview = view.findViewById<ImageView>(R.id.image)
        val abilitiesTextView = view.findViewById<TextView>(R.id.abilities)

        nameTextView.text = state.name

        Picasso.get().load(state.imageUrl).into(imagePreview)

        abilitiesTextView.text = state.abilities.joinToString(separator = ",") { it }
    }
}