package com.example.petsociety

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class signupActivity : AppCompatActivity(){
    private lateinit var username: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var loading: ProgressBar
    private lateinit var btnSignUp: Button
    val URL_REGIST = "https://d728b975.ngrok.io/android_register_login/register.php"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        username = findViewById(R.id.edittext_username)
        email = findViewById(R.id.edittext_email)
        password = findViewById(R.id.edittext_password)
        loading = findViewById(R.id.loading)
        btnSignUp = findViewById(R.id.btn_signup)

        btnSignUp.setOnClickListener {
            SignUp()
        }
    }

    private fun SignUp(){
        loading.visibility = View.VISIBLE
        btnSignUp.visibility = View.GONE

        val username = this.username.text.toString().trim()
        val email = this.email.text.toString().trim()
        val password = this.password.text.toString().trim()

        val stringRequest: StringRequest = object : StringRequest(Method.POST, URL_REGIST,
                Response.Listener { response ->
                    try {
                        val jsonObject = JSONObject(response)
                        val success = jsonObject.getString("success")
                        if (success == "1") {
                            Toast.makeText(this, "Register Success!", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        Toast.makeText(this,"Register Error! $e",Toast.LENGTH_SHORT).show()
                        loading.visibility = View.GONE
                        btnSignUp.setVisibility(View.VISIBLE)
                    }
                },
                Response.ErrorListener { error ->
                    Toast.makeText(this,"Register Error! $error",Toast.LENGTH_SHORT).show()
                    loading.visibility = View.GONE
                    btnSignUp.setVisibility(View.VISIBLE)
                }) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    params["name"] = username
                    params["email"] = email
                    params["password"] = password
                    return params
                }
            }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }
}