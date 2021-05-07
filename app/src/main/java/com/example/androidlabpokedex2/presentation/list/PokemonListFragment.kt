package com.example.androidlabpokedex2.presentation.list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.androidlabpokedex2.R
import com.example.androidlabpokedex2.databinding.FragmentPokemonListBinding
import com.example.androidlabpokedex2.presentation.list.adapter.DisplayableItem
import com.example.androidlabpokedex2.presentation.list.adapter.PokemonListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokemonListFragment : Fragment(R.layout.fragment_pokemon_list) {
    private val viewModel: PokemonListViewModel by viewModel()
    private val viewBinding: FragmentPokemonListBinding by viewBinding()
    private var adapter: PokemonListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initViewModel()
        viewModel.fetch()
    }

    private fun initRecyclerView() {
        adapter = PokemonListAdapter(
            onItemClicked = ::openPokemonById
        )

        viewBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@PokemonListFragment.adapter
        }
    }

    private fun initViewModel() {
        viewModel.viewState().observe(viewLifecycleOwner, ::showViewState)
    }

    private fun showViewState(state: PokemonListViewState) {
        when (state) {
            is PokemonListViewState.Loading -> {
                showProgress()
            }
            is PokemonListViewState.Error -> {
                showError(state.message)
            }
            is PokemonListViewState.Content -> {
                showContent(state.items)
            }
        }
    }

    private fun showProgress() {
        Toast.makeText(context, "Loading", Toast.LENGTH_LONG).show()
    }

    private fun showContent(items: List<DisplayableItem>) {
        adapter?.submitList(items)
    }

    private fun showError(errorMessage: String) {
        Toast.makeText(context, "Error: $errorMessage", Toast.LENGTH_LONG).show()
    }

    private fun openPokemonById(id: String) {
        val action = PokemonListFragmentDirections.actionPokemonListToPokemonDetails(id)
        findNavController().navigate(action)
    }
}