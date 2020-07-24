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
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    //communication between activities -> onActivityResult je Code anderer if-Block!
    private val newWordActivityRequestCode = 1
    private val lernrichtungRequestCode = 2
    private val changeLanguageRequestCode = 3
    private val highscoreRequestCode = 4
    private val highscoreEntryRequestCode = 5

    //game-specific variables
    private var questionWords = mutableListOf<String>()
    private var answerWords = mutableListOf<String>()
    private var solutionMap = mutableMapOf<String, String>()

    //external storage variables
    private val filepath = "MyFileStorage"
    private var myExternalFile: File?=null

    //load game vocabulary from file either in assets or in resources [READ-ONLY AREA]
    private fun loadNewGame() {
        // 2 Options available for readers: ASSETS or RESOURCES (enable only one!)
        var resReader = application.resources.openRawResource(R.raw.category1).bufferedReader()
        // var assetReader = assets.open("category1.txt").bufferedReader()
        resReader.forEachLine {
            val strs = it.split(",").toTypedArray()
            questionWords.add(strs[0])
            answerWords.add(strs[1])
            //todo: try/catch für duplikate, kann eine Map nicht halten!
            solutionMap[strs[0]] = strs[1]
        }
        questionWords.shuffle()
        answerWords.shuffle()
    }

    private fun loadNewQuestion() {
        questionWords.shuffle()
        answerWords.shuffle()
        var list: MutableList<String?> = mutableListOf(solutionMap[questionWords[0]],answerWords[0], answerWords[1], answerWords[2])
        list.shuffle()
        findViewById<TextView>(R.id.Frage).text = questionWords[0]
        findViewById<Button>(R.id.Antwort1).text = list[0]
        findViewById<Button>(R.id.Antwort2).text = list[1]
        findViewById<Button>(R.id.Antwort3).text = list[2]
        findViewById<Button>(R.id.Antwort4).text = list[3]
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

            //EXPERIMENT VON READ EXTERNAL STORAGE:
            //https://www.javatpoint.com/kotlin-android-read-and-write-external-storage
            //funktioniert aber ACHTUNG: Text bleibt erhalten, wird aber in new Word KOMPLETT überschrieben!
            myExternalFile = File(getExternalFilesDir(filepath), "category4.txt")
            val filename = "category4.txt"
            myExternalFile = File(getExternalFilesDir(filepath),filename)
            if(filename.toString()!=null && filename.toString().trim()!=""){
                var fileInputStream = FileInputStream(myExternalFile)
                var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
                val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
                val stringBuilder: StringBuilder = StringBuilder()
                var text: String? = null
                while ({ text = bufferedReader.readLine(); text }() != null) {
                    stringBuilder.append(text)
                }
                fileInputStream.close()
                //Displaying data on EditText
                Toast.makeText(applicationContext,stringBuilder.toString(),Toast.LENGTH_SHORT).show()
            }
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

            //todo: logik einfügen von wort in txt file

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