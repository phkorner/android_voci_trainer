package com.example.voci_trainer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.change_language.*
import kotlinx.android.synthetic.main.lernrichtung.*

class MainActivity : AppCompatActivity() {

    private val newWordActivityRequestCode = 1
    private val lernrichtungRequestCode = 1
    private val changeLanguageRequestCode = 1
    private val highscoreRequestCode = 1

    private lateinit var wordViewModel: WordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        //additional for Room SQLite Database
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = WordListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        wordViewModel = ViewModelProvider(this).get(WordViewModel::class.java)
        wordViewModel.allWords.observe(this, Observer { words ->
            // Update the cached copy of the words in the adapter.
            words?.let { adapter.setWords(it) }
        })

        //FloatingActionButton für Aufruf newWordActivity
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewWordActivity::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       return when (item.itemId) {
            R.id.Sprache -> {
                val intent = Intent(this@MainActivity, change_language::class.java)
                startActivityForResult(intent, changeLanguageRequestCode)
                true
            }
           R.id.Lernrichtung -> {
               val intent = Intent(this@MainActivity, lernrichtung::class.java)
               startActivityForResult(intent, lernrichtungRequestCode)
               true
           }
           R.id.Highscore -> {
               val intent = Intent(this@MainActivity, highscore::class.java)
               startActivityForResult(intent, highscoreRequestCode)
               true
           }
            else -> super.onOptionsItemSelected(item)
       }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //newWordActivity Result Handler
        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(NewWordActivity.EXTRA_REPLY)?.let {
                val word = Word(it)
                wordViewModel.insert(word)
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG).show()
        }

        //lernrichtung Result Handler
        if (requestCode == lernrichtungRequestCode && resultCode == Activity.RESULT_OK) {
            //TODO action bei klick DE-EN hier
            println("DE-EN")
        }
        if (requestCode == lernrichtungRequestCode && resultCode == Activity.RESULT_OK) {
            //TODO action bei klick EN-DE hier
            println("EN-DE")
        } else {
            Toast.makeText(
                applicationContext,
                "no action taken.",
                Toast.LENGTH_LONG).show()
        }





        //change Language Result Handler
        if (requestCode == changeLanguageRequestCode && resultCode == Activity.RESULT_OK) {
            //TODO action bei klick DEUTSCH
            println("Deutsch")
        }
        if (requestCode == changeLanguageRequestCode && resultCode == Activity.RESULT_OK) {
            //TODO action bei klick DEUTSCH
            println("English")
        }else {
            Toast.makeText(
                applicationContext,
                "no action taken.",
                Toast.LENGTH_LONG).show()
        }






        //Highscore Result Handler
        if (requestCode == highscoreRequestCode && resultCode == Activity.RESULT_OK) {
            //TODO action bei klick Highscore close hier
            println("highscore")
        } else {
            Toast.makeText(
                applicationContext,
                "no action taken.",
                Toast.LENGTH_LONG).show()
        }

    }
}