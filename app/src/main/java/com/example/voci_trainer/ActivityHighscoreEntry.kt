package com.example.voci_trainer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class ActivityHighscoreEntry : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.highscore_entry)

        //todo: herausfinden wie man highscoreCounter und Kategorie übergibt!!!
        /*
        Eintrag muss folgende FORM haben (pflicht!)
        String: "category1,5,category2,5,category3,5,category4,5"
         */

        findViewById<Button>(R.id.cancel).setOnClickListener {
            val replyIntent = Intent()
            setResult(Activity.RESULT_OK, replyIntent)
            finish()
        }

        findViewById<Button>(R.id.save_highscore).isEnabled = false // vorübergehend disabled.
    }
}
