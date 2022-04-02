package com.example.buttonnavigation.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.buttonnavigation.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener

class LoginActivity : AppCompatActivity(), KeyboardVisibilityEventListener, View.OnClickListener {
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        KeyboardVisibilityEvent.setEventListener(this, this)
        coordinateBtnAndInputs(login_btn, email_input, password_input)
        login_btn.setOnClickListener(this)
        create_account_text.setOnClickListener(this)

        mAuth = FirebaseAuth.getInstance()
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.login_btn -> {
                val email = email_input.text.toString()
                val password = password_input.text.toString()
                if (validate(email, password)) {
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
                        if (it.isSuccessful) {
                            startActivity(Intent(this, HomeActivity::class.java))
                            finish()
                        }
                    }
                } else {
                    showToast(getString(R.string.please_enter_email_and_password))
                }
            }
            R.id.create_account_text -> {
                startActivity(Intent(this, RegisterActivity::class.java))
            }
        }
    }

    override fun onVisibilityChanged(isKeyboardOpen: Boolean) {
        if (isKeyboardOpen) {
            create_account_text.visibility = View.GONE
        } else {
            create_account_text.visibility = View.VISIBLE
        }
    }

    private fun validate(email: String, password: String) =
        email.isNotEmpty() && password.isNotEmpty()
}