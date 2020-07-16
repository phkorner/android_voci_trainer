package com.example.voci_trainer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.lernrichtung.*

class lernrichtung : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lernrichtung)

        val button = findViewById<Button>(R.id.de_en)
        button.setOnClickListener {
            val replyIntent = Intent()
            setResult(Activity.RESULT_OK, replyIntent)
            finish()
        }

        val button2 = findViewById<Button>(R.id.en_de)
        button2.setOnClickListener {
            // todo logik für en_de klick
            finish()
        }

    }
}