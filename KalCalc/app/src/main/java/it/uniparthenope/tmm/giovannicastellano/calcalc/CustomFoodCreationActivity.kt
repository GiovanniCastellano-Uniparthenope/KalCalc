package it.uniparthenope.tmm.giovannicastellano.calcalc

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class CustomFoodCreationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_food_creation)
        supportActionBar?.hide()
        setTexts()
        val context = this;


        val nameEdit = findViewById<EditText>(R.id.NameEdit)
        val caloriesEdit = findViewById<EditText>(R.id.CaloriesEdit)
        val carboEdit = findViewById<EditText>(R.id.CarboEdit)
        val fatsEdit = findViewById<EditText>(R.id.FatsEdit)
        val proteinsEdit = findViewById<EditText>(R.id.ProteinsEdit)
        val createIngredientBtn = findViewById<Button>(R.id.CreateFoodBtn)

        createIngredientBtn.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                var message = ""; if(static.language == LANGUAGE.ITALIAN) { message = "Confermare crezione ingrediente?"; } else { message = "Confirm ingredient creation?"; }
                var yes = ""; if(static.language == LANGUAGE.ITALIAN) { yes = "SI"; } else { yes = "YES"; }
                var no = ""; if(static.language == LANGUAGE.ITALIAN) { no = "NO"; } else { no = "NO" }
                val alertD = AlertDialog.Builder(context)
                alertD.setMessage(message);
                alertD.setCancelable(false)
                alertD.setPositiveButton(yes, object : DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        if(nameEdit.text.isNotEmpty() && caloriesEdit.text.isNotEmpty() && carboEdit.text.isNotEmpty() && fatsEdit.text.isNotEmpty() && proteinsEdit.text.isNotEmpty())
                        {
                            if(nameEdit.text.contains(':') || nameEdit.text.contains('-') || nameEdit.text.contains('_'))
                            {
                                val rejectD1 = AlertDialog.Builder(context)
                                var msg = ""; if(static.language == LANGUAGE.ITALIAN) { msg = "Il nome non può contenere i caratteri \':\', \'-\' o \'_\'"; } else { msg = "The name cannot contain \':\', \'-\' or \'_\'"; }
                                rejectD1.setMessage(msg);
                                rejectD1.setPositiveButton("OK", object : DialogInterface.OnClickListener{
                                    override fun onClick(d1: DialogInterface?, which: Int) {
                                        d1?.dismiss()
                                    }
                                })
                                rejectD1.create().show()
                            }
                            else
                            {
                                val name = ArrayList<String>()
                                name.add(nameEdit.text.toString())
                                val portion = 100f
                                val calories = caloriesEdit.text.toString().toFloat()
                                val carbos = carboEdit.text.toString().toFloat()
                                val fats = fatsEdit.text.toString().toFloat()
                                val proteins = proteinsEdit.text.toString().toFloat()
                                val newFood = Food(name, portion, calories, carbos, fats, proteins, CATEGORY.OTHER)
                                val jsonparser = JSONparser()
                                jsonparser.addCustomFood(newFood)
                                val foodFile = openFileOutput("customFoods.json", Context.MODE_PRIVATE ).bufferedWriter().use {
                                    it.write(static.customFoodsFile)
                                }
                                
                                nameEdit.text.clear()
                                caloriesEdit.text.clear()
                                carboEdit.text.clear()
                                fatsEdit.text.clear()
                                proteinsEdit.text.clear()
                                dialog?.dismiss()
                            }
                        }
                        else
                        {
                            val rejectD2 = AlertDialog.Builder(context)
                            var msg = ""; if(static.language == LANGUAGE.ITALIAN) { msg = "Tutti i campi devono avere un valore: salvataggio annullato"; } else { msg = "All fields must have a value: save canceled"; }
                            rejectD2.setMessage(msg);
                            rejectD2.setPositiveButton("OK", object : DialogInterface.OnClickListener{
                                override fun onClick(d2: DialogInterface?, which: Int) {
                                    d2?.dismiss()
                                }
                            })
                            rejectD2.create().show()
                        }
                    }
                })
                alertD.setNegativeButton(no, object : DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        dialog?.dismiss()
                    }
                })
                alertD.create().show()
            }
        })
    }

    fun setTexts()
    {
        val languageManager = LanguageManager()
        languageManager.englishTexts.add("Here you can add a new custom ingredient or composition. You need to insert the nutritional values per single gram, be careful NOT inserting values per portion.")
        languageManager.italianTexts.add("Qui è possibile inserire un nuovo ingrediente custom, o una composizione di ingredienti. Attenzione: è necessario inserire i valori nutrizionali per il singolo grammo, NON i valori per porzione.")
        languageManager.englishTexts.add("Name"); languageManager.italianTexts.add("Nome")
        languageManager.englishTexts.add("Calories"); languageManager.italianTexts.add("Calorie")
        languageManager.englishTexts.add("Carbohydrates"); languageManager.italianTexts.add("Carboidrati")
        languageManager.englishTexts.add("Fats"); languageManager.italianTexts.add("Grassi")
        languageManager.englishTexts.add("Proteins"); languageManager.italianTexts.add("Proteine")
        languageManager.englishTexts.add("CREATE INGREDIENT"); languageManager.italianTexts.add("CREA INGREDIENTE")

        findViewById<TextView>(R.id.description).text = languageManager.getStringAt(0)
        findViewById<TextView>(R.id.NameText).text = languageManager.getStringAt(1)
        findViewById<EditText>(R.id.NameEdit).hint = languageManager.getStringAt(1)
        findViewById<TextView>(R.id.CaloriesText).text = languageManager.getStringAt(2)
        findViewById<EditText>(R.id.CaloriesEdit).hint = languageManager.getStringAt(2) + "/g"
        findViewById<TextView>(R.id.CarboText).text = languageManager.getStringAt(3)
        findViewById<EditText>(R.id.CarboEdit).hint = languageManager.getStringAt(3) + "/g"
        findViewById<TextView>(R.id.FatsText).text = languageManager.getStringAt(4)
        findViewById<EditText>(R.id.FatsEdit).hint = languageManager.getStringAt(4) + "/g"
        findViewById<TextView>(R.id.ProteinsText).text = languageManager.getStringAt(5)
        findViewById<EditText>(R.id.ProteinsEdit).hint = languageManager.getStringAt(5) + "/g"
        findViewById<Button>(R.id.CreateFoodBtn).text = languageManager.getStringAt(6)
    }
}