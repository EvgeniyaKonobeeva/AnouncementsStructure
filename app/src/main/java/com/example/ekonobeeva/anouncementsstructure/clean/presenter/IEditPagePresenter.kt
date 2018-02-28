package com.example.ekonobeeva.anouncementsstructure.clean.presenter

import com.example.ekonobeeva.anouncementsstructure.structure.Content

interface IEditPagePresenter {
    fun safeInDatabase()
    fun getFromDatabase(): Content
    fun addPageToEnd(content: Content)


}