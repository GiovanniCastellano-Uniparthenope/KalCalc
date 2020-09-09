package it.uniparthenope.tmm.giovannicastellano.calcalc

class static {
    companion object {
        @JvmStatic var standardFoods = ArrayList<Food>()
        @JvmStatic var customFoods = ArrayList<Food>()
        @JvmStatic var customFoodsFile = String()
        @JvmStatic var meal = ArrayList<String>()
        @JvmStatic var language = LANGUAGE.ENGLISH
        @JvmStatic var highestCal = String()
        @JvmStatic var highestCarb = String()
        @JvmStatic var highestFats = String()
        @JvmStatic var highestProts = String()
        @JvmStatic var hasMeat = false
        @JvmStatic var hasVegs = false
        @JvmStatic var hasAnimalD = false
        @JvmStatic var hasFish = false
        @JvmStatic var hasOther = false
    }
}