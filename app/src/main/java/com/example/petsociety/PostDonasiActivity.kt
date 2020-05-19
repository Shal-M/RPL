package com.example.petsociety

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Base64.DEFAULT
import android.util.Base64.encodeToString
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_post_donasi.*
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*

class PostDonasiActivity : AppCompatActivity() {
    val URL_DONASI = "https://ab6ff856.ngrok.io/android_register_login/uploadDonasi.php"
    private lateinit var imageView: ImageView
    private lateinit var mJudul: EditText
    private lateinit var mDeskripsi: EditText
    private lateinit var decoded: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_donasi)

        val actionbar = supportActionBar
        actionbar!!.title = "New Post"

        actionbar.setDisplayHomeAsUpEnabled(true)

        imageView = findViewById(R.id.img_post_adopsi)
        mJudul = findViewById(R.id.et_judul)
        mDeskripsi = findViewById(R.id.et_deskripsi)


        tv_change_photo.setOnClickListener {
            val intent = Intent()
            intent.type = "image/'"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Image From Galery"), 1)
        }

        btn_post_adopsi.setOnClickListener {
            UploadPicture()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun UploadPicture() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading.....")
        progressDialog.show()

        val GetJudul = mJudul.text.toString().trim()
        val GetDesc = mDeskripsi.text.toString()

        val stringRequest: StringRequest = object : StringRequest(Method.POST, URL.postDonasi,
            Response.Listener {response ->
                progressDialog.dismiss()
                Log.i(PostDonasiActivity::class.java.simpleName, response.toString())
                try {
                    val jsonObject = JSONObject(response)
                    val success = jsonObject.getString("success")
                    if(success == "1"){
                        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@PostDonasiActivity, HomeFragment::class.java)
                        startActivity(intent)
                    }
                }catch (e: JSONException){
                    e.printStackTrace()
                    progressDialog.dismiss()
                    Toast.makeText(this,"Try Again "+e, Toast.LENGTH_SHORT).show()
                }
        },
            Response.ErrorListener { error ->
                progressDialog.dismiss()
                Toast.makeText(this, "Try Again"+ error.toString(), Toast.LENGTH_SHORT).show()
            }){
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["judul"] = GetJudul
                params["deskripsi"] = GetDesc
                params["photo"] = Upload(decoded) as String
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }

    private fun Upload(bitmap: Bitmap): Any {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val imageByte = baos.toByteArray()
        return Base64.encodeToString(imageByte, Base64.DEFAULT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==1 && resultCode == Activity.RESULT_OK && data != null && data.data != null){
            val filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                imageView.setImageBitmap(bitmap)
                getResizedBitmap(bitmap,512)?.let { setToImageView(it) }
            }catch (e: IOException){

            }
        }
    }

    private fun getResizedBitmap(bitmap: Bitmap, i: Int): Bitmap? {
        var width = bitmap.width
        var heigth = bitmap.height
        val bitmapRatio = width.toFloat()/heigth.toFloat()
        if (bitmapRatio > 1) {
            width = i
            heigth = (width / bitmapRatio).toInt()
        } else {
            heigth = i
            width = (heigth * bitmapRatio).toInt()
        }
        return Bitmap.createScaledBitmap(bitmap, width, heigth, true)
    }

    private fun setToImageView(bitmap: Bitmap) {
        val byte = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, byte)
        decoded = BitmapFactory.decodeStream(ByteArrayInputStream(byte.toByteArray()))
        imageView.setImageBitmap(decoded)
    }
}
