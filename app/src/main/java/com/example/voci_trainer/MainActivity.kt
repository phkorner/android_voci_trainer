package com.example.voci_trainer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    //communication between activities
    private val newWordActivityRequestCode = 1
    private val lernrichtungRequestCode = 1
    private val changeLanguageRequestCode = 1
    private val highscoreRequestCode = 1
    private val highscoreEntryRequestCode = 1

    //game-specific variables
    var englishWords = mutableListOf<String>()
    var germanWords = mutableListOf<String>()
    var solutionMap = mutableMapOf<String, String>()

    // write logic within activity to access assets
    // had this in a separate class.kt -> however needs context, assetManager classes
    fun loadNewGame() {
        var reader = assets.open("category1.txt").bufferedReader()
        reader.forEachLine {
            val strs = it.split(",").toTypedArray()
            germanWords.add(strs[0])
            englishWords.add(strs[1])
            //todo: try/catch für duplikate, kann eine Map nicht halten!
            solutionMap[strs[0]] = strs[1]
        }
        germanWords.shuffle()
        englishWords.shuffle()
    }

    fun loadNewQuestion() {
        germanWords.shuffle()
        englishWords.shuffle()
        findViewById<TextView>(R.id.Frage).text = germanWords[0]
        findViewById<Button>(R.id.Antwort1).text = solutionMap[germanWords[0]]
        findViewById<Button>(R.id.Antwort2).text = englishWords[1]
        findViewById<Button>(R.id.Antwort3).text = englishWords[2]
        findViewById<Button>(R.id.Antwort4).text = englishWords[3]

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        //additional for game startup
        loadNewGame()
        loadNewQuestion()

        val gameAnswer1 = findViewById<Button>(R.id.Antwort1)
        gameAnswer1.setOnClickListener {
            loadNewQuestion()
        }
        val gameAnswer2 = findViewById<Button>(R.id.Antwort2)
        gameAnswer2.setOnClickListener {
            loadNewQuestion()
        }
        val gameAnswer3 = findViewById<Button>(R.id.Antwort3)
        gameAnswer3.setOnClickListener {
            loadNewQuestion()
        }
        val gameAnswer4 = findViewById<Button>(R.id.Antwort4)
        gameAnswer4.setOnClickListener {
            loadNewQuestion()
        }

        //FloatingActionButton für Aufruf newWordActivity
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, ActivityNewWord::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }

        //Button zum Abspeichern des Highscores
        val score = findViewById<Button>(R.id.save_highscore)
        score.setOnClickListener {
       //     val intent = Intent(this@MainActivity, NewWordActivity::class.java)
        //    startActivityForResult(intent, highscoreEntryRequestCode)
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
                val intent = Intent(this@MainActivity, ActivityChangeLanguage::class.java)
                startActivityForResult(intent, changeLanguageRequestCode)
                true
            }
           R.id.Lernrichtung -> {
               val intent = Intent(this@MainActivity, ActivityLearningDirection::class.java)
               startActivityForResult(intent, lernrichtungRequestCode)
               true
           }
           R.id.Highscore -> {
               val intent = Intent(this@MainActivity, ActivityHighscoreView::class.java)
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
            data?.getStringExtra(ActivityNewWord.EXTRA_REPLY)?.let {
                //todo: logik einfügen von wort in txt file
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG).show()
        }

        //learning_direction Result Handler
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
            println("highscore_view")
        } else {
            Toast.makeText(
                applicationContext,
                "no action taken.",
                Toast.LENGTH_LONG).show()
        }

    }
}