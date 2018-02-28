package com.example.ekonobeeva.anouncementsstructure.structure

import android.util.Log
import com.google.gson.*
import java.lang.reflect.Type

/**
 * {	"actions":
[
{
"actionType":"DISMISS",
"text":"cancel",
"textRef":"",
"type":"ACTION"
}
],
"announcePages":
[
{
"alignType":"CENTER",
"items":
[
{
"imageSize":"FULL_SCREEN",
"imageUrl":"url1",
"type":"IMAGE"
},

{
"text":"some text",
"textType":"HEADER",
"type":"TEXT"
}
]
}
]
}

 */

class JsonContentDeserializer : JsonDeserializer<Content> {

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Content? {
        json?.let {
            return Content(mapToPages(json.asJsonObject), mapToActions(json.asJsonObject))
        }
                ?: return null

    }

    private fun mapToPages(jsonObject: JsonObject): MutableList<AnnouncePage> {

        val resultLisPages = mutableListOf<AnnouncePage>()
        val jsonList: JsonArray = jsonObject.getAsJsonArray("announcePages")
        jsonList.forEach {

            val alignType = it.asJsonObject.get("alignType").asString
            val items = mapToItems(it.asJsonObject.get("items").asJsonArray)

            resultLisPages.add(AnnouncePage(items, AlignType.valueOf(alignType)))
    }

        return resultLisPages
    }


    private fun mapToItems(jsonArray: JsonArray): MutableList<Any>{
        val resultListItems = mutableListOf<Any>()
        jsonArray.forEach {

            val typeField = it.asJsonObject.get("type").asString

            val item: Any = when(typeField){

                ItemType.IMAGE.name -> {mapToImage(it.asJsonObject)}
                ItemType.TEXT.name -> {mapToText(it.asJsonObject)}
                else -> ""
            }
            resultListItems.add(item)

        }

        return resultListItems
    }

    private fun mapToActions(jsonObject: JsonObject): MutableList<Action> {
        val resultLisActions = mutableListOf<Action>()
        val jsonList: JsonArray = jsonObject.getAsJsonArray("actions")
        jsonList.forEach {
            element ->
            val actionType = element.asJsonObject.get("actionType").asString
            val text = element.asJsonObject.get("text").asString
            val textRef = element.asJsonObject.get("textRef").asString
            val type = element.asJsonObject.get("type").asString

            val action = Action(ItemType.valueOf(type), ActionType.valueOf(actionType), text, textRef)
            resultLisActions.add(action)

        }

        return resultLisActions
    }

    private fun mapToImage(jsonObject: JsonObject): Image {
        val imageSize = jsonObject.get("imageSize").asString
        val imageUrl = jsonObject.get("imageUrl").asString
        val type = jsonObject.get("type").asString
        return Image(ItemType.valueOf(type), ImageSize.valueOf(imageSize), imageUrl)
    }

    private fun mapToText(jsonObject: JsonObject): Text {
        val text = jsonObject.get("text").asString
        val textType = jsonObject.get("textType").asString
        val type = jsonObject.get("type").asString
        return Text(ItemType.valueOf(type),  text, TextType.valueOf(textType))
    }

}