package com.example.petsociety

import android.app.DownloadManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
class AdopsiFragment : Fragment() {
    val URL_ADOPT = "https://538b838b.ngrok.io/android_register_login/readAdopt.php"
    private val adoptList = arrayListOf<Adopt>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_adopsi, container, false)
        recyclerView = view.findViewById(R.id.rv_adopt)
        recyclerView.setHasFixedSize(true)
        viewManager = LinearLayoutManager(activity)
        loadAdopt()
        return view
    }

    private fun loadAdopt() {
        val requestQueue = Volley.newRequestQueue(activity)
        val jsonArrayRequest = JsonArrayRequest(GET, URL_ADOPT,null,
            Response.Listener {response ->
                for(i in 0 until response.length()){
                    try {
                        val adoptObject = response.getJSONObject(i)
                        val adopsi = Adopt()
                        adopsi.jhewan = adoptObject.getString("jhewan").toString()
                        adopsi.jkelamin = adoptObject.getString("jkelamin").toString()
                        adopsi.deskripsi = adoptObject.getString("deskripsi").toString()
                        adopsi.img = adoptObject.getString("image_hewan")
                        adoptList.add(adopsi)
                    }catch (e: JSONException){

                    }
                }
                recyclerView.layoutManager = LinearLayoutManager(activity)
                val adapter = activity?.let { AdoptAdapter(it, adoptList) }
                recyclerView.adapter = adapter
            },
            Response.ErrorListener {
                    error -> Log.d("tag", "onErrorResponse: " + error.message)
            })
        requestQueue.add(jsonArrayRequest)
    }

}
