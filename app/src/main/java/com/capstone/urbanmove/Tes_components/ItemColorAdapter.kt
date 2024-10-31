package com.capstone.urbanmove.Tes_components

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.urbanmove.R
import android.graphics.Color
import android.graphics.drawable.GradientDrawable

class ItemColorAdapter(private val items: List<VehicleColor>, private val onItemClick: (VehicleColor) -> Unit)
    : RecyclerView.Adapter<ItemColorAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val colorName: TextView = itemView.findViewById(R.id.colorName)
        private val colorCircle: View = itemView.findViewById(R.id.colorCircle)

        fun bind(item: VehicleColor) {
            colorName.text = item.color
            try {
                (colorCircle.background as GradientDrawable).setColor(Color.parseColor("#${item.code}"))
            } catch (e: IllegalArgumentException) {
                colorCircle.setBackgroundColor(Color.GRAY) // Color por defecto si hay un error
            }

            itemView.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_color, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
