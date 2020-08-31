package it.uniparthenope.tmm.giovannicastellano.calcalc

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val context = this
        var language = LANGUAGE.ITALIAN
        var allfoods = allFoods.standardFoods
        var customfoods = allFoods.customFoods
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
                    for(food in loadedFoods)
                    {
                        val name = food.getName(language.type)
                        var sublength = 0;
                        if(name.length >= s.length)
                            sublength = s.length
                        else
                            sublength = name.length

                        if(name.substring(0, sublength).toLowerCase() == s.substring(0, s.length).toLowerCase())
                        {
                            matchingFoods.add(food.getName(language.type));
                        }
                    }
                }

                if(matchingFoods.size <= 0)
                    searchList.visibility = View.GONE

                searchAdapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, matchingFoods)
                searchList.adapter = searchAdapter
            }

            override fun beforeTextChanged(s : CharSequence, start : Int, count : Int, after : Int) {
            }

            override fun onTextChanged(s : CharSequence, start : Int, before : Int, count : Int) {

            }
        });


    }

    override fun onBackPressed() {
        //DO NOTHING
    }
}