package com.example.androidlabpokedex2.presentation.details

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.androidlabpokedex2.R
import com.example.androidlabpokedex2.databinding.FragmentPokemonDetailsBinding
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PokemonDetailsFragment: Fragment(R.layout.fragment_pokemon_details) {
    private val args by navArgs<PokemonDetailsFragmentArgs>()

    private val viewModel: PokemonDetailsViewModel by viewModel { parametersOf(args.pokemonId) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView(view)
        viewModel.loadPokemon()
    }

    private fun initView(view: View) {
        val binding = FragmentPokemonDetailsBinding.bind(view)

        val progressView = binding.progress
        val contentView = binding.contentGroup
        val errorView = binding.errorMessageText

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

                    showDataState(binding, viewState)
                }
                is PokemonDetailsViewState.Error -> {
                    progressView.isVisible = false
                    contentView.isVisible = false
                    errorView.isVisible = true
                }
            }
        }
    }

    private fun showDataState(
        binding: FragmentPokemonDetailsBinding,
        state: PokemonDetailsViewState.Data
    ) {
        val nameTextView = binding.name //view.findViewById<TextView>(R.id.name)
        val imagePreview = binding.image //view.findViewById<ImageView>(R.id.image)
        val abilitiesTextView = binding.abilities //view.findViewById<TextView>(R.id.abilities)

        nameTextView.text = state.name

        Picasso.get().load(state.imageUrl).into(imagePreview)

        abilitiesTextView.text = state.abilities.joinToString(separator = ",") { it }
    }
}