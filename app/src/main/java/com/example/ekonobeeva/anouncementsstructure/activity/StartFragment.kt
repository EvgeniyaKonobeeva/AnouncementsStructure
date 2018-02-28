package com.example.ekonobeeva.anouncementsstructure.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.example.ekonobeeva.anouncementsstructure.R
import kotlinx.android.synthetic.main.fragment_strat.*

class StartFragment: MvpAppCompatFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_strat, container, false)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        start_button.setOnClickListener { replaceFragment(CreateNewAnnounceFragment()) }
    }


    private fun replaceFragment(fragment: Fragment){
        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.container, fragment)?.addToBackStack(null)?.commit()
    }

}