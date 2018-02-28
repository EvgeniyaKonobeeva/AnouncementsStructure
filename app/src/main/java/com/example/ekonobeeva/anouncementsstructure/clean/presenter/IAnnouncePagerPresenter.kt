package com.example.ekonobeeva.anouncementsstructure.clean.presenter

import com.example.ekonobeeva.anouncementsstructure.structure.Action

interface IAnnouncePagerPresenter {
    /*
    берем все из базы данных
     */
    fun getActionsButtons(): MutableList<Action>
    fun getAnnounceSize(): Int

}