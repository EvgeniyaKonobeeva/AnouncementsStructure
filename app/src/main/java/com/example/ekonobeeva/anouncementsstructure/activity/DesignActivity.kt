package com.example.ekonobeeva.anouncementsstructure.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import com.arellomobile.mvp.MvpAppCompatActivity
import com.example.ekonobeeva.anouncementsstructure.R
import com.example.ekonobeeva.anouncementsstructure.activity.annoncenments_view.AnnouncementPager
import com.example.ekonobeeva.anouncementsstructure.activity.edit_announcenment.EditAnnouncementPager

class DesignActivity: MvpAppCompatActivity() {
    val TAG = "DesignActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_design)
        replaceFragment(EditAnnouncementPager())
    }

    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit()
    }

    override fun onBackPressed() {
        Log.d(TAG, "onBackPressed: ");
        super.onBackPressed()
        
    }





}