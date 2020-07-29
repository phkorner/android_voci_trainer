package com.example.voci_trainer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText


class ActivityHighscoreEntry : AppCompatActivity() {

    private var newHighscore: String = "" //obtained via intent
    private var localCategoryFile: String = "" //obtained via intent
    private lateinit var editVorname: EditText
    private lateinit var editNachname: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.highscore_entry)

        //get intent highscore and category from MainActivity
        newHighscore = intent.getStringExtra("score").toString()
        localCategoryFile = intent.getStringExtra("category").toString()

        editVorname = findViewById<TextInputEditText>(R.id.VornameInput)
        editNachname = findViewById<TextInputEditText>(R.id.NachnameInput)

        findViewById<Button>(R.id.save_highscore).setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editVorname.text) || TextUtils.isEmpty(editNachname.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {


                setResult(Activity.RESULT_OK, replyIntent)
                /*
        Eintrag muss folgende FORM haben (pflicht!)
        String: "category1,5,category2,5,category3,5,category4,5"
         */
            }
            finish()
        }

        findViewById<Button>(R.id.cancel).setOnClickListener {
            val replyIntent = Intent()
            setResult(Activity.RESULT_CANCELED, replyIntent)
            finish()
        }
    }
}
