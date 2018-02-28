package com.example.ekonobeeva.anouncementsstructure.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.example.ekonobeeva.anouncementsstructure.R
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter
import kotlinx.android.synthetic.main.fragment_create_new_announce.*

class CreateNewAnnounceFragment: MvpAppCompatFragment(), ICreateAnnounceView {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_create_new_announce, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        create_new_announce_fab.setMenuListener(object : SimpleMenuListenerAdapter(){

        })
    }

}