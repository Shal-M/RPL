package com.example.petsociety

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var signUp: TextView
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var loading: ProgressBar
    val URL_Login = "https://ab6ff856.ngrok.io/rpl/login.php"

    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        email = findViewById(R.id.edittext_email)
        password = findViewById(R.id.edittext_password)
        btnLogin = findViewById(R.id.btn_login)
        signUp = findViewById(R.id.tv_signup)
        loading = findViewById(R.id.loading)

        signUp.setOnClickListener {
            val moveSignUp = Intent(this, signupActivity::class.java)
            startActivity(moveSignUp)
        }

        btnLogin.setOnClickListener {
            val mEmail = this.email.text.toString().trim()
            val mPass = this.password.text.toString().trim()
            //val MoveLogin = Intent(this@MainActivity, ControllerFragment::class.java)
            //startActivity(MoveLogin)

            if (!mEmail.isEmpty() && !mPass.isEmpty()) {
                login(mEmail, mPass)
            } else {
                if(mEmail.isEmpty()) email.error = "Please Insert Email"
                if(mPass.isEmpty()) password.error = "Please Insert Password"
            }
        }
    }

    private fun login(mEmail: String, mPass: String) {
        loading.visibility = View.VISIBLE
        btnLogin.visibility = View.GONE

        val stringRequest: StringRequest = object : StringRequest(Method.POST, URL.login,
            Response.Listener { response ->
                try {
                    val jsonObject = JSONObject(response)
                    val success = jsonObject.getString("success")
                    val jsonArray = jsonObject.getJSONArray("login")
                    if (success == "1") {
                        for (i in 0 until jsonArray.length()) {
                            val `object` = jsonArray.getJSONObject(i)
                            //val user: JSONObject = jsonObject.getJSONObject("user")
                            val userPref = getSharedPreferences("user", Context.MODE_PRIVATE)
                            val editor = userPref.edit()
                            val name = `object`.getString("username").trim()
                            val email = `object`.getString("email").trim()
                            editor.putString("username", name)
                            editor.putString("email", email)
                            editor.apply()
                            val MoveLogin = Intent(this@MainActivity, ControllerFragment::class.java)
                            startActivity(MoveLogin)
                            Toast.makeText(this, "Success Login. \nYour Name : $name\nYour Email : $email", Toast.LENGTH_SHORT).show()
                            loading.visibility = View.GONE
                        }
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    loading.visibility = View.GONE
                    btnLogin.visibility = View.VISIBLE
                    Toast.makeText(this, "Error!A $e", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { error ->
                loading.visibility = View.GONE
                btnLogin.visibility = View.VISIBLE
                Toast.makeText(this, "Error! $error", Toast.LENGTH_SHORT).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["email"] = mEmail
                params["password"] = mPass
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }
}