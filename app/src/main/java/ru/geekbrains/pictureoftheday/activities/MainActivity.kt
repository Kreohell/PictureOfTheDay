package ru.geekbrains.pictureoftheday.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.geekbrains.pictureoftheday.R
import ru.geekbrains.pictureoftheday.fragments.PODFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, PODFragment.newInstance())
                    .commitNow()
        }
    }
}