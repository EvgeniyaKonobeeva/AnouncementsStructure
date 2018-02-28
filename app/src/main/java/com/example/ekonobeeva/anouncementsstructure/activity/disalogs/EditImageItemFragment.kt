package com.example.ekonobeeva.anouncementsstructure.activity.disalogs

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.ekonobeeva.anouncementsstructure.R
import com.example.ekonobeeva.anouncementsstructure.activity.AnnounceBuffer
import com.example.ekonobeeva.anouncementsstructure.structure.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.OnItemClickListener
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.fragment_edit_image_item.*
import kotlinx.android.synthetic.main.header_save_back_press.*

class EditImageItemFragment: Fragment() {
    val TAG = "EditImageItemFragment"

    companion object {
        const val PAGE_INDEX_KEY = "PAGE_INDEX_KEY"

        fun newInstance(pageIndex: Int): EditImageItemFragment {
            return EditImageItemFragment().apply { arguments = Bundle().apply { putInt(PAGE_INDEX_KEY, pageIndex) } }
        }
    }

    private lateinit var adapter: GroupAdapter<ViewHolder>
    private var pageIndex: Int = 0
    private var imageSize: ImageSize = ImageSize.SIZE_2
    private var selectedIndex = 0
    private var selectedUrl = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        pageIndex = arguments?.getInt(PAGE_INDEX_KEY) ?: 0
        return inflater.inflate(R.layout.fragment_edit_image_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initHeaderLayout()
        initImageSizeSpinner()
        initSpinnerPicture()
        initIndexSpinner()


    }

    private fun initHeaderLayout(){
        header.text = "Добавить картинку"

        back_press.setOnClickListener {
            activity?.onBackPressed()
        }

        save.setOnClickListener {
            saveResult()
            activity?.onBackPressed()
        }
    }

    private fun initImageSizeSpinner(){
        val spinnerAdapter = ArrayAdapter(activity, android.R.layout.simple_spinner_item, ImageSize.values())
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        action_type_list.adapter = spinnerAdapter
        action_type_list.setSelection(0)

        action_type_list.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                // empty
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                imageSize = ImageSize.values()[p2]
            }
        }
    }

    private fun initIndexSpinner(){

        val indexList = mutableListOf<Int>()
        for(i: Int in 0 .. getItemsCount()){
            indexList.add(i)
        }
        val spinnerAdapter = ArrayAdapter(activity, android.R.layout.simple_spinner_item, indexList)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        index_list.adapter = spinnerAdapter
        index_list.setSelection(0)

        index_list.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                // empty
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedIndex = p2
            }
        }
    }


    private fun initSpinnerPicture(){
//        fragment_edit_image_item_recycler_image.layoutManager = GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false)
//
//
//        adapter = GroupAdapter<ViewHolder>()
//        adapter.setOnItemClickListener(onItemClickListener)
//
//        fragment_edit_image_item_recycler_image.adapter = adapter
//
//        adapter.clear()
//        adapter.addAll(getUrls())


        val spinnerAdapter = IconSpinnerAdapter(activity, R.layout.item_image_for_select, getUrls(), layoutInflater)
        spinnerAdapter.setDropDownViewResource(R.layout.item_image_for_select);
        spinner_picture.adapter = spinnerAdapter
        spinner_picture.setSelection(0)

        spinner_picture.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                // empty
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedUrl = getUrls()[p2]
            }
        }


    }
    private fun getUrls():ArrayList<String>{
        return arrayListOf(
                "https://million-wallpapers.com/wallpapers/6/20/small/509300661331552.jpg",
                "https://f1.ds-russia.ru/u_dirs/144/144037/p/6f71650b7c713843d80d5b6c125a63bb.jpg",
                "https://b-track.com/uploads/user/photo/1/4/2/6/3/_/avatar_966bf4.jpg",
                "http://mw2.google.com/mw-panoramio/photos/medium/75185375.jpg",
                "http://www.iphonewallpaperss.com/images/iphone-6-best-view-forest-sun-nature-new-hd-wallpapers.jpg"
        )
    }

    private fun getItemsCount(): Int{

        return AnnounceBuffer.content.announcePages[pageIndex].items?.size ?: 0
    }

    private fun saveResult(){
        if(selectedIndex < getItemsCount()) {
            AnnounceBuffer.content.announcePages[pageIndex].items?.add(selectedIndex, Image(ItemType.IMAGE, imageSize, selectedUrl))
        }else{
            AnnounceBuffer.content.announcePages[pageIndex].items?.add(Image(ItemType.IMAGE, imageSize, selectedUrl))
        }
    }


}