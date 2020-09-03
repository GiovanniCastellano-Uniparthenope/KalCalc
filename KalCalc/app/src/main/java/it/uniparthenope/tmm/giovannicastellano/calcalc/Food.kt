package it.uniparthenope.tmm.giovannicastellano.calcalc

import kotlin.collections.*

class Food (names : ArrayList<String>, portion : Float, calories : Float, carbohydrates : Float, fats : Float, proteins : Float, category : CATEGORY){
    private var names = arrayListOf<String>()
    var calories = 0.0f
    var portion = 0.0f
    var carbohydrates = 0.0f
    var fats = 0.0f
    var proteins = 0.0f
    var category = CATEGORY.OTHER

    init {
        this.names = names
        this.portion = portion
        this.calories = calories
        this.carbohydrates = carbohydrates
        this.fats = fats
        this.proteins = proteins
        this.category = category
    }

    fun getName(index : Int) : String
    {
        if(index < names.size)
            return names[index]
        return names[0]
    }
}