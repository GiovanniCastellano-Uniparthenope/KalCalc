package it.uniparthenope.tmm.giovannicastellano.calcalc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val context = this

        setTexts()

        var foodName = findViewById<TextView>(R.id.foodName)
        var quantity = findViewById<EditText>(R.id.quantityText)
        var usePortions = findViewById<Switch>(R.id.usePortions)
        var mealList = findViewById<ListView>(R.id.mealList)
        var meal = ArrayList<String>()
        var mealAdapter = ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, meal)
        mealList.adapter = mealAdapter

        var XBtn = findViewById<Button>(R.id.delete)
        var AddFoodBtn = findViewById<Button>(R.id.addFoodToMeal)
        var ConfirmBtn = findViewById<Button>(R.id.confirmBtn)
        var ResetBtn = findViewById<Button>(R.id.confirmBtn)

        var buttons = ArrayList<Button>()
        buttons.add(XBtn); buttons.add(AddFoodBtn); buttons.add(ConfirmBtn); buttons.add(ResetBtn)

        var allfoods = static.standardFoods
        var customfoods = static.customFoods
        var loadedFoods = ArrayList<Food>()
        loadedFoods.addAll(customfoods)
        loadedFoods.addAll(allfoods)
        var matchingFoods = ArrayList<String>()

        var searchText = findViewById<EditText>(R.id.searchText)
        var searchList = findViewById<ListView>(R.id.searchList)
        var searchAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, matchingFoods)
        searchList.adapter = searchAdapter
        searchList.visibility = View.GONE

        searchText.addTextChangedListener( object : TextWatcher {
            override fun afterTextChanged(s : Editable) {
                matchingFoods = ArrayList<String>()
                if(s.isNotEmpty())
                {
                    searchList.visibility = View.VISIBLE
                    XBtn.visibility = View.GONE
                    AddFoodBtn.visibility = View.GONE
                    for(food in loadedFoods)
                    {
                        val name = food.getName(static.language.type)
                        var sublength = 0;
                        if(name.length >= s.length)
                            sublength = s.length
                        else
                            sublength = name.length

                        if(name.substring(0, sublength).toLowerCase() == s.substring(0, s.length).toLowerCase())
                        {
                            matchingFoods.add(food.getName(static.language.type));
                        }
                    }
                }

                if(matchingFoods.size <= 0) {
                    searchList.visibility = View.GONE
                    XBtn.visibility = View.VISIBLE
                    AddFoodBtn.visibility = View.VISIBLE
                }

                searchAdapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, matchingFoods)
                searchList.adapter = searchAdapter
            }

            override fun beforeTextChanged(s : CharSequence, start : Int, count : Int, after : Int) {
            }

            override fun onTextChanged(s : CharSequence, start : Int, before : Int, count : Int) {

            }
        });
        searchList.setOnItemClickListener( object : AdapterView.OnItemClickListener{
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var string = searchAdapter.getItem(position)
                if(string != null)
                    if(string != "")
                    {
                        foodName.text = string
                        searchText.text.clear()
                        searchList.visibility = View.GONE
                    }
            }
        })
        XBtn.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                foodName.text = ""
            }
        })
        addFoodToMeal.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                if(foodName.text != "" && quantity.text.isNotEmpty())
                {
                    val amount = quantity.text.toString().toInt()
                    if(amount > 0)
                    {
                        var listStr = ""
                        var currFood = static.standardFoods[0]
                        var setted = false
                        for(food in static.standardFoods)
                        {
                            if(food.getName(static.language.type) == foodName.text)
                            { currFood = food; setted = true; break }
                        }

                        if(setted)
                        {
                            listStr += currFood.getName(static.language.type)
                            listStr += ": "
                            if(usePortions.isChecked)
                                listStr += (((currFood.portion * amount).toInt()).toString() + "g")
                            else
                                listStr += (amount.toString() + "g")
                        }
                        if(listStr != "")
                        {
                            meal.add(listStr)
                            mealAdapter = ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, meal)
                            mealList.adapter = mealAdapter
                        }
                    }

                    foodName.text = ""
                    quantity.text.clear()
                    usePortions.isChecked = false
                }
            }
        })
        resetListBtn.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                meal.clear()
                mealAdapter = ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, meal)
                mealList.adapter = mealAdapter
            }
        })
        confirmBtn.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                static.meal = meal
                val intent = Intent(context, ResultsActivity::class.java)
                startActivity(intent)
            }
        })
    }

    fun setTexts()
    {
        val languageManager = LanguageManager()
        languageManager.englishTexts.add("Search foods..."); languageManager.italianTexts.add("Cerca ingredienti...")
        languageManager.englishTexts.add("grams or portions..."); languageManager.italianTexts.add("grammi o porzioni...")
        languageManager.englishTexts.add("Use portions instead of grams:"); languageManager.italianTexts.add("Usa porzioni invece dei grammi:")
        languageManager.englishTexts.add("ADD FOOD TO MEAL"); languageManager.italianTexts.add("AGGIUNGI INGREDIENTE")
        languageManager.englishTexts.add("RESET MEAL"); languageManager.italianTexts.add("RESETTA PASTO")
        languageManager.englishTexts.add("CONFIRM MEAL"); languageManager.italianTexts.add("CONFERMA PASTO")

        findViewById<EditText>(R.id.searchText).hint = languageManager.getStringAt(0);
        findViewById<EditText>(R.id.quantityText).hint = languageManager.getStringAt(1);
        findViewById<TextView>(R.id.ToggleText).text = languageManager.getStringAt(2);
        findViewById<Button>(R.id.addFoodToMeal).text = languageManager.getStringAt(3);
        findViewById<Button>(R.id.resetListBtn).text = languageManager.getStringAt(4);
        findViewById<Button>(R.id.confirmBtn).text = languageManager.getStringAt(5);
    }
}