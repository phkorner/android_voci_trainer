package com.example.voci_trainer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.lernrichtung.*


class lernrichtung : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        de_en.setOnClickListener {

            finish()
        }

        en_de.setOnClickListener {


            finish()
        }

    }
}