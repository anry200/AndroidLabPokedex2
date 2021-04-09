package com.example.androidlabpokedex2

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MainAdapter(
    private val items: List<String>
): RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

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

        fun bind(item: String) {
            textView.text = item

            val colors = listOf(
                Color.RED,
                Color.BLUE,
                Color.GREEN
            )

            val color = colors.random()
            textView.setBackgroundColor(color)
        }
    }
}