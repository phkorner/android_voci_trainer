package com.example.voci_trainer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ActivityNewWord : AppCompatActivity() {

    private lateinit var editGermanWordView: EditText
    private lateinit var editEnglishWordView: EditText

    /**
    //solution from: https://www.javatpoint.com/kotlin-android-read-and-write-external-storage
    //how to write external storage which IS WRITABLE unlike ASSETS and RESOURCES directories.
    //note to self: APP internal & external storage is invisible in android studio. held by app.
    */
    private val filepath = "MyFileStorage"
    private var myExternalFile: File?=null
    private val isExternalStorageReadOnly: Boolean get() {
        val extStorageState = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)
    }
    private val isExternalStorageAvailable: Boolean get() {
        val extStorageState = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED.equals(extStorageState)
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_word)
        editGermanWordView = findViewById(R.id.edit_german)
        editEnglishWordView = findViewById(R.id.edit_english)

        val button = findViewById<Button>(R.id.button_save)
        if (!isExternalStorageAvailable || isExternalStorageReadOnly) {
            button.isEnabled = false
        }

        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editGermanWordView.text) || TextUtils.isEmpty(editEnglishWordView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val germanWord = editGermanWordView.text.toString()
                val englishWord = editEnglishWordView.text.toString()

                myExternalFile = File(getExternalFilesDir(filepath), "category4.txt")
                try {
                    val fileOutPutStream = FileOutputStream(myExternalFile)
                    fileOutPutStream.write(editGermanWordView.text.toString().toByteArray())
                    fileOutPutStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                Toast.makeText(applicationContext,"data save",Toast.LENGTH_SHORT).show()

                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }


    }
}