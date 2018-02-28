package com.example.ekonobeeva.anouncementsstructure.activity.edit_announcenment

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.ekonobeeva.anouncementsstructure.R
import com.example.ekonobeeva.anouncementsstructure.activity.AnnounceBuffer
import com.example.ekonobeeva.anouncementsstructure.activity.disalogs.EditButtonDialog
import com.example.ekonobeeva.anouncementsstructure.structure.Action
import com.example.ekonobeeva.anouncementsstructure.structure.ActionType
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter
import kotlinx.android.synthetic.main.edit_announcement_pager.*

class EditAnnouncementPager : Fragment(){
    val TAG = "EditAnnouncementPager"

    private lateinit var pagerAdapter : EditViewPagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView: ");
        val view = inflater.inflate(R.layout.edit_announcement_pager, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val itemsCount = AnnounceBuffer.getModel().announcePages.size
        pagerAdapter = EditViewPagerAdapter(childFragmentManager, itemsCount )
        announcement_pager_view_pager.adapter = pagerAdapter
        addAllButtons()
        initFabSpeedDial()

    }

    override fun onResume() {
        // обновлять модель из базы
        Log.d(TAG, "onResume: ")
        super.onResume()
        addAllButtons()
        pagerAdapter.notifyDataSetChanged()

    }


    private fun addAllButtons(){
        button_layout.removeAllViewsInLayout()
        AnnounceBuffer.getModel().actions?.forEach {
            element ->
            button_layout.addView(mapListeners(element))
        }

    }


    private fun mapListeners(action: Action): Button{
        val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val button = inflater.inflate(R.layout.my_button, null) as Button
        button.text = action.text


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

    private fun initFabSpeedDial(){
        create_new_announce_fab.setMenuListener(object : SimpleMenuListenerAdapter(){
            override fun onMenuItemSelected(menuItem: MenuItem?): Boolean {
                menuItem?.let {
                    when(it.itemId){
                        R.id.create_page -> {
                            replaceFragment(EditPageFragment.newInstance(-1))
                        }
                        R.id.add_button -> {
                            showEditButtonsDialog()
                        }
                        R.id.edit_page -> {
                            replaceFragment(EditPageFragment.newInstance(announcement_pager_view_pager.currentItem))

                            /*
                            редактируем теукущий слайд
                            уже внутри, если нажали редактировать - удалить его составляющие по свайпу, редактировать их по нажатию, добавить новое поле по флоат баттон
                            если все айтемы удалили, то что ? удаляем весь слайд

                             */
                        }
                        R.id.delete_page -> {
                            deletePage()
                        }
                    }
                }

                return true
            }
        })
    }

    private fun replaceFragment(fragment: Fragment){
        (activity as? AppCompatActivity)?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.container, fragment)
                ?.addToBackStack(null)
                ?.commit()
    }

    private fun showEditButtonsDialog(){
        val dialog = EditButtonDialog()
        dialog.onSaveChangesListener = object : EditButtonDialog.OnSaveChangesListener{
            override fun onSaveChangesListener() {
                onResume()
            }
        }
        dialog.show(activity?.fragmentManager, "editButton")
    }

    // метод презентера
    private fun removeAnnouncePage(index: Int){
        AnnounceBuffer.content.announcePages.removeAt(announcement_pager_view_pager.currentItem)
    }

    private fun deletePage(){
        var newCurrentItem = announcement_pager_view_pager.currentItem

        removeAnnouncePage(newCurrentItem)

        pagerAdapter.itemCount = pagerAdapter.itemCount - 1

        announcement_pager_view_pager.adapter = pagerAdapter

        if(newCurrentItem > 0){ newCurrentItem-- }

        announcement_pager_view_pager.currentItem = newCurrentItem
    }



}