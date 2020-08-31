package it.uniparthenope.tmm.giovannicastellano.calcalc

import android.content.Context
import org.json.JSONObject
import kotlin.collections.*

class JSONparser {
    fun readFoodsFromFile(file : String): Int {
        var foodJSON = JSONObject(file)
        var foodNames = foodJSON.optJSONArray("foods")
        var allfoods = allFoods.standardFoods
        var food : Food

        for (i in 0 until foodNames.length())
        {
            val jsonObject = foodNames.getJSONObject(i)
            val namesarray = jsonObject.optJSONArray("names")
            var names = ArrayList<String>()
            for(i in 0 until namesarray.length())
                names.add(namesarray.getString(i))

            val calories = jsonObject.optString("calories").toFloat()
            val intcategory = jsonObject.optString("category").toInt()
            var category : CATEGORY

            if(intcategory == 1)
                category = CATEGORY.MEAT
            else if(intcategory == 2)
                category = CATEGORY.VEGETABLES
            else if(intcategory == 3)
                category = CATEGORY.ANIMAL_DERIVATE
            else if(intcategory == 4)
                category = CATEGORY.FISH
            else (intcategory == 5)
                category = CATEGORY.OTHER

            food = Food(names, calories, category)
            allfoods.add(food)
        }

        return foodNames.length()
    }

    fun readFoodsFromCustomFile(file : String): Int {
        var foodJSON = JSONObject(file)
        var foodNames = foodJSON.optJSONArray("foods")
        var allfoods = allFoods.customFoods
        allFoods.customFoodsFile = file;
        var food : Food

        for (i in 0 until foodNames.length())
        {
            val jsonObject = foodNames.getJSONObject(i)
            val namesarray = jsonObject.optJSONArray("names")
            var names = ArrayList<String>()
            for(i in 0 until namesarray.length())
                names.add(namesarray.getString(i))

            val calories = jsonObject.optString("calories").toFloat()
            val intcategory = jsonObject.optString("category").toInt()
            var category : CATEGORY

            if(intcategory == 1)
                category = CATEGORY.MEAT
            else if(intcategory == 2)
                category = CATEGORY.VEGETABLES
            else if(intcategory == 3)
                category = CATEGORY.ANIMAL_DERIVATE
            else if(intcategory == 4)
                category = CATEGORY.FISH
            else (intcategory == 5)
                category = CATEGORY.OTHER

            food = Food(names, calories, category)
            allfoods.add(food)
        }

        return foodNames.length()
    }

    fun addCustomFood(food : Food)
    {
        var jsonstring = ",\n\t\t{\n"
        jsonstring += "\t\t\t\"names\": ["
        val name = food.getName(0)
        jsonstring += "\"" + name + "\"]\n"
        jsonstring += "\t\t\t\"calories\": "
        jsonstring += "\"" + food.calories.toString() + "\"\n"
        jsonstring += "\t\t\t\"category\": "
        jsonstring += "\"" + food.category.type.toString() + "\"\n"
        jsonstring += "\n\t\t}\n"

        allFoods.customFoodsFile = allFoods.customFoodsFile.substring(0, allFoods.customFoodsFile.length - 10) + jsonstring
        allFoods.customFoodsFile += "\n\t]\n}"
    }

    fun resetCustomFoods()
    {
        val jsonfile = "{\n\t\"foods\": [\n\t\t\n\t]\n}"
        allFoods.customFoodsFile = jsonfile
        print(jsonfile + "\n")
    }
}






