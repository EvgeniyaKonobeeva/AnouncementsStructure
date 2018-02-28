package com.example.ekonobeeva.anouncementsstructure.structure

enum class ItemType{
    IMAGE,
    TEXT,
    ACTION
}

enum class ImageSize(val width: Int, val heigth: Int){
    FULL_SCREEN(0, 0), // на весь экран но не за его пределами
    SIZE_1(52, 52), // самый большой
    SIZE_2(121, 121),
    SIZE_3(328, 200) // самый маленький
}

enum class TextType{
    HEADER,
    LIST,
    PLAIN,
    SUB_HEADER
}

enum class ModulesTag {
    STEPS,
    PULSE,
    WEIGHT
}


enum class AlignType(private val float: Float){
    CENTER(0.5f),
    TOP(0f);

    fun getAssociatedBias():Float{
        return float
    }
}

enum class ActionType{
    DISMISS,
    MODULE,
    EXTERNAL_REF
}

