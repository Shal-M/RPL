package com.example.petsociety

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_login.setOnClickListener {
            login()

        }


        tv_signup.setOnClickListener {
            Log.d("LoginActivity","Try to Show signup")
            val moveSignUP = Intent(this, singupActivity::class.java)
            startActivity(moveSignUP)
        }
    }
    private fun login(){

        val email = edittext_email.text.toString()
        val password = edittext_password.text.toString()

        if (email.isEmpty() ||  password.isEmpty()){
            Toast.makeText(this,"Please Enter text",Toast.LENGTH_SHORT).show()
            return
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener
                Log.d("Main", "Success: ${it.result?.user?.uid}")
            }
            .addOnFailureListener {
                Log.d("Main","Failed to Login: ${it.message}")
                Toast.makeText(this, "Failed to Login: ${it.message}", Toast.LENGTH_SHORT).show()
            }

    }
}


