package com.example.androidlabpokedex2.presentation.list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidlabpokedex2.R
import com.example.androidlabpokedex2.presentation.details.PARAM_POKEMON_ID
import com.example.androidlabpokedex2.presentation.list.adapter.DisplayableItem
import com.example.androidlabpokedex2.presentation.list.adapter.PokemonListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokemonListFragment: Fragment(R.layout.fragment_pokemon_list) {
    private val viewModel: PokemonListViewModel by viewModel()
    private var adapter: PokemonListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initViewModel()
        viewModel.loadData()
    }

    private fun initViewModel() {
        viewModel.viewState().observe(viewLifecycleOwner) { state ->
            when(state) {
                is PokemonListViewState.Loading -> {
                    showProgress()
                }
                is PokemonListViewState.Error -> {
                    showError(state.message)
                }
                is PokemonListViewState.Data -> {
                    showData(state.items)
                }
            }
        }
    }

    private fun initRecyclerView() {
        adapter = PokemonListAdapter(
            onItemClicked = { id ->
                val bundle = bundleOf(
                    PARAM_POKEMON_ID to id
                )
                findNavController().navigate(R.id.action_pokemonList_to_pokemonDetails, bundle)
            }
        )

        val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = adapter
    }

    private fun showProgress() {
        Toast.makeText(context, "Loading", Toast.LENGTH_LONG).show()
    }

    private fun showData(items: List<DisplayableItem>) {
        adapter?.setPokemonList(items)
    }

    private fun showError(errorMessage: String) {

    }
}