package com.example.searchenginemercadolibre.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.searchenginemercadolibre.databinding.ItemSearchBinding
import com.example.searchenginemercadolibre.domain.models.Item

class ListProductAdapter(
    private val context: Context,
    private val itemCLickListener: OnItemClickListener
) : RecyclerView.Adapter<ViewHolder>(){

    private val listItems: List<Item> = listOf()

    interface OnItemClickListener{
        fun onItemClick(item: Item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemSearchBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}