package it.uniparthenope.tmm.giovannicastellano.calcalc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import java.util.ArrayList

class ResultsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)
        supportActionBar?.hide()
        val context = this

        val meal = allFoods.meal

        var mealAdapter = ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, meal)
        val mealList = findViewById<ListView>(R.id.meals)
        mealList.adapter = mealAdapter

    }
}