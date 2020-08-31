package it.uniparthenope.tmm.giovannicastellano.calcalc

import kotlin.collections.*

class Food (names : ArrayList<String>, calories : Float, category : CATEGORY){
    private var names = arrayListOf<String>()
    var calories = 0.0f
    var category = CATEGORY.OTHER

    init {
        this.names = names;
        this.calories = calories;
        this.category = category
    }

    fun getName(index : Int) : String
    {
        if(index < names.size)
            return names[index]
        return names[0]
    }
}