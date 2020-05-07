package com.example.petsociety

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment


/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {
    private lateinit var email: TextView
    private lateinit var username : TextView
    private lateinit var btnLogOut: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_profile, container, false)
        btnLogOut = view.findViewById(R.id.btn_logout)
        username = view.findViewById(R.id.tv_username)
        email = view.findViewById(R.id.tv_email)

        val sharedPreferences = context?.getSharedPreferences("user", Context.MODE_PRIVATE)

        //val name = sharedPreferences?.getString("name","")
        //val email = sharedPreferences?.getString("email", "")

        //val mUsername = arguments?.getString("name").toString()
        //val mEmail = arguments?.getString("email").toString()
        btnLogOut.setOnClickListener {
            //Log.d("username: ", mUsername)
        }
        username.setText(sharedPreferences?.getString("name", ""))
        email.setText(sharedPreferences?.getString("email",""))

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnEditProfil = view.findViewById<Button>(R.id.btn_editProfile)

        btnEditProfil.setOnClickListener {
            activity?.let {
                val moveEditProfil = Intent(it, EditProfilActivity::class.java)
                it.startActivity(moveEditProfil)
            }
        }
    }
}
