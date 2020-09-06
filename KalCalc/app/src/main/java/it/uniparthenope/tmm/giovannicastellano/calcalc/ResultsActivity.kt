package it.uniparthenope.tmm.giovannicastellano.calcalc

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlin.collections.ArrayList
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class ResultsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)
        supportActionBar?.hide()
        val context = this

        setTexts()
        val meal = parseMeal()

        var nutritionalValues = ArrayList<PieEntry>()
        nutritionalValues.add(PieEntry(meal.calories, "Calories (kcal)"))
        nutritionalValues.add(PieEntry(meal.carbohydrates, "Carbohydrates (g)"))
        nutritionalValues.add(PieEntry(meal.fats, "Fats (g)"))
        nutritionalValues.add(PieEntry(meal.proteins, "Proteins (g)"))

        var pieDataSet = PieDataSet(nutritionalValues, "")
        pieDataSet.setColors(Color.RED, Color.GREEN, Color.YELLOW, Color.BLUE)
        pieDataSet.valueTextColor = Color.BLACK
        pieDataSet.valueTextSize = 18f


        var pieData = PieData(pieDataSet)

        val pieChart = findViewById<com.github.mikephil.charting.charts.PieChart>(R.id.pieChart)
        pieChart.data = pieData;
        pieChart.description.isEnabled = false
        if(meal.calories > 0 || meal.carbohydrates > 0 || meal.fats > 0 || meal.proteins > 0)
            pieChart.centerText = "Nutritional values"
        else
            pieChart.centerText = "No foods found"
        pieChart.setCenterTextSize(18f)
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

    fun setTexts()
    {
        val languageManager = LanguageManager()
        languageManager.englishTexts.add("The food with the highest calories value is: ")
        languageManager.italianTexts.add("Il cibo con il valore calorico più alto è: ")
        languageManager.englishTexts.add("The food with the highest carbohydrates value is: ")
        languageManager.italianTexts.add("Il cibo con il maggior numero di carboidrati è: ")
        languageManager.englishTexts.add("The food with the highest fats value is: ")
        languageManager.italianTexts.add("Il cibo con il maggior numero di grassi è: ")
        languageManager.englishTexts.add("The food with the highest proteins value is: ")
        languageManager.italianTexts.add("Il cibo con il maggior numero di proteine è: ")

        findViewById<TextView>(R.id.CaloriesTV).text = languageManager.getStringAt(0)
        findViewById<TextView>(R.id.CarbTV).text = languageManager.getStringAt(1)
        findViewById<TextView>(R.id.FatsTV).text = languageManager.getStringAt(2)
        findViewById<TextView>(R.id.ProteinsTV).text = languageManager.getStringAt(3)
    }
}