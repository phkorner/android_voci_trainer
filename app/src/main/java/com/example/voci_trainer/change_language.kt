package com.example.voci_trainer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.change_language.*


class change_language : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        deutsch.setOnClickListener {

           finish()
        }

        english.setOnClickListener {


            finish()
        }

    }
}