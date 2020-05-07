package com.example.petsociety

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btngalang = view.findViewById<Button>(R.id.btn_galang)
        val btnadopsi = view.findViewById<Button>(R.id.btn_buka_adopsi)

        btngalang.setOnClickListener {
           activity?.let {
               val movebtngalang = Intent(it, PostDonasiActivity::class.java)
               it.startActivity(movebtngalang)
           }

        }

        btnadopsi.setOnClickListener {
            activity?.let {
                val movebtnadopsi = Intent(it, PostAdopsiActivity::class.java)
                it.startActivity(movebtnadopsi)
            }
        }
    }

}
