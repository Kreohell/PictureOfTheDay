package ru.geekbrains.pictureoftheday.activities

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_splash.*
import ru.geekbrains.pictureoftheday.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        splash_image_view.animate().rotationYBy(300f)
                .setInterpolator(LinearInterpolator())
                .setDuration(3000)
                .setListener(object : Animator.AnimatorListener {

                    override fun onAnimationEnd(animation: Animator?) {
                        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                        finish()
                    }

                    override fun onAnimationStart(animation: Animator?) {}
                    override fun onAnimationCancel(animation: Animator?) {}
                    override fun onAnimationRepeat(animation: Animator?) {}

                })
    }
}