package com.example.petsociety

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PilihNominalDonasiActivity:AppCompatActivity() {
    private lateinit var nom10 : LinearLayout
    private lateinit var nom20 : LinearLayout
    private lateinit var nom50 : LinearLayout
    private lateinit var nom100 : LinearLayout
    private lateinit var btnbayar : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pilih_nominal_donasi)

        val actionbar = supportActionBar
        actionbar!!.title = "Pilih Nominal"

        actionbar.setDisplayHomeAsUpEnabled(true)

        btnbayar = findViewById(R.id.btn_bayar)
        nom10 = findViewById(R.id.nom_10k)
        nom20 = findViewById(R.id.nom_20k)
        nom50 = findViewById(R.id.nom_50k)
        nom100 = findViewById(R.id.nom_100k)

        btnbayar.setOnClickListener {
            Toast.makeText(this, "Donasi sukses", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}