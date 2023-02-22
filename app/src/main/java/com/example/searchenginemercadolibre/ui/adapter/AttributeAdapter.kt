package com.example.searchenginemercadolibre.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.searchenginemercadolibre.core.BaseViewHolder
import com.example.searchenginemercadolibre.databinding.ItemAttributeBinding
import com.example.searchenginemercadolibre.domain.models.AttributesModel

class AttributeAdapter(
    private val context: Context,
    private val listAttribute: List<AttributesModel>,
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = ItemAttributeBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )
        return ItemViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is ItemViewHolder -> holder.bind(listAttribute[position])
        }
    }

    override fun getItemCount(): Int = listAttribute.size

    inner class ItemViewHolder(private val binding: ItemAttributeBinding) :
        BaseViewHolder<AttributesModel>(binding.root) {
        override fun bind(item: AttributesModel): Unit = with(binding) {
            name.text = item.name
            valueName.text = item.valueName
        }

    }
}