package com.example.petsociety

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class detailDonasi : AppCompatActivity() {
    companion object{
        const val EXTRA_JUDUL = "extra_judul"
        const val EXTRA_DESC = "extra_desc"
        const val EXTRA_TEMPAT = "extra_tempat"
        const val EXTRA_PHOTO = "extra_photo"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_donasi)

        val imgPhoto: ImageView = findViewById(R.id.img_item_adopsi)
        val tvJudul: TextView = findViewById(R.id.et_judul)

        val judul = intent.getStringExtra(EXTRA_JUDUL)
        val img = intent.getStringExtra(EXTRA_PHOTO)

        tvJudul.text = judul
        Glide.with(this)
            .load(img)
            .into(imgPhoto)
    }
}
