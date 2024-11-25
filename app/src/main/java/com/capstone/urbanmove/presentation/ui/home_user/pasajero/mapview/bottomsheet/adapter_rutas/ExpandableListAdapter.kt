package com.capstone.urbanmove.presentation.ui.home_user.pasajero.mapview.bottomsheet.adapter_rutas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.capstone.urbanmove.R
import com.capstone.urbanmove.databinding.ChildItemsBinding
import com.capstone.urbanmove.databinding.GroupItemBinding

class ExpandableListAdapter(
    private val context: Context,
    private val titles: List<String>,
    private val icons: Map<String, Int>,
    private val data: Map<String, List<String>>
) : BaseExpandableListAdapter() {

    override fun getGroupCount(): Int = titles.size
    override fun getChildrenCount(groupPosition: Int): Int = data[titles[groupPosition]]?.size ?: 0
    override fun getGroup(groupPosition: Int): Any = titles[groupPosition]
    override fun getChild(groupPosition: Int, childPosition: Int): Any =
        data[titles[groupPosition]]?.get(childPosition) ?: ""

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
        val childItems = data[titles[groupPosition]] ?: emptyList()

        // Configurar el RecyclerView
        val recyclerView = binding.recyclerChildItems
        recyclerView.layoutManager = GridLayoutManager(context, 3) // 3 columnas por fila
        recyclerView.adapter = ChildGridAdapter(childItems)

        return binding.root
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean = true

}