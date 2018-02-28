package com.example.ekonobeeva.anouncementsstructure.activity.disalogs

import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.ekonobeeva.anouncementsstructure.R
import com.example.ekonobeeva.anouncementsstructure.activity.AnnounceBuffer
import com.example.ekonobeeva.anouncementsstructure.structure.Action
import com.example.ekonobeeva.anouncementsstructure.structure.ActionType
import com.example.ekonobeeva.anouncementsstructure.structure.ItemType
import kotlinx.android.synthetic.main.dialog_edit_button.*
import kotlinx.android.synthetic.main.header_save_back_press.*

class EditButtonDialog: DialogFragment(){
    /*
    по кнопке сохранить мы делаем изменнеия в зависимости от выбранных параметров
     */

    val TAG = "EditButtonDialog"
    private var selectedIndex = 0 // индекс элемента
    private var selectedOperationIndex = 0
    private var selectedAction: ActionType = ActionType.DISMISS
    var onSaveChangesListener: OnSaveChangesListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(activity, R.style.AppTheme)
        if (dialog.window != null) {
            dialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        }
        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.dialog_edit_button, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initOperationSpinner()
        initIndexSpinner()
        initActionSpinner()
        initHeaderLayout()
    }

    private fun initHeaderLayout(){
        Log.d(TAG, "initHeaderLayout: ");
        header.text = "Изменить кнопку"

        back_press.setOnClickListener {
            Log.d(TAG, "initHeaderLayout: back_press");
            dismiss()
        }

        save.setOnClickListener {
            saveResult()
            Log.d(TAG, "initHeaderLayout: save ");
            onSaveChangesListener?.onSaveChangesListener()
            dismiss()
        }
    }

    private fun initOperationSpinner(){
        val spinnerAdapter = ArrayAdapter.createFromResource(activity.applicationContext, R.array.operations_array, android.R.layout.simple_spinner_item)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        operation_list.adapter = spinnerAdapter
        operation_list.setSelection(0)

        operation_list.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                // empty
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedOperationIndex = p2

                when(selectedOperationIndex){
                    1,2 -> { getInfoAboutAction(selectedIndex)} // Удалить Изменить
                }
            }
        }
    }

    private fun initIndexSpinner(){

        val indexList = mutableListOf<Int>()
        for(i: Int in 0 .. getCountButtons()){
            indexList.add(i)
        }
        val spinnerAdapter = ArrayAdapter(activity.applicationContext, android.R.layout.simple_spinner_item, indexList)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        index_list.adapter = spinnerAdapter


        index_list.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                // empty
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedIndex = p2

                when(selectedOperationIndex){
                    1,2 -> { getInfoAboutAction(selectedIndex)} // Удалить Изменить
                }
            }
        }

        index_list.setSelection(0)
    }

    private fun initActionSpinner(){
        val spinnerAdapter = ArrayAdapter(activity.applicationContext, android.R.layout.simple_spinner_item, ActionType.values())
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        action_type_list.adapter = spinnerAdapter
        action_type_list.setSelection(0)

        action_type_list.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                // empty
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedAction = ActionType.values()[p2]
            }
        }
    }


    private fun getCountButtons(): Int{
        return AnnounceBuffer.getModel().actions?.let {
            it.size
        } ?: 0
    }

    private fun getInfoAboutAction(selectedIndex: Int){

        if(selectedIndex < getCountButtons()) {
            AnnounceBuffer.getModel().actions?.get(selectedIndex)
                    ?.let {
                        dialog_edit_button_edit_text_title.setText(it.text)
                        dialog_edit_button_edit_text_reference.setText(it.textRef)
                        action_type_list.setSelection(it.actionType.ordinal)
                    }
        }

    }

    private fun saveResult(){
        //сохраняем в бд
        Log.d(TAG, "saveResult: ");
        when(selectedOperationIndex){
            0 -> { addButton(selectedIndex)} // Добавить
            1 -> { removeButton(selectedIndex)} // Удалить
            2 -> {editButton(selectedIndex)} // Изменить
        }
    }


    private fun addButton(selectedIndex : Int){
        Log.d(TAG, "addButton: ");
        val action = Action(ItemType.ACTION, selectedAction, dialog_edit_button_edit_text_title.text.toString(), dialog_edit_button_edit_text_reference.text.toString())
        if(selectedIndex > getCountButtons()-1){

            AnnounceBuffer.getModel().actions?.add(action)

        }else{

            AnnounceBuffer.getModel().actions?.add(selectedIndex, action)
        }

    }

    private fun editButton(selectedIndex : Int) {
        if (selectedIndex < getCountButtons()){
            val action = AnnounceBuffer.getModel().actions?.get(selectedIndex)
            action?.let {
                it.text = dialog_edit_button_edit_text_title.text.toString()
                it.textRef = dialog_edit_button_edit_text_reference.text.toString()
                it.actionType = selectedAction
            }
        }
    }

    private fun removeButton(selectedIndex : Int){
        if(selectedIndex < getCountButtons()) {
            AnnounceBuffer.getModel().actions?.removeAt(selectedIndex)
        }
    }

    interface OnSaveChangesListener{
        fun onSaveChangesListener()
    }
}