package com.example.ekonobeeva.anouncementsstructure.activity.annoncenments_view

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class ViewPagerAdapter(private val fragmentManager: FragmentManager, private val itemCount: Int): FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return PageFragment.newInstance(position)
    }

    override fun getCount(): Int = itemCount
}
