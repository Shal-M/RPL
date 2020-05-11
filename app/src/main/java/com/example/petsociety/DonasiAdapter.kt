package com.example.petsociety

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class DonasiAdapter(private val context: Context, private val listDonasi:ArrayList<Donasi>): RecyclerView.Adapter<DonasiAdapter.DonasiViewHolder>() {
    inner class DonasiViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var tvJudul: TextView = itemView.findViewById(R.id.tv_item_galdana)
        var tvDeskripsi : TextView = itemView.findViewById(R.id.tv_item_username)
        var img: ImageView = itemView.findViewById(R.id.img_item_galdana)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonasiAdapter.DonasiViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_galdana_view,parent,false)
        return DonasiViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listDonasi.size
    }

    override fun onBindViewHolder(holder: DonasiAdapter.DonasiViewHolder, position: Int) {
        val donasi = listDonasi[position]

        Glide.with(holder.itemView.context)
            .load(donasi.photo)
            .into(holder.img)

        holder.tvJudul.text = donasi.judul
        holder.tvDeskripsi.text = donasi.desc
    }
}