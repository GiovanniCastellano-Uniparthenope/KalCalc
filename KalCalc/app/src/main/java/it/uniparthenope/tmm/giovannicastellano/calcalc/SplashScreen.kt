package it.uniparthenope.tmm.giovannicastellano.calcalc

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import android.content.Context


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
            var totaltime = 0;
            var foodFile = applicationContext.assets.open("foodsAsset.json").bufferedReader().use {
                it.readText()
            }
            var jsonparser = JSONparser()
            totaltime += jsonparser.readFoodsFromFile(foodFile)

            try
            {
                foodFile = openFileInput("customFoods.json").bufferedReader().use {
                    it.readText()
                }
                print("Read file from cache")
            }
            catch(e : Exception)
            {

                foodFile = applicationContext.assets.open("customFoodsAsset.json").bufferedReader().use {
                    it.readText()
                }

                print("Read file from assets")

                openFileOutput("customFoods.json", Context.MODE_PRIVATE).bufferedWriter().use {
                    it.write(foodFile)
                }
            }

            foodFile = applicationContext.assets.open("customFoodsAsset.json").bufferedReader().use {
                it.readText()
            }
            totaltime += jsonparser.readFoodsFromCustomFile(foodFile)

            totaltime = 1000 - totaltime
            if(totaltime < 0)
                totaltime = 0
            Thread.sleep(totaltime.toLong())

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }, 100)
    }

    override fun onBackPressed() {
        //DO NOTHING
    }
}

