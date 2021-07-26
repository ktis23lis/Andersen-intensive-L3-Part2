package com.example.loadimage

import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.net.URL
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var pushUrlEditText: EditText
    private lateinit var button: Button
    private var url: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        btnClick(button)
    }

    private fun initView() {
        imageView = findViewById(R.id.imageView)
        pushUrlEditText = findViewById(R.id.push_url_editText)
        button = findViewById(R.id.button)
    }

    private fun btnClick(btn: Button) {
        btn.setOnClickListener {
            url = pushUrlEditText.text.toString()

            if (url.isEmpty()) {
                Toast.makeText(this, getString(R.string.enter_url), Toast.LENGTH_SHORT).show()
            } else {
                loadPage(url, imageView)
            }
        }
    }

    private fun loadPage(page: String, imageView: ImageView) {
        try {
            val url = URL(page)
            val handler = Handler()
            thread {
                try {
                    val inputStream = url.openStream()
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    handler.post {
                        imageView.setImageBitmap(bitmap)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    handler.post {
                        getToast(e)
                    }
                }
            }
        } catch (e: Exception) {
        }
    }

    private fun getToast(e: Exception) {
        Toast.makeText(applicationContext, getString(R.string.error) + " $e", Toast.LENGTH_SHORT)
            .show()
    }
}