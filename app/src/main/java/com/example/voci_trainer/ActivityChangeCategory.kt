package com.example.voci_trainer

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ActivityChangeCategory : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_category)

        findViewById<Button>(R.id.category1).setOnClickListener {
            val replyIntent = Intent()
            replyIntent.putExtra("categoryChosen", "category1.txt")
            setResult(Activity.RESULT_OK, replyIntent)
            finish()
        }
        findViewById<Button>(R.id.category2).setOnClickListener {
            val replyIntent = Intent()
            replyIntent.putExtra("categoryChosen", "category2.txt")
            setResult(Activity.RESULT_OK, replyIntent)
            finish()
        }
        findViewById<Button>(R.id.category3).setOnClickListener {
            val replyIntent = Intent()
            replyIntent.putExtra("categoryChosen", "category3.txt")
            setResult(Activity.RESULT_OK, replyIntent)
            finish()
        }
        findViewById<Button>(R.id.category4).setOnClickListener {
            val replyIntent = Intent()
            replyIntent.putExtra("categoryChosen", "category4.txt")
            setResult(Activity.RESULT_OK, replyIntent)
            finish()
        }

    }
}