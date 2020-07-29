package com.example.voci_trainer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import java.io.*


class ActivityHighscoreEntry : AppCompatActivity() {

    private var newHighscore: String = "" //obtained via intent
    private var localCategoryFile: String = "" //obtained via intent
    private lateinit var editVorname: EditText
    private lateinit var editNachname: EditText
    private var myExternalHighscores: File?=null
    private val filepath = "MyFileStorage"
    private var highscoreFile = "highscore.txt"


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
                //load existing highscores from external storage
                myExternalHighscores = File(getExternalFilesDir(filepath),highscoreFile)
                var fileInputStream = FileInputStream(myExternalHighscores)
                var inputStreamReader = InputStreamReader(fileInputStream)
                val bufferedReader = BufferedReader(inputStreamReader)
                val stringBuilder: StringBuilder = StringBuilder()
                var text3: String? = null
                while ({ text3 = bufferedReader.readLine(); text3 }() != null) {
                    stringBuilder.append(text3)
                }
                fileInputStream.close()
                //divide stream into [i=category.txt] [i+1=highscore] [i+2=name]
                val strs = stringBuilder.toString().split(",").toTypedArray()

                //enter new highscore and name for given category
                var name: String = editVorname.text.toString() + " " + editNachname.text.toString()
                if (strs[0] == localCategoryFile.substring(0,8)) { strs[2] = name; strs[1] = newHighscore }
                if (strs[3] == localCategoryFile.substring(0,8)) { strs[5] = name; strs[4] = newHighscore }
                if (strs[6] == localCategoryFile.substring(0,8)) { strs[8] = name; strs[7] = newHighscore }
                if (strs[9] == localCategoryFile.substring(0,8)) { strs[11] = name; strs[10] = newHighscore }

                //transform and write back in external storage
                val stringBuilder2: StringBuilder = StringBuilder()
                for (i in strs) { stringBuilder2.append("$i,") }
                try {
                    val fileOutPutStream = FileOutputStream(myExternalHighscores)
                    fileOutPutStream.write(stringBuilder2.toString().toByteArray())
                    fileOutPutStream.close()
                } catch (e: IOException) { e.printStackTrace() }
                setResult(Activity.RESULT_OK, replyIntent)
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
