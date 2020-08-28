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
import java.io.*

class ActivityHighscoreView : AppCompatActivity() {

    private val filepath = "MyFileStorage"
    private var highscoreFile = "highscore.txt"
    private var myExternalHighscores: File?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.highscore_view)

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
        val strs = stringBuilder.toString().split(",").toTypedArray()
        strs[0] = resources.getString(R.string.cat1)
        strs[3] = resources.getString(R.string.cat2)
        strs[6] = resources.getString(R.string.cat3)
        strs[9] = resources.getString(R.string.cat4)

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

            //"category1,5,anonym,category2,5,anonym,category3,5,anonym,category4,5,anonym" (default)
            strs[0] = "category1"; strs[1] = "5"; strs[2] ="anonym"
            strs[3] = "category2"; strs[4] = "5"; strs[5] ="anonym"
            strs[6] = "category3"; strs[7] = "5"; strs[8] ="anonym"
            strs[9] = "category4"; strs[10] = "5"; strs[11] ="anonym"

            //transform and write back in external storage
            val stringBuilder2: StringBuilder = StringBuilder()
            for (i in strs) { stringBuilder2.append("$i,") }
            try {
                val fileOutPutStream = FileOutputStream(myExternalHighscores)
                fileOutPutStream.write(stringBuilder2.toString().toByteArray())
                fileOutPutStream.close()
            } catch (e: IOException) { e.printStackTrace() }

            finish()
        }
    }

    private class MyCustomAdapter(context: Context, strs: Array<String>) : BaseAdapter() {

        private val mContext: Context = context
        private val categories = arrayListOf(strs[0], strs[3], strs[6], strs[9])
        private val names = arrayListOf(strs[2], strs[5], strs[8], strs[11])
        private val highscores = arrayListOf(strs[1], strs[4], strs[7], strs[10])

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
            categoryTextView.text = categories[position]

            val nameTextView = rowMain.findViewById<TextView>(R.id.name_Name)
            nameTextView.text = names[position]

            val scoreTextView = rowMain.findViewById<TextView>(R.id.score)
            scoreTextView.text = highscores[position]

            return rowMain
        }
    }
}