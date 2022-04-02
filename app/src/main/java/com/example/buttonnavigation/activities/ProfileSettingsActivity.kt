package com.example.buttonnavigation.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.buttonnavigation.R
import com.example.buttonnavigation.utils.FirebaseHelper
import kotlinx.android.synthetic.main.activity_profile_settings.*

class ProfileSettingsActivity : AppCompatActivity() {

    private lateinit var mFirebase: FirebaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_settings)

        mFirebase = FirebaseHelper(this)
        sign_out_text.setOnClickListener { mFirebase.auth.signOut() }
        back_image.setOnClickListener { finish() }
    }
}