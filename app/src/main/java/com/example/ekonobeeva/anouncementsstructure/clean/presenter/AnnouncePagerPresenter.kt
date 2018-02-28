package com.example.ekonobeeva.anouncementsstructure.clean.presenter

import com.example.ekonobeeva.anouncementsstructure.structure.Action

class AnnouncePagerPresenter: IAnnouncePagerPresenter {
    override fun getActionsButtons(): MutableList<Action> {
        return mutableListOf()
    }

    override fun getAnnounceSize(): Int {
        return 0
    }
}