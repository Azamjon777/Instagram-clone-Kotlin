package com.example.buttonnavigation.activities

import android.os.Bundle
import com.example.buttonnavigation.R

class LikesActivity : BaseActivity(3) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupBottomNav()
    }
}