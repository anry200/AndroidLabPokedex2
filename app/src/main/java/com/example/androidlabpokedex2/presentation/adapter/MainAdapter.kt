package com.example.androidlabpokedex2.presentation.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidlabpokedex2.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

private const val ITEM_TYPE_UNKNOWN = 0
private const val ITEM_TYPE_POKEMON = 1
private const val ITEM_TYPE_BANNER = 2
class MainAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: MutableList<DisplayableItem> = emptyList<DisplayableItem>().toMutableList()

    fun setPokemonList(pokemons: List<DisplayableItem>) {
        items.clear()
        items.addAll(pokemons)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            ITEM_TYPE_POKEMON -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.main_item, parent, false)
                PokemonViewHolder(view)
            }

            ITEM_TYPE_BANNER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.banner_item, parent, false)
                BannerViewHolder(view)
            }
            else -> {
                throw IllegalStateException()
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemToShow = items[position]

        when (itemToShow) {
            is PokemonItem -> {
                (holder as PokemonViewHolder).bind(itemToShow)
            }
            is BannerItem -> {
                (holder as BannerViewHolder).bind(itemToShow)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(items[position]) {
            is PokemonItem -> ITEM_TYPE_POKEMON
            is BannerItem -> ITEM_TYPE_BANNER
            else -> ITEM_TYPE_UNKNOWN
        }
    }

    override fun getItemCount(): Int = items.size

    class PokemonViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val textView = itemView.findViewById<TextView>(R.id.name)
        private val imagePreview = itemView.findViewById<ImageView>(R.id.imagePreview)

        fun bind(item: PokemonItem) {
            textView.text = item.name

            if (item.useRedColor) {
                textView.setTextColor(Color.RED)
            } else {
                textView.setTextColor(Color.BLACK)
            }

            Picasso.get().load(item.image).into(imagePreview, object: Callback {
                override fun onSuccess() {
                    Log.d("","Loaded image")
                }

                override fun onError(e: Exception?) {
                    Log.d("", "Loaded image", e)
                }
            })
        }
    }

    class BannerViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val bannerName = itemView.findViewById<TextView>(R.id.bannerName)

        fun bind(item: BannerItem) {
            bannerName.text = item.bannerText
        }
    }
}