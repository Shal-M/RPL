package com.example.petsociety

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_signup.edittext_password
import kotlinx.android.synthetic.main.activity_signup.edittext_username

class singupActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        btn_signup.setOnClickListener {
            signUp()
        }
    }

    private fun signUp(){
        val email = edittext_email.text.toString()
        val username = edittext_username.text.toString()
        val password = edittext_password.text.toString()

        if (email.isEmpty() || username.isEmpty() || password.isEmpty()){
            Toast.makeText(this,"Please Enter text",Toast.LENGTH_SHORT).show()
            return
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener{
                if(!it.isSuccessful) return@addOnCompleteListener

                Log.d("Main", "Success: ${it.result?.user?.uid}")
            }
            .addOnFailureListener {
                Log.d("Main","Failed to Creat: ${it.message}")
                Toast.makeText(this, "Failed to Create User: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
}