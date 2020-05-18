package com.example.petsociety

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Request.Method.GET
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import java.lang.reflect.Method


/**
 * A simple [Fragment] subclass.
 */
class GalangDanaFragment : Fragment() {
    val URL_DONASI = "https://ab6ff856.ngrok.io/android_register_login/readDonasi.php"
    private val donasiList = arrayListOf<Donasi>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var judul: TextView
    private lateinit var deskripsi: TextView
    private lateinit var img: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_galang_dana, container, false)
        recyclerView = view.findViewById(R.id.rv_galdana)
        recyclerView.setHasFixedSize(true)
        viewManager = LinearLayoutManager(activity)
        //judul = view.findViewById(R.id.tv_item_galdana)
        //deskripsi = view.findViewById(R.id.tv_item_username)
        //img = view.findViewById(R.id.img_item_galdana)
        jsonrequest()
        return view
    }

    private fun jsonrequest() {
        val queue = Volley.newRequestQueue(activity)
        val jsonArrayRequest = JsonArrayRequest(GET, URL_DONASI, null,
            Response.Listener { response ->
                for (i in 0 until response.length()) {
                    try {
                        val donasiObject = response.getJSONObject(i)
                        val donasi = Donasi()
                        donasi.judul= donasiObject.getString("judul").toString()
                        donasi.desc = donasiObject.getString("deskripsi").toString()
                        donasi.photo = donasiObject.getString("photo")
                        donasiList.add(donasi)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
                recyclerView.layoutManager = LinearLayoutManager(activity)
                val adapter = activity?.let { DonasiAdapter(it, donasiList) }
                recyclerView.adapter = adapter
            },
            Response.ErrorListener { error -> Log.d("tag", "onErrorResponse: " + error.message) })

        queue.add(jsonArrayRequest)
    }
    private fun setuprecyclerview(donasiList: List<Donasi>) {
        //val myadapter = DonasiAdapter(activity, donasiList)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        //recyclerView.adapter = myadapter
    }
}
