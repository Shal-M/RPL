package com.example.petsociety

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener
import kotlinx.android.synthetic.main.fragment_home.*


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {
    val sampleimg = intArrayOf(
        R.drawable.tes1,
        R.drawable.tes2,
        R.drawable.tes3
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {
        val carouselView = view?.findViewById<CarouselView>(R.id.carousel_vhome)
        carouselView?.pageCount = sampleimg.size;
        carouselView?.setImageListener(imglistener);
        return inflater.inflate(R.layout.fragment_home, container, false)

    }
    var imglistener: ImageListener = object : ImageListener {
        override fun setImageForPosition(position: Int, imageView: ImageView) {
            imageView.setImageResource(sampleimg[position])
        }
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
