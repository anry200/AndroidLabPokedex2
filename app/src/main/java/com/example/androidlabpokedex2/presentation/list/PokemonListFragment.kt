package com.example.androidlabpokedex2.presentation.list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidlabpokedex2.R
import com.example.androidlabpokedex2.databinding.FragmentPokemonListBinding
import com.example.androidlabpokedex2.presentation.list.adapter.DisplayableItem
import com.example.androidlabpokedex2.presentation.list.adapter.PokemonListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokemonListFragment: Fragment(R.layout.fragment_pokemon_list) {
    private val viewModel: PokemonListViewModel by viewModel()
    private var adapter: PokemonListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentPokemonListBinding.bind(view)
        initRecyclerView(binding)
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

    private fun initRecyclerView(binding: FragmentPokemonListBinding) {
        adapter = PokemonListAdapter(
            onItemClicked = { id ->
                val action = PokemonListFragmentDirections.actionPokemonListToPokemonDetails(id)
                findNavController().navigate(action)
            }
        )

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@PokemonListFragment.adapter
        }
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