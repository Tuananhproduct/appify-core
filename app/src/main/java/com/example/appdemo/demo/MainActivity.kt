package com.example.appdemo.demo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appdemo.R
import com.example.appdemo.SelectLanguageView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<SelectLanguageView>(R.id.select_language).apply {
            setToolbarListener(
                clickRight = {
                    Toast.makeText(this@MainActivity, "left", Toast.LENGTH_SHORT).show()
                },
                clickLeft = {
                    Toast.makeText(this@MainActivity, "right", Toast.LENGTH_SHORT).show()
                })

            onChangeLanguageListener {
                Toast.makeText(this@MainActivity, "$it", Toast.LENGTH_SHORT).show()
            }


        }

    }
}