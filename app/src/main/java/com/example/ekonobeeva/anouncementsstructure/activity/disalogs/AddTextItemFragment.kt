package com.example.ekonobeeva.anouncementsstructure.activity.disalogs

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.ekonobeeva.anouncementsstructure.R
import com.example.ekonobeeva.anouncementsstructure.activity.AnnounceBuffer
import com.example.ekonobeeva.anouncementsstructure.structure.ItemType
import com.example.ekonobeeva.anouncementsstructure.structure.Text
import com.example.ekonobeeva.anouncementsstructure.structure.TextType
import kotlinx.android.synthetic.main.fragment_add_text_item.*
import kotlinx.android.synthetic.main.header_save_back_press.*

class AddTextItemFragment: Fragment() {
    val TAG = "AddTextItemFragment"

    private var selectedIndex = 0 // куда вставить
    private var selectedType: TextType = TextType.PLAIN

    companion object {
        const val COUNT_ITEMS_KEY = "COUNT_ITEMS_KEY"
        const val ANNOUNCE_PAGE_INDEX = "ANNOUNCE_PAGE_INDEX"

        fun newInstance(countItems: Int, announcePageIndex: Int): AddTextItemFragment {
            val TAG = "AddTextItemFragment"
            Log.d(TAG, "newInstance: " + countItems);
            return AddTextItemFragment()
                    .apply { arguments = Bundle().apply {
                                                    this.putInt(COUNT_ITEMS_KEY, countItems)
                                                    this.putInt(ANNOUNCE_PAGE_INDEX, announcePageIndex)} }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView: ");
        return inflater.inflate(R.layout.fragment_add_text_item, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initIndexSpinner()
        initTextTypeSpinner()
        initHeaderLayout()
    }


    private fun initHeaderLayout(){
        header.text = "Добавить текстовое поле"

        back_press.setOnClickListener {
            activity?.onBackPressed()
        }

        save.setOnClickListener {
            save(selectedIndex, selectedType, dialog_edit_button_edit_text_title.text.toString())
            activity?.onBackPressed()
        }
    }


    private fun initIndexSpinner(){

        val indexList = mutableListOf<Int>()
        for(i: Int in 0 .. getCountItems()){
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

    private fun getCountItems(): Int{
        Log.d(TAG, "getCountItems: " + arguments?.getInt(COUNT_ITEMS_KEY, 0));
        return arguments?.getInt(COUNT_ITEMS_KEY, 0) ?: 0
    }

    private fun getAnnounceIndex(): Int{
        Log.d(TAG, "getAnnounceIndex: " + arguments?.getInt(ANNOUNCE_PAGE_INDEX, 0));
        return arguments?.getInt(ANNOUNCE_PAGE_INDEX, 0) ?: 0
    }

    private fun initTextTypeSpinner(){
        val spinnerAdapter = ArrayAdapter(activity, android.R.layout.simple_spinner_item, TextType.values())
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        action_type_list.adapter = spinnerAdapter
        action_type_list.setSelection(0)

        action_type_list.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                // empty
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedType = TextType.values()[p2]
            }
        }
    }

    private fun save(selectedIndex: Int, selectedTextType: TextType, text: String){
        if(selectedIndex < getCountItems()) {
            AnnounceBuffer.content.announcePages[getAnnounceIndex()].items?.add(selectedIndex, Text(ItemType.TEXT, text, selectedTextType))
        }else{
            AnnounceBuffer.content.announcePages[getAnnounceIndex()].items?.add(Text(ItemType.TEXT, text, selectedTextType))
        }
    }
}