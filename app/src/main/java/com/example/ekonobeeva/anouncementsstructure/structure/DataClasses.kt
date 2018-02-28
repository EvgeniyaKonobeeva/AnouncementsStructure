package com.example.ekonobeeva.anouncementsstructure.structure


data class Image (var type: ItemType,
                  var imageSize: ImageSize,
                  var imageUrl: String)

data class Text (var type: ItemType,
                 var text: String,
                 var textType: TextType)


data class Action (var type: ItemType,
                   var actionType: ActionType,
                   var text: String,
                   var textRef: String?)