package com.example.voci_trainer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.new_word.*
import java.io.*

class ActivityNewWord : AppCompatActivity() {

    private lateinit var editGermanWordView: EditText
    private lateinit var editEnglishWordView: EditText
    private val filepath = "MyFileStorage"
    private var myExternalFile: File?=null
    private var localCategoryFile = "" //obtained from intent MainActivity in onCreate
    private val isExternalStorageReadOnly: Boolean get() {
        val extStorageState = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED_READ_ONLY == extStorageState
    }
    private val isExternalStorageAvailable: Boolean get() {
        val extStorageState = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == extStorageState
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_word)
        editGermanWordView = findViewById(R.id.edit_german)
        editEnglishWordView = findViewById(R.id.edit_english)

        //get intent from MainActivity
        localCategoryFile = intent.getStringExtra("category").toString()

        val button = findViewById<Button>(R.id.button_save)
        if (!isExternalStorageAvailable || isExternalStorageReadOnly) {
            button.isEnabled = false
        }
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editGermanWordView.text) || TextUtils.isEmpty(editEnglishWordView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                myExternalFile = File(getExternalFilesDir(filepath), localCategoryFile)

                //load existing external storage (these would be already existing additional words!)
                var fileInputStream = FileInputStream(myExternalFile)
                var inputStreamReader = InputStreamReader(fileInputStream)
                val bufferedReader = BufferedReader(inputStreamReader)
                val stringBuilder: StringBuilder = StringBuilder()
                var text: String? = null
                while ({ text = bufferedReader.readLine(); text }() != null) {
                    stringBuilder.append(text)
                }
                fileInputStream.close()

                //add new word to stringBuilder
                var newString = editGermanWordView.text.toString() + "," + editEnglishWordView.text.toString() + ","
                stringBuilder.append(newString)

                //write new additional vocabulary set to external storage
                try {
                    val fileOutPutStream = FileOutputStream(myExternalFile)
                    fileOutPutStream.write(stringBuilder.toString().toByteArray())
                    fileOutPutStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }

        button_reset.setOnClickListener {
            val replyIntent = Intent()
            myExternalFile = File(getExternalFilesDir(filepath), localCategoryFile)
            var newString = ""
            try {
                val fileOutPutStream = FileOutputStream(myExternalFile)
                fileOutPutStream.write(newString.toByteArray())
                fileOutPutStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            setResult(Activity.RESULT_OK, replyIntent)
            finish()
        }

    }
}