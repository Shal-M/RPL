package com.example.petsociety

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
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
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*

class PostAdopsiActivity() : AppCompatActivity() {
    var bitmap: Bitmap? = null
    var check: Boolean = true
    val URL_ADOPSI = "https://eeec719e.ngrok.io/android_register_login/uploadAdopt.php"
    private lateinit var imageView: ImageView
    private lateinit var mHewan: EditText
    private lateinit var mKelamin: EditText
    private lateinit var mDeskripsi: EditText

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

    private fun Upload(): String {
        var byteArrayOutputStreamObject: ByteArrayOutputStream
        byteArrayOutputStreamObject = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG,100, byteArrayOutputStreamObject)
        val byteArrayVar = ByteArrayOutputStream().toByteArray()
        val convertImage = Base64.encodeToString(byteArrayVar,Base64.DEFAULT)
        return convertImage
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val filePath = data.data
            try {
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                imageView.setImageBitmap(bitmap)
            } catch (e: IOException){
                e.printStackTrace()
            }
        }
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
                params["image_hewan"] = Upload()
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }
}
