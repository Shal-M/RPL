package com.example.petsociety

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.view.menu.ActionMenuItemView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class AdoptAdapter(private val context: Context, private val listAdopt: ArrayList<Adopt>): RecyclerView.Adapter<AdoptAdapter.AdoptViewHolder>() {
    inner class AdoptViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var tvHewan = itemView.findViewById<TextView>(R.id.tv_item_adopsi)
        var tvKel = itemView.findViewById<TextView>(R.id.tv_item_username)
        var tvDesc = itemView.findViewById<TextView>(R.id.tv_desk_item)
        var img = itemView.findViewById<ImageView>(R.id.img_item_adopsi)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdoptAdapter.AdoptViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_adopsi_view,parent,false)
        return AdoptViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listAdopt.size
    }

    override fun onBindViewHolder(holder: AdoptAdapter.AdoptViewHolder, position: Int) {
        val adopt = listAdopt[position]

        Glide.with(holder.itemView.context)
            .load(adopt.img)
            .into(holder.img)

        holder.tvHewan.text = adopt.jhewan
        holder.tvKel.text = adopt.jkelamin
        holder.tvDesc.text = adopt.deskripsi

    }
}