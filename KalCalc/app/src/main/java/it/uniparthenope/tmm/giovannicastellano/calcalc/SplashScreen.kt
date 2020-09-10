package it.uniparthenope.tmm.giovannicastellano.calcalc

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import java.util.*


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class SplashScreen : AppCompatActivity() {

    private var mVisible: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash_screen)

        mVisible = true
        supportActionBar?.hide()
    }

    override fun onStart() {
        super.onStart()

        val handler = Handler()
        handler.postDelayed({
            var foodFile = applicationContext.assets.open("foodsAsset.json").bufferedReader().use {
                it.readText()
            }
            var jsonparser = JSONparser()
            jsonparser.readFoodsFromFile(foodFile)

            try
            {
                foodFile = openFileInput("customFoods.json").bufferedReader().use {
                    it.readText()
                }
                print("Read file from cache\n")
            }
            catch(e : Exception)
            {
                foodFile = applicationContext.assets.open("customFoodsAsset.json").bufferedReader().use {
                    it.readText()
                }
                print("Read file from assets\n")
                openFileOutput("customFoods.json", Context.MODE_PRIVATE).bufferedWriter().use {
                    it.write(foodFile)
                }
            }

            try
            {
                jsonparser.readFoodsFromCustomFile(foodFile)
            }
            catch (e : Exception)
            {
                error("Unable to read foodFile")
                foodFile = applicationContext.assets.open("customFoodsAsset.json").bufferedReader().use {
                    it.readText()
                }
            }

            if(Locale.getDefault().getDisplayLanguage().toLowerCase() == "italiano")
                static.language = LANGUAGE.ITALIAN
            else
                static.language = LANGUAGE.ENGLISH

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }

    override fun onBackPressed() {
        //DO NOTHING
    }
}

