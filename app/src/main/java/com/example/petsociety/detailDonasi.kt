package com.example.petsociety

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class detailDonasi : AppCompatActivity() {
    companion object{
        const val EXTRA_JUDUL = "extra_judul"
        const val EXTRA_DESC = "extra_desc"
        const val EXTRA_PHOTO = "extra_photo"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_donasi)

        val btnDonasi : Button = findViewById(R.id.btn_galang_di_detail)
        val imgPhoto: ImageView = findViewById(R.id.img_item_adopsi)
        val tvJudul: TextView = findViewById(R.id.et_judul)
        val tvDesk : TextView = findViewById(R.id.item_desk)

        val judul = intent.getStringExtra(EXTRA_JUDUL)
        val img = intent.getStringExtra(EXTRA_PHOTO)
        val desk = intent.getStringExtra(EXTRA_DESC)

        tvJudul.text = judul
        tvDesk.text=desk
        Glide.with(this)
            .load(img)
            .into(imgPhoto)

        btnDonasi.setOnClickListener {
            val moveToPay = Intent(this, PilihNominalDonasiActivity::class.java)
            startActivity(moveToPay)
        }
    }
}
