package com.example.ekonobeeva.anouncementsstructure.activity.edit_announcenment

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.ekonobeeva.anouncementsstructure.activity.annoncenments_view.PageFragment

class EditViewPagerAdapter(private val fragmentManager: FragmentManager, var itemCount: Int): FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return PageFragment.newInstance(position)
    }

    override fun getCount(): Int = itemCount

}
