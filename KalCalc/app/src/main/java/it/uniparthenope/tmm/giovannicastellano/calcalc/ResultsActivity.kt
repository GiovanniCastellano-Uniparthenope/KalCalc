package it.uniparthenope.tmm.giovannicastellano.calcalc

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlin.collections.ArrayList
import com.github.mikephil.charting.*
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

class ResultsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)
        supportActionBar?.hide()
        val context = this
        val meal = parseMeal()

        var nutritionalValues = ArrayList<PieEntry>()
        nutritionalValues.add(PieEntry(meal.calories, "Calories (kcal)"))
        nutritionalValues.add(PieEntry(meal.carbohydrates, "Carbohydrates (g)"))
        nutritionalValues.add(PieEntry(meal.fats, "Fats (g)"))
        nutritionalValues.add(PieEntry(meal.proteins, "Proteins (g)"))

        var pieDataSet = PieDataSet(nutritionalValues, "")
        pieDataSet.setColors(Color.RED, Color.GREEN, Color.YELLOW, Color.BLUE)
        pieDataSet.valueTextColor = Color.BLACK
        pieDataSet.valueTextSize = 12f


        var pieData = PieData(pieDataSet)

        val pieChart = findViewById<com.github.mikephil.charting.charts.PieChart>(R.id.pieChart)
        pieChart.data = pieData;
        pieChart.description.isEnabled = false
        if(meal.calories > 0 || meal.carbohydrates > 0 || meal.fats > 0 || meal.proteins > 0)
            pieChart.centerText = "Nutritional values of the meal"
        else
            pieChart.centerText = "No foods found"
        pieChart.animate()
        pieChart.setDrawEntryLabels(false)
    }

    fun parseMeal() : Food
    {
        val names = ArrayList<String>()
        names.add("Meal")
        var meal = Food(names, 0f, 0f, 0f, 0f, 0f, CATEGORY.OTHER)
        var foodName : String
        var foodQuantity : String
        var quantity : Float
        var i : Int
        var found = false
        for(string in static.meal)
        {
            foodName = ""
            foodQuantity = ""
            i = 0
            while(string[i] != ':')
            {
                foodName += string[i]
                i++
            }
            i+=2
            while(string[i] != 'g')
            {
                foodQuantity += string[i]
                i++
            }
            quantity = foodQuantity.toFloat()

            var currentFood = static.standardFoods[0]
            for(food in static.standardFoods)
            {
                if(food.getName(static.language.type) == foodName)
                {
                    currentFood = food
                    found = true
                    break
                }
            }
            if(found)
            {
                meal.proteins += currentFood.proteins * quantity
                meal.carbohydrates += currentFood.carbohydrates * quantity
                meal.fats += currentFood.fats * quantity
                meal.calories += currentFood.calories * quantity
            }
            else
            {
                for(food in static.customFoods)
                {
                    if(food.getName(static.language.type) == foodName)
                    {
                        currentFood = food
                        found = true
                        break
                    }
                }
                if(found)
                {
                    meal.proteins += currentFood.proteins * quantity
                    meal.carbohydrates += currentFood.carbohydrates * quantity
                    meal.fats += currentFood.fats * quantity
                    meal.calories += currentFood.calories * quantity
                }
            }

            found = false
        }

        return meal
    }
}