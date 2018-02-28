package com.example.ekonobeeva.anouncementsstructure.clean.interactor

import com.example.ekonobeeva.anouncementsstructure.R.id.dialog_edit_button_edit_text_reference
import com.example.ekonobeeva.anouncementsstructure.R.id.dialog_edit_button_edit_text_title
import com.example.ekonobeeva.anouncementsstructure.activity.AnnounceBuffer
import com.example.ekonobeeva.anouncementsstructure.structure.*
import kotlinx.android.synthetic.main.dialog_edit_button.*

class AnnounceInteractor: IAnnounceInteractor {

    private var content: Content = Content(mutableListOf(), mutableListOf())
    private var contentDataBase: Content = modelDataBase()

    override fun getFromDatabase(): Content {
        /*
        взять из базы анонс, сохранить его в интеракторе
        распарсить json
         */
        return contentDataBase
    }

    override fun saveInDatabase() {
        contentDataBase = content
    }

    override fun editButton(index: Int, title: String, actionType: ActionType, reference: String) {
        content.actions?.get(index)?.let {
            it.actionType = actionType
            it.textRef = reference
            it.text = title
        }
    }

    override fun removeButton(index: Int) {
        if(index < content.actions?.size ?: 0 && index > 0) {
            content.actions?.removeAt(index)
        }
    }

    override fun addOrReplaceButton(index: Int, title: String, actionType: ActionType, reference: String) {
        val action = Action(ItemType.ACTION, actionType, title, reference)
        if(index > content.actions?.size ?: 0){

            content.actions?.add(action)

        }else{

            content.actions?.add(index, action)
        }
    }

    override fun addOrReplaceTextItem(index: Int, title: String, textType: TextType, announcementIndex: Int) {
        if(index < content.announcePages[announcementIndex].items?.size ?: 0) {
            content.announcePages[announcementIndex].items?.add(index, Text(ItemType.TEXT, title, textType))
        }else{
            content.announcePages[announcementIndex].items?.add(Text(ItemType.TEXT, title, textType))
        }
    }

    override fun editTextItem(index: Int, title: String, textType: TextType, announcementIndex: Int) {
        (content.announcePages[announcementIndex].items?.get(index) as? Text)
                ?.let {
                    it.text = title
                    it.textType = textType
                }
    }

    override fun addOrReplaceImageItem(index: Int, url: String, imageSize: ImageSize, announcementIndex: Int) {
        if(index < content.announcePages[announcementIndex].items?.size ?: 0) {
            content.announcePages[announcementIndex].items?.add(index, Image(ItemType.IMAGE, imageSize, url))
        }else{
            content.announcePages[announcementIndex].items?.add(Image(ItemType.IMAGE, imageSize, url))
        }
    }

    override fun removeItem(index: Int, announcementIndex: Int) {
        content.announcePages[announcementIndex].items?.removeAt(index)
    }


    override fun removePage(index: Int) {
        content.announcePages.removeAt(index)
    }

    fun modelDataBase(): Content{
        val imageItem13 = Image(ItemType.IMAGE, ImageSize.SIZE_3, "https://million-wallpapers.com/wallpapers/6/20/small/509300661331552.jpg")
        val imageItem12 = Image(ItemType.IMAGE, ImageSize.SIZE_2, "https://f1.ds-russia.ru/u_dirs/144/144037/p/6f71650b7c713843d80d5b6c125a63bb.jpg")
        val imageItem11 = Image(ItemType.IMAGE, ImageSize.SIZE_1, "https://b-track.com/uploads/user/photo/1/4/2/6/3/_/avatar_966bf4.jpg")
        val imageItem1f = Image(ItemType.IMAGE, ImageSize.FULL_SCREEN, "http://mw2.google.com/mw-panoramio/photos/medium/75185375.jpg")
        val textItemHeader = Text(ItemType.TEXT, "some header", TextType.HEADER)
        val textItemHeader1 = Text(ItemType.TEXT, "some header", TextType.HEADER)
        val textItemSubHeader = Text(ItemType.TEXT, "some sub header", TextType.SUB_HEADER)
        val textItemPlain = Text(ItemType.TEXT, "some plain some plain some plain some plain some plain some plain 1", TextType.PLAIN)
        val textItemPlain1 = Text(ItemType.TEXT, "some plain some plain some plain some plain some plain some plain 2", TextType.PLAIN)
        val textItemPlain2 = Text(ItemType.TEXT, "some plain some plain some plain some plain some plain some plain 3", TextType.PLAIN)
        val textItemPlain3 = Text(ItemType.TEXT, "some plain some plain some plain some plain some plain some plain 4", TextType.PLAIN)
        val textItemPlain4 = Text(ItemType.TEXT, "some plain some plain some plain some plain some plain some plain 5", TextType.PLAIN)
        val textItemPlain5 = Text(ItemType.TEXT, "some plain some plain some plain some plain some plain some plain 6", TextType.PLAIN)
        val textItemPlain6 = Text(ItemType.TEXT, "some plain some plain some plain some plain some plain some plain 7", TextType.PLAIN)
        val textItemPlain7 = Text(ItemType.TEXT, "some plain some plain some plain some plain some plain some plain 8", TextType.PLAIN)
        val textItemPlain8 = Text(ItemType.TEXT, "some plain some plain some plain some plain some plain some plain 9", TextType.PLAIN)
        val textItemPlain9 = Text(ItemType.TEXT, "some plain some plain some plain some plain some plain some plain 10", TextType.PLAIN)
        val textItemList = Text(ItemType.TEXT, "some list", TextType.LIST)
        val textItemList1 = Text(ItemType.TEXT, "some list1", TextType.LIST)
        val textItemList2 = Text(ItemType.TEXT, "some list2", TextType.LIST)
        val textItemList3 = Text(ItemType.TEXT, "some list3", TextType.LIST)


        val actionItem1 = Action(ItemType.ACTION, ActionType.DISMISS, "cancel", "")
        val actionItem2 = Action(ItemType.ACTION, ActionType.MODULE, "on fragment", "")

        val slide1 = AnnouncePage(mutableListOf(imageItem11, textItemHeader, textItemHeader1, textItemPlain, textItemPlain1, textItemPlain2, textItemPlain3,
                textItemPlain4,textItemPlain5,textItemPlain6), AlignType.CENTER)
        val slide2 = AnnouncePage(mutableListOf(imageItem13, textItemHeader, textItemSubHeader, textItemList, textItemList1, textItemList2, textItemList3), AlignType.TOP)
        val slide3 = AnnouncePage(mutableListOf(imageItem12, textItemHeader, textItemPlain, textItemPlain1, textItemPlain2), AlignType.CENTER)
        val slide4 = AnnouncePage(mutableListOf(imageItem1f, textItemHeader, textItemSubHeader, textItemList, textItemList1, textItemList2, textItemList3), AlignType.TOP)

        return Content(mutableListOf(), mutableListOf())
    }


}