package com.example.petsociety

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
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
    private lateinit var btnLogin: Button

    val URL_Login = "https://d728b975.ngrok.io/android_register_login/login.php"

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

            if (!mEmail.isEmpty() || !mPass.isEmpty()) {
                login(mEmail, mPass)
            } else {
                email.error = "Please Insert Email"
                password.error = "Please Insert Password"
            }
        }
    }

    private fun login(mEmail: String, mPass: String) {
        loading.visibility = View.VISIBLE
        btnLogin.visibility = View.GONE

        val stringRequest: StringRequest = object : StringRequest(Method.POST, URL_Login,
                Response.Listener { response ->
                    try {
                        val jsonObject = JSONObject(response)
                        val success = jsonObject.getString("success")
                        val jsonArray = jsonObject.getJSONArray("login")
                        if (success == "1") {
                            for (i in 0 until jsonArray.length()) {
                                val `object` = jsonArray.getJSONObject(i)
                                val name = `object`.getString("name").trim()
                                val email = `object`.getString("email").trim()
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


