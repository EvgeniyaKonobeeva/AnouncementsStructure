package com.example.ekonobeeva.anouncementsstructure.activity.annoncenments_view

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ekonobeeva.anouncementsstructure.R
import com.example.ekonobeeva.anouncementsstructure.activity.AnnounceBuffer
import com.example.ekonobeeva.anouncementsstructure.structure.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.fragment_page.*

class PageFragment: Fragment() {

    /*
    всегда брать из базы
     */
    private lateinit var announcePage: AnnouncePage
    private var pageIndex: Int = -1



    companion object {
        val PAGE_INDEX_KEY = "PAGE_INDEX_KEY"

        fun newInstance(pageIndex: Int): PageFragment{
            return PageFragment().apply { arguments = Bundle().apply { putInt(PAGE_INDEX_KEY, pageIndex) } }
        }
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_page, container, false)
        getParamsFromArguments()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ConstraintSet().apply {
            clone(context, R.layout.fragment_page)
            setVerticalBias(R.id.page_fragment_recycler, announcePage.alignType.getAssociatedBias())
            applyTo(page_fragment_id as ConstraintLayout)
        }

        val adapter = GroupAdapter<ViewHolder>()
        page_fragment_recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        page_fragment_recycler.adapter = adapter


        adapter.addAll(mapPageItemToRecyclerViewItems())
    }

    private fun getParamsFromArguments(){
        arguments?.getInt(PAGE_INDEX_KEY)?.let {
            pageIndex = it
            announcePage = AnnounceBuffer.getModel().announcePages[pageIndex]
        }
    }


    private fun mapPageItemToRecyclerViewItems(): List<Item>{
        val resultList = mutableListOf<Item>()

        announcePage.items?.forEach { element ->
           resultList.add(
                   when(element){
                        is Image -> {ImageItem(element.type, element.imageSize, element.imageUrl)}

                        is Text -> {textItemFactory(element)}

                        else -> {ItemTextHeader("", 0.5f)}
                   }
           )
        }

        return resultList
    }

    fun textItemFactory(textClass: Text): Item{
        return when(textClass.textType){
            TextType.HEADER -> {ItemTextHeader(textClass.text, announcePage.alignType.getAssociatedBias())}

            TextType.SUB_HEADER -> {ItemTextSubHeader(textClass.text, announcePage.alignType.getAssociatedBias())}

            TextType.LIST -> {ItemTextList(textClass.text, announcePage.alignType.getAssociatedBias())}

            TextType.PLAIN -> {ItemTextPlain(textClass.text, announcePage.alignType.getAssociatedBias())}

        }
    }




}