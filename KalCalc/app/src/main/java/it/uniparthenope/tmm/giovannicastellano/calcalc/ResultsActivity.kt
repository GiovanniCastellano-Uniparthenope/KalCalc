package it.uniparthenope.tmm.giovannicastellano.calcalc

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
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

        var Calories = ""; if(static.language == LANGUAGE.ITALIAN) { Calories = "Calorie (KCal)"; } else { Calories = "Calories (KCal)" }
        var Carbohydrates = ""; if(static.language == LANGUAGE.ITALIAN) { Carbohydrates = "Carboidrati (g)"; } else { Carbohydrates = "Carbohydrates (g)"; }
        var Fats = ""; if(static.language == LANGUAGE.ITALIAN) { Fats = "Grassi (g)"; } else { Fats = "Fats (g)"; }
        var Proteins = ""; if(static.language == LANGUAGE.ITALIAN) { Proteins = "Proteine (g)"; } else { Proteins = "Proteins (g)"; }

        var nutritionalValues = ArrayList<PieEntry>()
        nutritionalValues.add(PieEntry(meal.calories, Calories))
        nutritionalValues.add(PieEntry(meal.carbohydrates, Carbohydrates))
        nutritionalValues.add(PieEntry(meal.fats, Fats))
        nutritionalValues.add(PieEntry(meal.proteins, Proteins))

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

        val jsonparser = JSONparser()

        val saveButton = findViewById<Button>(R.id.saveMeal)
        saveButton.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                var title = ""; if(static.language == LANGUAGE.ITALIAN) { title = "Nome del pasto"; } else { title = "Meal name"; }
                var yes = ""; if(static.language == LANGUAGE.ITALIAN) { yes = "Conferma"; } else { yes = "Confirm"; }
                var no = ""; if(static.language == LANGUAGE.ITALIAN) { no = "Annulla"; } else { no = "Cancel"; }
                val editText = EditText(context)
                val alertD = AlertDialog.Builder(context)
                alertD.setTitle(title)
                alertD.setView(editText)
                alertD.setPositiveButton(yes, object : DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        if(editText.text.contains(':') || editText.text.contains('-') || editText.text.contains('_'))
                        {
                            val reject = AlertDialog.Builder(context)
                            var message = ""; if(static.language == LANGUAGE.ITALIAN) { message = "Il nome contiene caratteri invalidi"; } else { message = "Inserted name contains invalid characters"; }
                            reject.setMessage(message)
                            reject.setPositiveButton("Ok", object: DialogInterface.OnClickListener {
                                override fun onClick(rejectD: DialogInterface?, which: Int) {
                                    rejectD?.dismiss()
                                }
                            })
                            val rejectDialog = reject.create()
                            rejectDialog.show()
                        }
                        else
                        {
                            val name = ArrayList<String>()
                            name.add(editText.text.toString())
                            val newFood = Food(name, meal.portion, meal.calories / meal.portion, meal.carbohydrates / meal.portion, meal.fats / meal.portion, meal.proteins / meal.portion, CATEGORY.OTHER)
                            jsonparser.addCustomFood(newFood)
                            val foodFile = openFileOutput("customFoods.json", Context.MODE_PRIVATE ).bufferedWriter().use {
                                it.write(static.customFoodsFile)
                            }
                            print("Read file from cache")
                            dialog?.dismiss()
                        }
                    }
                })
                val alertDialog = alertD.create()
                alertDialog.show()
            }
        })
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
                meal.portion += quantity
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
                    meal.portion += quantity
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
        languageManager.englishTexts.add("SAVE MEAL AS CUSTOM FOOD")
        languageManager.italianTexts.add("SALVA PASTO COME CIBO CUSTOM")

        findViewById<TextView>(R.id.CaloriesTV).text = languageManager.getStringAt(0)
        findViewById<TextView>(R.id.CarbTV).text = languageManager.getStringAt(1)
        findViewById<TextView>(R.id.FatsTV).text = languageManager.getStringAt(2)
        findViewById<TextView>(R.id.ProteinsTV).text = languageManager.getStringAt(3)
        findViewById<Button>(R.id.saveMeal).text = languageManager.getStringAt(4)
    }
}