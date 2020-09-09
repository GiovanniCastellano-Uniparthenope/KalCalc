package it.uniparthenope.tmm.giovannicastellano.calcalc

import org.json.JSONObject
import java.lang.Exception
import java.util.*
import kotlin.collections.*

class JSONparser {
    fun readFoodsFromFile(file : String): Int {
        try {
            var foodJSON = JSONObject(file)
            var foodNames = foodJSON.optJSONArray("foods")
            var food: Food

            for (i in 0 until foodNames.length()) {
                val jsonObject = foodNames.getJSONObject(i)
                val namesarray = jsonObject.optJSONArray("names")
                var names = ArrayList<String>()
                for (i in 0 until namesarray.length())
                    names.add(namesarray.getString(i))

                val portion = jsonObject.optString("portion").toFloat()
                val calories = jsonObject.optString("calories").toFloat()
                val carbohydrates = jsonObject.optString("carbohydrates").toFloat()
                val fats = jsonObject.optString("fats").toFloat()
                val proteins = jsonObject.optString("proteins").toFloat()
                val intcategory = jsonObject.optString("category").toInt()
                var category = CATEGORY.OTHER

                if (intcategory == 1)
                    category = CATEGORY.MEAT
                else if (intcategory == 2)
                    category = CATEGORY.VEGETABLES
                else if (intcategory == 3)
                    category = CATEGORY.ANIMAL_DERIVATE
                else if (intcategory == 4)
                    category = CATEGORY.FISH
                else
                category = CATEGORY.OTHER

                food = Food(names, portion, calories, carbohydrates, fats, proteins, category)
                static.standardFoods.add(food)
            }

            return foodNames.length()
        }
        catch (E : Exception)
        {
            return -1
        }
    }

    fun readFoodsFromCustomFile(file : String): Int {
        try {
            var foodJSON = JSONObject(file)
            var foodNames = foodJSON.optJSONArray("foods")
            static.customFoodsFile = file;
            var food: Food

            for (i in 0 until foodNames.length()) {
                val jsonObject = foodNames.getJSONObject(i)
                val namesarray = jsonObject.optJSONArray("names")
                var names = ArrayList<String>()
                for (i in 0 until namesarray.length())
                    names.add(namesarray.getString(i))

                val portion = jsonObject.optString("portion").toFloat()
                val calories = jsonObject.optString("calories").toFloat()
                val carbohydrates = jsonObject.optString("carbohydrates").toFloat()
                val fats = jsonObject.optString("fats").toFloat()
                val proteins = jsonObject.optString("proteins").toFloat()
                val intcategory = jsonObject.optString("category").toInt()
                var category = CATEGORY.OTHER

                if (intcategory == 1)
                    category = CATEGORY.MEAT
                else if (intcategory == 2)
                    category = CATEGORY.VEGETABLES
                else if (intcategory == 3)
                    category = CATEGORY.ANIMAL_DERIVATE
                else if (intcategory == 4)
                    category = CATEGORY.FISH
                else
                    category = CATEGORY.OTHER

                food = Food(names, portion, calories, carbohydrates, fats, proteins, category)
                static.customFoods.add(food)
            }

            return foodNames.length()
        }
        catch (E : Exception)
        {
            return -1
        }

    }

    fun addCustomFood(food : Food)
    {
        var jsonstring : String
        if(static.customFoodsFile.contains("name"))
            jsonstring = ",\n\t\t{\n"
        else
            jsonstring = "\t\t{\n"


        jsonstring += "\t\t\t\"names\": ["
        val name = food.getName(0)
        jsonstring += "\"" + name + "\"],\n"
        jsonstring += "\t\t\t\"portion\": "
        jsonstring += "\"" + food.portion.toString() + "\",\n"
        jsonstring += "\t\t\t\"calories\": "
        jsonstring += "\"" + food.calories.toString() + "\",\n"
        jsonstring += "\t\t\t\"carbohydrates\": "
        jsonstring += "\"" + food.carbohydrates.toString() + "\",\n"
        jsonstring += "\t\t\t\"fats\": "
        jsonstring += "\"" + food.fats.toString() + "\",\n"
        jsonstring += "\t\t\t\"proteins\": "
        jsonstring += "\"" + food.proteins.toString() + "\",\n"
        jsonstring += "\t\t\t\"category\": "
        jsonstring += "\"" + food.category.type.toString() + "\"\n"
        jsonstring += "\n\t\t}\n"

        static.customFoodsFile = static.customFoodsFile.substring(0, static.customFoodsFile.length - 6) + jsonstring
        static.customFoodsFile += "\n\t]\n}"

        static.customFoods.add(food)
    }

    fun resetCustomFoods()
    {
        val jsonfile = "{\n\t\"foods\": [\n\t\t\n\t]\n}"
        static.customFoodsFile = jsonfile
        static.customFoods.clear()
    }
}






