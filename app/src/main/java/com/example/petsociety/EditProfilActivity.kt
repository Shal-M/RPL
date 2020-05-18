package com.example.petsociety

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class EditProfilActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profil)

        val actionbar = supportActionBar
        actionbar!!.title = "Edit Profil"

        actionbar.setDisplayHomeAsUpEnabled(true)


        var arrowBack = findViewById<ImageButton>(R.id.arrow_back)

        arrowBack.setOnClickListener {
            val moveBack = Intent(this, ProfileFragment::class.java)
            startActivity(moveBack)
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
