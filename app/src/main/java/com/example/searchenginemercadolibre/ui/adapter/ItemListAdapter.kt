package com.example.searchenginemercadolibre.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.searchenginemercadolibre.R
import com.example.searchenginemercadolibre.core.BaseViewHolder
import com.example.searchenginemercadolibre.databinding.ItemSearchBinding
import com.example.searchenginemercadolibre.domain.models.Item
import java.text.DecimalFormat

class ItemListAdapter(
    private val context: Context,
    private val listItems: List<Item>,
    private val itemCLickListener: OnItemClickListener
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnItemClickListener {
        fun onItemClick(item: Item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = ItemSearchBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )
        return ItemViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is ItemViewHolder -> holder.bind(listItems[position])
        }
    }

    override fun getItemCount(): Int = listItems.size

    inner class ItemViewHolder(private val binding: ItemSearchBinding) :
        BaseViewHolder<Item>(binding.root) {
        override fun bind(item: Item): Unit = with(binding) {
            Glide.with(context).load(item.thumbnail).placeholder(R.drawable.load).into(thumbnail)
            tvTitle.text = item.title
            val doublePrice = item.price.toDouble()
            val formatter = DecimalFormat("#,###")
            val price = "$ ${formatter.format(doublePrice)}"
            tvPrice.text = price
            tvCondition.text = item.condition
            if (item.freeShipping) {
                tvShipping.visibility = View.VISIBLE
            } else {
                tvShipping.visibility = View.GONE
            }
            cardView.setOnClickListener {
                itemCLickListener.onItemClick(item)
            }
        }

    }
}