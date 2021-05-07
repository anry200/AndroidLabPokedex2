package com.example.androidlabpokedex2.presentation.list.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.androidlabpokedex2.R
import com.example.androidlabpokedex2.databinding.HeaderItemBinding
import com.example.androidlabpokedex2.databinding.MainItemBinding
import com.squareup.picasso.Picasso

private const val ITEM_TYPE_UNKNOWN = 0
private const val ITEM_TYPE_POKEMON = 1
private const val ITEM_TYPE_HEADER = 2

class PokemonListAdapter(
    private val onItemClicked: (id: String) -> Unit
) : ListAdapter<DisplayableItem, RecyclerView.ViewHolder>(PokemonItemDiffCallback) {

    private object PokemonItemDiffCallback : DiffUtil.ItemCallback<DisplayableItem>() {
        override fun areItemsTheSame(
            oldItem: DisplayableItem,
            newItem: DisplayableItem
        ): Boolean = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: DisplayableItem,
            newItem: DisplayableItem
        ): Boolean = false
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_TYPE_POKEMON -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.main_item, parent, false)
                PokemonViewHolder(view, onItemClicked)
            }

            ITEM_TYPE_HEADER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.header_item, parent, false)
                HeaderViewHolder(view)
            }
            else -> {
                throw IllegalStateException()
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is PokemonItem -> {
                (holder as PokemonViewHolder).bind(item)
            }
            is HeaderItem -> {
                (holder as HeaderViewHolder).bind(item)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is PokemonItem -> ITEM_TYPE_POKEMON
            is HeaderItem -> ITEM_TYPE_HEADER
            else -> ITEM_TYPE_UNKNOWN
        }
    }

    class PokemonViewHolder(view: View, val onItemClicked: (id: String) -> Unit) :
        RecyclerView.ViewHolder(view) {
        private val binding = MainItemBinding.bind(itemView)

        fun bind(item: PokemonItem) = with(binding) {
            name.text = item.name

            if (item.useRedColor) {
                name.setTextColor(Color.RED)
            } else {
                name.setTextColor(Color.BLACK)
            }

            Picasso.get().load(item.image).into(imagePreview)

            itemView.setOnClickListener {
                onItemClicked(item.id)
            }
        }
    }

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = HeaderItemBinding.bind(itemView)

        fun bind(item: HeaderItem) = with(binding) {
            headerText.text = item.text
        }
    }
}