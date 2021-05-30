package ru.geekbrains.pictureoftheday.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.geekbrains.pictureoftheday.R
import ru.geekbrains.pictureoftheday.fragments.PODFragment


class MainActivity : AppCompatActivity() {

    private val NAME_SHARED_PREFERENCE = "LOGIN"
    private val APP_THEME = "APP_THEME"
    private val MOON_THEME = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        getAppTheme()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, PODFragment.newInstance())
                    .commitNow()
        }
    }

    private fun getAppTheme() {
        val sharedPreferences = getSharedPreferences(NAME_SHARED_PREFERENCE, MODE_PRIVATE)
        if (sharedPreferences != null) {
            val codeStyle = sharedPreferences.getInt(APP_THEME, 0)
            if (codeStyle == MOON_THEME) {
                setTheme(R.style.Theme_PictureOfTheDay_Moon)
            } else {
                setTheme(R.style.Theme_PictureOfTheDay_Grass)
            }
        }
    }
}