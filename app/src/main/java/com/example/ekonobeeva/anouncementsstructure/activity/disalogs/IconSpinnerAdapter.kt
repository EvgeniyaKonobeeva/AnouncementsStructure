package com.example.ekonobeeva.anouncementsstructure.activity.disalogs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import com.example.ekonobeeva.anouncementsstructure.R
import com.example.ekonobeeva.anouncementsstructure.R.id.image_item_image

import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_image_for_select.view.*


class IconSpinnerAdapter(context: Context?, val int: Int, array: ArrayList<String>, private val layoutInflater: LayoutInflater):ArrayAdapter<String>(context, int, array) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return createCustomIconLayout(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return createCustomIconLayout(position, convertView, parent)
    }

    fun createCustomIconLayout(position: Int, convertView: View?, parent: ViewGroup?): View{
        val view = layoutInflater.inflate(R.layout.item_image_for_select, parent, false)
        val imageView = view.image_item_image
        Picasso.with(context)
                .load(getItem(position))
                .into(imageView)
        return view
    }


}