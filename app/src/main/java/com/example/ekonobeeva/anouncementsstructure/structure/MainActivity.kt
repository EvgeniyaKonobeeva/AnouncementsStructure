package com.example.ekonobeeva.anouncementsstructure.structure

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.ekonobeeva.anouncementsstructure.R
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gson = Gson();
        val stringJson = gson.toJson(content)
        textTT.text = gson.toJson(content)
        Log.d("TAG", gson.toJson(content))

        val gsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapter(Content::class.java, JsonContentDeserializer())
        val gson1 = gsonBuilder.create()

        Log.d("TAGG", gson1.fromJson(stringJson, Content::class.java).toString())

    }


    val imageItem = Image(ItemType.IMAGE, ImageSize.FULL_SCREEN, "url1")
    val textItem = Text(ItemType.TEXT, "some text", TextType.HEADER)


    val actionItem1 = Action(ItemType.ACTION, ActionType.DISMISS, "cancel", "")

    val slide1 = AnnouncePage(mutableListOf(imageItem, textItem), AlignType.CENTER)
    val slide2 = AnnouncePage(mutableListOf(imageItem, textItem), AlignType.CENTER)
//    val slide3 = AnnouncePage(listOf(imageItem, textItem), listOf(actionItem1, actionItem2, actionItem3))

    val content = Content(mutableListOf(slide1, slide2), mutableListOf(actionItem1))



}
