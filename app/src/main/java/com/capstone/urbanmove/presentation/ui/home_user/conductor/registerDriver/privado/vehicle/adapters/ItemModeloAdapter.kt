package com.capstone.urbanmove.presentation.ui.home_user.conductor.registerDriver.privado.vehicle.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.urbanmove.R
import com.capstone.urbanmove.presentation.ui.home_user.conductor.registerDriver.privado.vehicle.objects.VehicleModelo

class ItemModeloAdapter(
    private val items: List<VehicleModelo>,
    private val onItemClick: (VehicleModelo) -> Unit
) : RecyclerView.Adapter<ItemModeloAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewItemName: TextView = itemView.findViewById(R.id.textViewItemName)

        fun bind(item: VehicleModelo) {
            textViewItemName.text = item.modelo
            itemView.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_element, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}