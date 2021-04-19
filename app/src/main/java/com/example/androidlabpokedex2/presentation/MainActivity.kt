package com.example.androidlabpokedex2.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidlabpokedex2.R
import com.example.androidlabpokedex2.presentation.adapter.DisplayableItem
import com.example.androidlabpokedex2.presentation.adapter.MainAdapter

class MainActivity : AppCompatActivity() {
    private val viewModel = MainViewModel()
    private val adapter = MainAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()

        viewModel.viewState().observe(this) { state ->
            when(state) {
                is MainViewState.LoadingState -> {
                    showProgress()
                }
                is MainViewState.ErrorState -> {
                    showError(state.errorMessage)
                }
                is MainViewState.ContentState -> {
                    showData(state.items)
                }
            }
        }

        viewModel.loadData()
    }

    private fun initRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun showProgress() {
        Toast.makeText(this, "Loading", Toast.LENGTH_LONG).show()
    }

    private fun showData(items: List<DisplayableItem>) {
        adapter.setPokemonList(items)
    }

    private fun showError(errorMessage: String) {

    }
}