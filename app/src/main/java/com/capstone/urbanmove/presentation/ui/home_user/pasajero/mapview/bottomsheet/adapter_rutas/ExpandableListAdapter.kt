package com.capstone.urbanmove.presentation.ui.home_user.pasajero.mapview.bottomsheet.adapter_rutas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.capstone.urbanmove.R
import com.capstone.urbanmove.databinding.ChildItemsBinding
import com.capstone.urbanmove.databinding.GroupItemBinding
import com.capstone.urbanmove.presentation.ui.home_user.pasajero.PassengerViewModel
import com.capstone.urbanmove.presentation.ui.home_user.pasajero.mapview.bottomsheet.models.Ruta

class ExpandableListAdapter(
    private val context: Context,
    private val titles: List<String>,
    private val icons: Map<String, Int>,
    private val data: Map<String, List<Ruta>>,
    private val viewModelPassenger: PassengerViewModel
) : BaseExpandableListAdapter() {

    override fun getGroupCount(): Int = titles.size
    override fun getChildrenCount(groupPosition: Int): Int {
        val groupTitle = titles[groupPosition]
        val total = data[groupTitle]?.size ?: 0
        if (total == 0)
            return 0
        return 1
    }

    override fun getGroup(groupPosition: Int): Any = titles[groupPosition]
    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        val groupTitle = titles[groupPosition]
        return data[groupTitle]?.get(childPosition) ?: Ruta(0, "", "", 0,"") // Retornar un valor por defecto si no se encuentra
    }

    override fun getGroupId(groupPosition: Int): Long = groupPosition.toLong()
    override fun getChildId(groupPosition: Int, childPosition: Int): Long =
        (groupPosition * 100 + childPosition).toLong()

    override fun hasStableIds(): Boolean = true

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        val binding = GroupItemBinding.inflate(LayoutInflater.from(context), parent, false)
        val groupTitle = getGroup(groupPosition) as String

        binding.textviewTransporte.text = groupTitle
        binding.iconTransporte.setImageResource(icons[groupTitle] ?: R.drawable.icon_microbus)

        return binding.root
    }
    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        val binding = ChildItemsBinding.inflate(LayoutInflater.from(context), parent, false)
        val groupTitle = titles[groupPosition]
        val childItems = data[groupTitle] ?: emptyList()

        val recyclerView = binding.recyclerChildItems
        recyclerView.layoutManager = GridLayoutManager(context, 3)
        recyclerView.adapter = ChildGridAdapter(childItems) { selectedItem ->
            viewModelPassenger.add_ruta_list(selectedItem)
        }

        return binding.root
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean = true

}