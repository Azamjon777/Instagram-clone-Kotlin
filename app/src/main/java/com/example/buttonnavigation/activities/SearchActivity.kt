package com.example.buttonnavigation.activities

import android.os.Bundle
import com.example.buttonnavigation.R

class SearchActivity : BaseActivity(1) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupBottomNav()
    }
}