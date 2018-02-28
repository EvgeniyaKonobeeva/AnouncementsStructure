package com.example.ekonobeeva.anouncementsstructure.clean.interactor

import android.support.v7.widget.DialogTitle
import com.example.ekonobeeva.anouncementsstructure.structure.ActionType
import com.example.ekonobeeva.anouncementsstructure.structure.Content
import com.example.ekonobeeva.anouncementsstructure.structure.ImageSize
import com.example.ekonobeeva.anouncementsstructure.structure.TextType
import java.lang.ref.PhantomReference

interface IAnnounceInteractor {
    fun getFromDatabase(): Content
    fun saveInDatabase()

    fun editButton(index: Int, title: String, actionType: ActionType, reference: String)
    fun removeButton(index: Int)
    fun addOrReplaceButton(index: Int, title: String, actionType: ActionType, reference: String)


    fun addOrReplaceTextItem(index: Int, title: String, textType: TextType, announcementIndex: Int)
    fun editTextItem(index: Int, title: String, textType: TextType, announcementIndex: Int)

    fun addOrReplaceImageItem(index: Int, url: String, imageSize: ImageSize, announcementIndex: Int)


    fun removeItem(index: Int, announcementIndex: Int)
    fun removePage(index: Int)
}