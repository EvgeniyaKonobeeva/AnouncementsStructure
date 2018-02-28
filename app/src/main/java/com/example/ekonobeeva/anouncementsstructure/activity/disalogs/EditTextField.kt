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
import kotlinx.android.synthetic.main.fragment_edit_text_item.*
import kotlinx.android.synthetic.main.header_save_back_press.*


class EditTextField : Fragment() {

    private var selectedType: TextType = TextType.PLAIN

    companion object {
        const val TEXT_ITEM_ID = "TEXT_ITEM_ID"
        const val ANNOUNCE_PAGE_INDEX = "ANNOUNCE_PAGE_INDEX"

        fun newInstance(textItemId: Int, announcePageIndex: Int): EditTextField {
            return EditTextField()
                    .apply { arguments = Bundle().apply {
                        this.putInt(TEXT_ITEM_ID, textItemId)
                        this.putInt(ANNOUNCE_PAGE_INDEX, announcePageIndex)} }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_text_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTextTypeSpinner()
        initHeaderLayout()
        getTextOfItem()
    }

    private fun getItemIndex(): Int{
        return arguments?.getInt(TEXT_ITEM_ID)?.let { it } ?: 0
    }

    private fun getPageIndex(): Int{
        return arguments?.getInt(ANNOUNCE_PAGE_INDEX)?.let { it } ?: 0
    }

    private fun initHeaderLayout(){
        header.text = "Изменить текстовое поле"

        back_press.setOnClickListener {
            activity?.onBackPressed()
        }

        save.setOnClickListener {
            save(selectedType, dialog_edit_button_edit_text_title.text.toString())
            activity?.onBackPressed()
        }
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

    private fun save(selectedTextType: TextType, text: String){
        (AnnounceBuffer.content.announcePages[getPageIndex()].items?.get(getItemIndex()) as? Text)
                    ?.let {
                        it.text = text
                        it.textType = selectedTextType
                    }
    }

    private fun getTextOfItem(){
        val textt = (AnnounceBuffer.content.announcePages[getPageIndex()].items?.get(getItemIndex()) as? Text)?.text ?: ""
        dialog_edit_button_edit_text_title.setText(textt)
        val typText = (AnnounceBuffer.content.announcePages[getPageIndex()].items?.get(getItemIndex()) as? Text)?.textType ?: TextType.HEADER
        action_type_list.setSelection(typText.ordinal)
    }

}