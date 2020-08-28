package com.example.voci_trainer


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.TypedArray
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader

class ActivityHighscoreView : AppCompatActivity() {

    private val filepath = "MyFileStorage"
    private var highscoreFile = "highscore.txt"
    private var myExternalHighscores: File?=null

    private var categoryName1 = "default1" //resources.getString(R.string.cat1)
    private var categoryName2 = "default2"
    private var categoryName3 = "default3"
    private var categoryName4 = "default4"
    private var highscore1 = "5"
    private var highscore2 = "5"
    private var highscore3 = "5"
    private var highscore4 = "5"
    private var userName1 = "name"
    private var userName2 = "name"
    private var userName3 = "name"
    private var userName4 = "name"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.highscore_view)

        //work in progress area <-- start
        //load highscores from internal storage
        myExternalHighscores = File(getExternalFilesDir(filepath),highscoreFile)
        var fileInputStream = FileInputStream(myExternalHighscores)
        var inputStreamReader = InputStreamReader(fileInputStream)
        val bufferedReader = BufferedReader(inputStreamReader)
        val stringBuilder: StringBuilder = StringBuilder()
        var text1: String? = null
        while ({ text1 = bufferedReader.readLine(); text1 }() != null) {
            stringBuilder.append(text1)
        }
        fileInputStream.close()
        //divide stream into [i=category.txt] [i+1=highscore] [i+2=name]
        //"category1,5,name,category2,5,name,category3,5,name,category4,5,name" (default)
        val strs = stringBuilder.toString().split(",").toTypedArray()
        strs[0] = resources.getString(R.string.cat1)
        strs[3] = resources.getString(R.string.cat2)
        strs[6] = resources.getString(R.string.cat3)
        strs[9] = resources.getString(R.string.cat4)
        highscore1 = strs[1]; highscore2 = strs[4]; highscore3 = strs[7]; highscore4 = strs[10]
        userName1 = strs[2]; userName2 = strs[5]; userName3 = strs[8]; userName4 = strs[11];
        //work in progress area <-- end

        val listView = findViewById<ListView>(R.id.main_listview)
        listView.adapter = MyCustomAdapter(this, strs)


        val button = findViewById<Button>(R.id.highscore_close)
        button.setOnClickListener {
            val replyIntent = Intent()
            setResult(Activity.RESULT_OK, replyIntent)
            finish()
        }

        val button2 = findViewById<Button>(R.id.reset_highscore)
        button2.setOnClickListener {
            val replyIntent = Intent()
            setResult(Activity.RESULT_OK, replyIntent)
            finish()
        }
    }

    private class MyCustomAdapter(context: Context, strs: Array<String>) : BaseAdapter() {

        private val mContext: Context = context
        private val categories = arrayListOf<String>(
            strs[0], strs[3], strs[6], strs[9]
        )

        override fun getCount(): Int {
            return categories.size
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getItem(position: Int): Any {
            return "Test"
        }

        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(mContext)
            val rowMain = layoutInflater.inflate(R.layout.row_main, viewGroup, false)

            val categoryTextView = rowMain.findViewById<TextView>(R.id.name_Category)
            categoryTextView.text = categories.get(position)

            val nameTextView = rowMain.findViewById<TextView>(R.id.name_Name)
            nameTextView.text = "Name"

            val scoreTextView = rowMain.findViewById<TextView>(R.id.score)
            scoreTextView.text = "number of points"

            return rowMain
        }
    }
}