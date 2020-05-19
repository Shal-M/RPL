package com.example.petsociety

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class DetailAdopsi : AppCompatActivity() {
    companion object{
        const val EXTRA_JUDUL = "extra_judul"
        const val EXTRA_DESC = "extra_desc"
        const val EXTRA_TELP = "extra_telp"
        const val EXTRA_PHOTO = "extra_photo"
        const val EXTRA_JENKEL = "extra_jenkel"
        const val EXTRA_HEWAN = "extra_hewan"
    }
    private lateinit var pesan: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_adopsi)

        val imgPhoto : ImageView = findViewById(R.id.img_item_adopsi)
        val tvJudul: TextView = findViewById(R.id.et_judul)
        val tvDesk : TextView = findViewById(R.id.item_desk)
        val tvJenkel : TextView = findViewById(R.id.jenis_kelamin_hewan)
        val tvHewan : TextView = findViewById(R.id.jenis_hewan)
        val btn: Button = findViewById(R.id.btn_adopt)

        val judul = intent.getStringExtra(EXTRA_JUDUL)
        val img = intent.getStringExtra(EXTRA_PHOTO)
        val desk = intent.getStringExtra(EXTRA_DESC)
        val hewan = intent.getStringExtra(EXTRA_HEWAN)
        val jenKel = intent.getStringExtra(EXTRA_JENKEL)
        val telp = intent.getStringExtra(EXTRA_TELP)

        tvJudul.text=judul
        tvHewan.text=hewan
        tvJenkel.text=jenKel
        tvDesk.text=desk

        Glide.with(this)
            .load(img)
            .into(imgPhoto)

        btn.setOnClickListener {
            pesan = "Saya berniat Adopsi"
            val intent = Intent(Intent.ACTION_SEND)
            intent.setType("text/plain")
            intent.putExtra(Intent.EXTRA_TEXT, pesan)
            intent.putExtra("jid", "62"+telp+"@s.whatsapp.net")
            intent.setPackage("com.whatsapp")
            startActivity(intent)
        }
    }
}