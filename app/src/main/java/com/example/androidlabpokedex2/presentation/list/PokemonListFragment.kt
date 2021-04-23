package com.example.androidlabpokedex2.presentation.list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidlabpokedex2.R
import com.example.androidlabpokedex2.presentation.details.PokemonDetailsActivity
import com.example.androidlabpokedex2.presentation.list.adapter.DisplayableItem
import com.example.androidlabpokedex2.presentation.list.adapter.PokemonListAdapter

class PokemonListFragment: Fragment(R.layout.fragment_pokemon_list) {
    private val viewModel = PokemonListViewModel()
    private var adapter: PokemonListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        viewModel.viewState().observe(this) { state ->
            when(state) {
                is PokemonListViewState.LoadingState -> {
                    showProgress()
                }
                is PokemonListViewState.ErrorState -> {
                    showError(state.errorMessage)
                }
                is PokemonListViewState.ContentState -> {
                    showData(state.items)
                }
            }
        }

        viewModel.loadData()
    }

    private fun initRecyclerView() {
        adapter = PokemonListAdapter(
            onItemClicked = { id ->
                val safeContext = context
                if (safeContext != null) {
                    startActivity(PokemonDetailsActivity.intent(safeContext, id))
                }
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