package com.example.searchenginemercadolibre.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.example.searchenginemercadolibre.R
import java.util.*

class ViewPagerAdapter(
    private val context: Context,
    private val listPhotos: List<String>
) : PagerAdapter() {

    override fun getCount(): Int {
        return listPhotos.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as RelativeLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val mLayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView: View = mLayoutInflater.inflate(R.layout.item_photos, container, false)
        val imageView: AppCompatImageView = itemView.findViewById(R.id.ivPhoto)

        Glide.with(context).load(listPhotos[position]).placeholder(R.drawable.load).into(imageView)

        Objects.requireNonNull(container).addView(itemView)
        return itemView
    }
}