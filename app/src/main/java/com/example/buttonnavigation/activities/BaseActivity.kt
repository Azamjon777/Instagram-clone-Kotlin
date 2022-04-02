package com.example.buttonnavigation.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.buttonnavigation.R
import kotlinx.android.synthetic.main.bottom_nav_view.*

abstract class BaseActivity(private val navNumber: Int) : AppCompatActivity() {

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    fun setupBottomNav() {
        bottom_navigation_view.setOnNavigationItemSelectedListener {
            val nextActivity =
                when (it.itemId) {
                    R.id.home1 -> {
                        HomeActivity::class.java
                    }
                    R.id.search -> {
                        SearchActivity::class.java
                    }
                    R.id.share -> {
                        ShareActivity::class.java
                    }
                    R.id.likes -> {
                        LikesActivity::class.java
                    }
                    R.id.profile -> {
                        ProfileActivity::class.java
                    }
                    else -> {
                        null
                    }
                }
            if (nextActivity != null) {
                val intent = Intent(this, nextActivity)
                intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                startActivity(intent)
                overridePendingTransition(0, 0)
                true
            } else {
                false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (bottom_navigation_view != null) {
            bottom_navigation_view.menu.getItem(navNumber).isChecked = true
        }
    }
}