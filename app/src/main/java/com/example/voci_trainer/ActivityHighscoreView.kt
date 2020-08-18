package com.example.voci_trainer


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ActivityHighscoreView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.highscore_view)

        val listView = findViewById<ListView>(R.id.main_listview)

        listView.adapter = MyCustomAdapter(this)

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

    private class MyCustomAdapter(context: Context) : BaseAdapter() {

        private val mContext: Context
        private val categories = arrayListOf<String>(
            "Professions", "Food", "Family"
        )

        init {
            mContext = context
        }

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