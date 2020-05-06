package com.example.petsociety

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*


class PostAdopsiActivity() : AppCompatActivity() {
    var bitmap: Bitmap? = null
    val URL_ADOPSI = "https://c4c0c92d.ngrok.io/android_register_login/uploadAdopt.php"
    private lateinit var imageView: ImageView
    private lateinit var mHewan: EditText
    private lateinit var mKelamin: EditText
    private lateinit var mDeskripsi: EditText
    private lateinit var decoded: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_adopsi)

        imageView = findViewById(R.id.img_post_adopsi)
        var addPhoto = findViewById<TextView>(R.id.tv_change_photo)
        mHewan = findViewById(R.id.et_hewan)
        mKelamin = findViewById(R.id.et_kelamin)
        mDeskripsi = findViewById(R.id.et_deskripsi)
        var post = findViewById<Button>(R.id.btn_post_adopsi)

        addPhoto.setOnClickListener {
            val intent = Intent()
            intent.type = "image/'"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "select Image From Galery"), 1)
        }
        post.setOnClickListener {
            UploadPicture()
        }
    }

    private fun Upload(bmp: Bitmap): Any {
        val baos = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val imageBytes = baos.toByteArray()
        return Base64.encodeToString(imageBytes, Base64.DEFAULT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                imageView.setImageBitmap(bitmap)
                // 512 adalah resolusi tertinggi setelah image di resize, bisa di ganti.
                getResizedBitmap(bitmap,512)?.let { setToImageView(it) }
            } catch (e: IOException){
                e.printStackTrace()
            }
        }
    }

    private fun setToImageView(bmp: Bitmap) {
        //compress image
        val bytes = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.JPEG, 60, bytes)
        decoded = BitmapFactory.decodeStream(ByteArrayInputStream(bytes.toByteArray()))

        //menampilkan gambar yang dipilih dari camera/gallery ke ImageView
        imageView.setImageBitmap(decoded)
    }
    fun getResizedBitmap(image: Bitmap, maxSize: Int): Bitmap? {
        var width = image.width
        var height = image.height
        val bitmapRatio = width.toFloat() / height.toFloat()
        if (bitmapRatio > 1) {
            width = maxSize
            height = (width / bitmapRatio).toInt()
        } else {
            height = maxSize
            width = (height * bitmapRatio).toInt()
        }
        return Bitmap.createScaledBitmap(image, width, height, true)
    }

    private fun UploadPicture() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading....")
        progressDialog.show()

        val GetHewan = mHewan.text.toString().trim()
        val GetKelamin = mKelamin.text.toString().trim()
        val GetDesc = mDeskripsi.text.toString()

        val stringRequest: StringRequest = object : StringRequest(Method.POST, URL_ADOPSI,
            Response.Listener { response ->
                progressDialog.dismiss()
                Log.i(PostAdopsiActivity::class.java.simpleName, response.toString())
                try {
                    val jsonObject = JSONObject(response)
                    val success = jsonObject.getString("success")
                    if(success == "1"){
                        Toast.makeText(this,"Success", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: JSONException) {
                        e.printStackTrace()
                        progressDialog.dismiss()
                        Toast.makeText(this,"Try Again!A "+e, Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { error ->
                progressDialog.dismiss()
                Toast.makeText(this,"Try Again! "+error.toString(), Toast.LENGTH_SHORT).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["jhewan"] = GetHewan
                params["jkelamin"] = GetKelamin
                params["deskripsi"] = GetDesc
                params["image_hewan"] = Upload(decoded) as String
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }
}
