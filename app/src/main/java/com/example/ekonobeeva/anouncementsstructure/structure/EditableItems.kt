package com.example.ekonobeeva.anouncementsstructure.structure

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.Gravity
import android.widget.ImageView
import com.example.ekonobeeva.anouncementsstructure.R
import com.squareup.picasso.Picasso
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_image.view.*
import kotlinx.android.synthetic.main.item_list_text.view.*
import kotlinx.android.synthetic.main.item_plain_text.view.*
import kotlinx.android.synthetic.main.item_text_header.view.*
import kotlinx.android.synthetic.main.item_text_sub_header.view.*
import java.security.AccessController.getContext

val TAG = "ITEMS"

private fun changeBias(cloneLayoutId: Int, receiverConstraintLayout: ConstraintLayout, biasItemId: Int, bias: Float, context: Context){

    ConstraintSet().apply { clone(context, cloneLayoutId)
        setHorizontalBias(biasItemId, bias)
        applyTo(receiverConstraintLayout)
    }

}

private fun countDpInPixels(context: Context, int: Int): Int{
    val scale = context.resources.displayMetrics.density
    return (int * scale + 0.5f).toInt()
}

abstract class EditableItem: Item(){
    override fun getSwipeDirs(): Int {
        return ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    }

    override fun getDragDirs(): Int {
        return ItemTouchHelper.UP or ItemTouchHelper.DOWN
    }
}

open class EditableImageItem (val type: ItemType,
                      val imageSize: ImageSize,
                      val imageUrl: String): EditableItem(){

    override fun bind(viewHolder: ViewHolder, position: Int) {

        if(imageSize == ImageSize.FULL_SCREEN){
            viewHolder.itemView.image_item_image.layoutParams.height = ConstraintLayout.LayoutParams.MATCH_PARENT
            viewHolder.itemView.image_item_image.layoutParams.width = ConstraintLayout.LayoutParams.MATCH_PARENT
            viewHolder.itemView.layoutParams.height = ConstraintLayout.LayoutParams.MATCH_PARENT
            viewHolder.itemView.layoutParams.width = ConstraintLayout.LayoutParams.MATCH_PARENT
        }else {
            viewHolder.itemView.layoutParams.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
            viewHolder.itemView.layoutParams.width = ConstraintLayout.LayoutParams.MATCH_PARENT
            viewHolder.itemView.image_item_image.layoutParams.height = countDpInPixels(viewHolder.itemView.image_item_image.context, imageSize.heigth)
            viewHolder.itemView.image_item_image.layoutParams.width = countDpInPixels(viewHolder.itemView.image_item_image.context, imageSize.width)
        }

        Picasso.with(viewHolder.itemView.image_item_image.context)
                .load(imageUrl)
                .into(viewHolder.itemView.image_item_image)

    }

    override fun getLayout(): Int = R.layout.item_image

}

open class EditableItemTextHeader (val text: String, val bias: Float): EditableItem(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.text_header_item_text_view.text = text

        changeBias(R.layout.item_text_header,
                viewHolder.itemView.item_text_header_container,
                R.id.text_header_item_text_view,
                bias,
                viewHolder.itemView.text_header_item_text_view.context)

        if(bias > 0 && bias < 1){
            viewHolder.itemView.text_header_item_text_view.gravity = Gravity.CENTER
        }else{
            viewHolder.itemView.text_header_item_text_view.gravity = Gravity.LEFT
        }
    }

    override fun getLayout(): Int = R.layout.item_text_header
}

open class EditableItemTextSubHeader (
        val text: String,
        val bias: Float): EditableItem(){

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.text_sub_header_item_text_view.text = text

        changeBias(R.layout.item_text_sub_header,
                viewHolder.itemView.item_text_sub_header_container,
                R.id.text_sub_header_item_text_view,
                bias,
                viewHolder.itemView.text_sub_header_item_text_view.context)

        if(bias > 0 && bias < 1){
            viewHolder.itemView.text_sub_header_item_text_view.gravity = Gravity.CENTER
        }else{
            viewHolder.itemView.text_sub_header_item_text_view.gravity = Gravity.LEFT
        }

    }

    override fun getLayout(): Int = R.layout.item_text_sub_header

}


open class EditableItemTextList (val text: String, val bias: Float): EditableItem(){

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.item_list_text_text_view.text = "• " + text

        changeBias(R.layout.item_list_text,
                viewHolder.itemView.item_list_text_container,
                R.id.item_list_text_text_view,
                bias, viewHolder.itemView.item_list_text_text_view.context)
    }

    override fun getLayout(): Int = R.layout.item_list_text

}

open class EditableItemTextPlain (val text: String, val bias: Float ): EditableItem(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.item_plain_text_text_view.text = text

        changeBias(R.layout.item_plain_text,
                viewHolder.itemView.item_plain_text_container,
                R.id.item_plain_text_text_view,
                bias,
                viewHolder.itemView.item_plain_text_text_view.context)

        if(bias > 0 && bias < 1){
            viewHolder.itemView.item_plain_text_text_view.gravity = Gravity.CENTER
        }else{
            viewHolder.itemView.item_plain_text_text_view.gravity = Gravity.LEFT
        }
    }

    override fun getLayout(): Int = R.layout.item_plain_text

}


open class ListImageItem (var string: String): EditableItem(){
    override fun bind(viewHolder: ViewHolder, position: Int) {

        Picasso.with(viewHolder.itemView.image_item_image.context)
                .load(string)
                .into(viewHolder.itemView.image_item_image)
    }

    override fun getLayout(): Int = R.layout.item_image_for_select

}