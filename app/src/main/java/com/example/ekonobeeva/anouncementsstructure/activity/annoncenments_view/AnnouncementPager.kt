package com.example.ekonobeeva.anouncementsstructure.activity.annoncenments_view

import android.content.Context
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.ekonobeeva.anouncementsstructure.R
import com.example.ekonobeeva.anouncementsstructure.activity.AnnounceBuffer
import com.example.ekonobeeva.anouncementsstructure.structure.Action
import com.example.ekonobeeva.anouncementsstructure.structure.ActionType
import kotlinx.android.synthetic.main.announcement_pager.*

class AnnouncementPager: Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.announcement_pager, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val itemsCount = AnnounceBuffer.getModel().announcePages.size
        announcement_pager_view_pager.adapter = ViewPagerAdapter(childFragmentManager, itemsCount )
        addButtons()

    }


    private fun addButtons(){
        val linearLayout = button_layout

//        val buttonsList = mutableListOf<Button>()

        AnnounceBuffer.getModel().actions?.forEach {
            element ->
//            buttonsList.add(mapListeners(element))
            linearLayout.addView(mapListeners(element))
        }

    }


    private fun mapListeners(action: Action): Button{
        val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val button = inflater.inflate(R.layout.my_button, null) as Button
        button.setText(action.text)


        val listener = when(action.actionType){
            ActionType.DISMISS ->{
                View.OnClickListener {
                    Toast.makeText(context, "DISMISS", Toast.LENGTH_SHORT).show()
                }
            }
            ActionType.MODULE ->{
                View.OnClickListener {
                    // переход в модуль   работа с роутером здесь
                    Toast.makeText(context, "MODULE", Toast.LENGTH_SHORT).show()
                }
            }
            ActionType.EXTERNAL_REF ->{
                View.OnClickListener {
                    // переход по ссылке   работа с роутером здесь
                    Toast.makeText(context, "EXTERNAL_REF", Toast.LENGTH_SHORT).show()
                }
            }
        }

        button.setOnClickListener(listener)

        return button
    }



}