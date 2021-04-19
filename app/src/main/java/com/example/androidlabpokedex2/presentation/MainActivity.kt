package com.example.androidlabpokedex2.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidlabpokedex2.R
import com.example.androidlabpokedex2.presentation.adapter.DisplayableItem
import com.example.androidlabpokedex2.presentation.adapter.MainAdapter

class MainActivity : AppCompatActivity() {
    private val viewModel = MainViwModel()
    private val adapter = MainAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()


        viewModel.getPokemonList().observe(this){
            showData(it)
        }

        viewModel.error().observe(this) {
            showError(it)
        }

        viewModel.loading().observe(this) {
            showProgress()
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