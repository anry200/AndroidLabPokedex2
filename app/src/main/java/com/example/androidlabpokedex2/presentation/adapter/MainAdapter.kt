package com.example.androidlabpokedex2.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidlabpokedex2.R
import com.example.androidlabpokedex2.domain.PokemonEntity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class MainAdapter: RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    private var items: MutableList<PokemonEntity> = emptyList<PokemonEntity>().toMutableList()

    fun setPokemonList(pokemons: List<PokemonEntity>) {
        items.clear()
        items.addAll(pokemons)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.main_item, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val itemToShow = items[position]
       holder.bind(itemToShow)
    }

    override fun getItemCount(): Int = items.size

    class MainViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val textView = itemView.findViewById<TextView>(R.id.name)
        private val imagePreview = itemView.findViewById<ImageView>(R.id.imagePreview)

        fun bind(item: PokemonEntity) {
            textView.text = item.name

            Picasso.get().load(item.previewUrl).into(imagePreview, object: Callback {
                override fun onSuccess() {
                    Log.d("","Loaded image")
                }

                override fun onError(e: Exception?) {
                    Log.d("", "Loaded image", e)
                }
            })
        }
    }
}