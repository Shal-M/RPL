package com.example.petsociety

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_login.setOnClickListener {
            val email = edittext_email.text.toString()
            val password = edittext_password.text.toString()

            Log.d("MainActivity", "Email is" +email)
            Log.d("MainActivity", "Password is" +password)
        }
        tv_signup.setOnClickListener {
            Log.d("MainActivity","Try to Show signup")
            val moveSignUP = Intent(this, singupActivity::class.java)
            startActivity(moveSignUP)
        }
    }
}


