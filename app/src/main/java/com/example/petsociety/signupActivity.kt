package com.example.petsociety

import android.content.Intent
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
import kotlinx.android.synthetic.main.activity_signup.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class signupActivity : AppCompatActivity(){
    private lateinit var username: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var loading: ProgressBar
    private lateinit var btnSignUp: Button
    val URL_REGIST = "https://eeec719e.ngrok.io/android_register_login/register.php"

    // tes comment doang hapus aja nanti
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        username = findViewById(R.id.edittext_username)
        email = findViewById(R.id.edittext_email)
        password = findViewById(R.id.edittext_password)
        loading = findViewById(R.id.loading)
        btnSignUp = findViewById(R.id.btn_signup)

        have_acount.setOnClickListener {
            val moveLogin = Intent(this, MainActivity::class.java)
            startActivity(moveLogin)
        }

        btnSignUp.setOnClickListener {
            SignUp()
        }
    }

    private fun SignUp() {
        loading.visibility = View.VISIBLE
        btnSignUp.visibility = View.GONE

        val mUsername = this.username.text.toString().trim()
        val mEmail = this.email.text.toString().trim()
        val mPassword = this.password.text.toString().trim()

        if (!mEmail.isEmpty() && !mUsername.isEmpty() && !mPassword.isEmpty()) {
            val stringRequest: StringRequest = object : StringRequest(Method.POST, URL.register,
                Response.Listener { response ->
                    try {
                        val jsonObject = JSONObject(response)
                        val success = jsonObject.getString("success")
                        if (success == "1") {
                            Toast.makeText(this, "Register Success!", Toast.LENGTH_SHORT).show()
                            loading.visibility = View.GONE
                            val intent = Intent(this@signupActivity, MainActivity::class.java)
                            startActivity(intent)
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        Toast.makeText(this, "Register Error! $e", Toast.LENGTH_SHORT).show()
                        loading.visibility = View.GONE
                        btnSignUp.setVisibility(View.VISIBLE)
                    }
                },
                Response.ErrorListener { error ->
                    Toast.makeText(this, "Register Error! $error", Toast.LENGTH_SHORT).show()
                    loading.visibility = View.GONE
                    btnSignUp.setVisibility(View.VISIBLE)
                }) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    params["username"] = mUsername
                    params["email"] = mEmail
                    params["password"] = mPassword
                    return params
                }
            }
            val requestQueue = Volley.newRequestQueue(this)
            requestQueue.add(stringRequest)
        } else {
            if(mEmail.isEmpty()) email.error = "Please Insert Email"
            if(mUsername.isEmpty()) username.error = "Please Insert Error"
            if (mPassword.isEmpty())password.error = "Please Insert Password"
            loading.visibility = View.GONE
            btnSignUp.visibility = View.VISIBLE
        }
    }
}