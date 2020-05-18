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
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.analytics.FirebaseAnalytics.Event.LOGIN
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.lang.reflect.Method

/**
 * A simple [Fragment] subclass.
 */
class AdopsiFragment : Fragment() {
    val URL_ADOPT = "https://ab6ff856.ngrok.io/android_register_login/readAdopt.php"
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
        val stringRequest: StringRequest = object : StringRequest(Method.POST, URL.readAdopt,
            Response.Listener {response ->
                try {
                    val jsonArray = JSONArray(response)
                    for(i in 0 until jsonArray.length()){
                        val adopt = jsonArray.getJSONObject(i)
                        adoptList.add(
                            Adopt(
                            adopt.getString("tipe_hewan"),
                                adopt.getString("jenis_kelamin"),
                                adopt.getString("deskripsi_hewan"),
                                adopt.getString("image_hewan"),
                                adopt.getString("nama_hewan"),
                                adopt.getString("telp")
                        ))
                    }
                    recyclerView.layoutManager = LinearLayoutManager(activity)
                    val adapter = activity?.let { AdoptAdapter(it, adoptList) }
                    recyclerView.adapter = adapter
                }catch (e: JSONException){
                    e.printStackTrace()
                }
            },
            Response.ErrorListener {
                    error -> Log.d("tag", "onErrorResponse: " + error.message)
            }){

        }
        Volley.newRequestQueue(activity).add(stringRequest)
    }

}
