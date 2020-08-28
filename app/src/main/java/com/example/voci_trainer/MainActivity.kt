package com.example.voci_trainer

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.*
import java.util.*

class MainActivity : AppCompatActivity() {


    //communication between activities -> onActivityResult je Code anderer if-Block!
    private val newWordActivityRequestCode = 1
    private val lernrichtungRequestCode = 2
    private val changeLanguageRequestCode = 3
    private val highscoreRequestCode = 4
    private val highscoreEntryRequestCode = 5
    private val changeCategoryCode = 6
    private val resetHighscoreRequestCode = 7

    //game-specific variables
    private var questionWords = mutableListOf<String>()
    private var answerWords = mutableListOf<String>()
    private var solutionMap = mutableMapOf<String, String>()
    private var highscoreCounter = 0
    private var existingHighscore: Int = 5 //minimum threshold for a highscore

    //external storage variables
    private val filepath = "MyFileStorage"
    private var myExternalFile: File?=null
    private var categoryFile = "category1.txt" //external storage file, set default here
    private var categoryRaw = R.raw.category1 //raw resource file, set default here
    private var myExternalHighscores: File?=null
    private var highscoreFile = "highscore.txt"

    /**
     * basic concept: ASSETS and RESOURCES Directories of the app are READ-ONLY
     * 1) we load pre-installed vocabulary from raw resources
     * 2) external storage then IS WRITABLE. we load additional words from there.
     *
     * how to handle external storage files:
     * //https://www.javatpoint.com/kotlin-android-read-and-write-external-storage
     */
    private fun loadNewGame() {

        //STEP ONE
        //load standard vocabulary for given category from raw resources
        var resReader = application.resources.openRawResource(categoryRaw).bufferedReader()
        val stringBuilder: StringBuilder = StringBuilder()
        var text: String? = null
        while ({ text = resReader.readLine(); text }() != null) {
            stringBuilder.append(text)
        }

        //STEP TWO
        //load additional vocabulary from external storage and append it
        myExternalFile = File(getExternalFilesDir(filepath), categoryFile)
        if (!File(getExternalFilesDir(filepath), categoryFile).exists()) {
            var newFileString = ""
            val fileOutPutStream = FileOutputStream(myExternalFile)
            fileOutPutStream.write(newFileString.toByteArray())
            fileOutPutStream.close()
        }
        var fileInputStream = FileInputStream(myExternalFile)
        var inputStreamReader = InputStreamReader(fileInputStream)
        val bufferedReader = BufferedReader(inputStreamReader)
        var text2: String? = null
        while ({ text2 = bufferedReader.readLine(); text2 }() != null) {
            stringBuilder.append(text2)
        }
        fileInputStream.close()

        //STEP THREE
        //load complete vocabulary on game variables [questionWords] [answerWords] [solutionMap]
        questionWords.clear()
        answerWords.clear()
        solutionMap.clear()
        val strs = stringBuilder.toString().split(",").toTypedArray()
        var i = 0
        while (i < (strs.size - 1) ) {
            questionWords.add(strs[i])
            answerWords.add(strs[i+1])
            solutionMap[strs[i]] = strs[i+1]
            i += 2
        }

        //STEP FOUR
        //load new highscore counter for current session and category
        myExternalHighscores = File(getExternalFilesDir(filepath),highscoreFile)
        if (!File(getExternalFilesDir(filepath), highscoreFile).exists()) {
            var newFileString = "category1,5,anonym,category2,5,anonym,category3,5,anonym,category4,5,anonym"
            val fileOutPutStream = FileOutputStream(myExternalHighscores)
            fileOutPutStream.write(newFileString.toByteArray())
            fileOutPutStream.close()
        }
        var fileInputStream2 = FileInputStream(myExternalHighscores)
        var inputStreamReader2 = InputStreamReader(fileInputStream2)
        val bufferedReader2 = BufferedReader(inputStreamReader2)
        val stringBuilder2: StringBuilder = StringBuilder()
        var text3: String? = null
        while ({ text3 = bufferedReader2.readLine(); text3 }() != null) {
            stringBuilder2.append(text3)
        }
        fileInputStream2.close()
        var strs2 = stringBuilder2.toString().split(",").toTypedArray()
        if (strs2[0] == categoryFile.substring(0,9)) { existingHighscore = strs2[1].toInt() }
        if (strs2[3] == categoryFile.substring(0,9)) { existingHighscore = strs2[4].toInt() }
        if (strs2[6] == categoryFile.substring(0,9)) { existingHighscore = strs2[7].toInt() }
        if (strs2[9] == categoryFile.substring(0,9)) { existingHighscore = strs2[10].toInt() }
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

    private fun validateAnswer(answer: CharSequence) {
        if (solutionMap[questionWords[0]] == answer) { highscoreCounter++ } else { highscoreCounter = 0 }
        var highscoreCounterString = "in a row: $highscoreCounter"
        findViewById<TextView>(R.id.highscore_counter).text = highscoreCounterString
        findViewById<Button>(R.id.save_highscore).isEnabled = highscoreCounter > existingHighscore
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadLocate()
        setContentView(R.layout.activity_main)


        setSupportActionBar(findViewById(R.id.toolbar))
        loadNewGame()
        loadNewQuestion()

        findViewById<Button>(R.id.Antwort1).setOnClickListener {
            var answer = findViewById<Button>(R.id.Antwort1).text
            validateAnswer(answer)
            loadNewQuestion()
        }
        findViewById<Button>(R.id.Antwort2).setOnClickListener {
            var answer = findViewById<Button>(R.id.Antwort2).text
            validateAnswer(answer)
            loadNewQuestion()
        }
        findViewById<Button>(R.id.Antwort3).setOnClickListener {
            var answer = findViewById<Button>(R.id.Antwort3).text
            validateAnswer(answer)
            loadNewQuestion()
        }
        findViewById<Button>(R.id.Antwort4).setOnClickListener {
            var answer = findViewById<Button>(R.id.Antwort4).text
            validateAnswer(answer)
            loadNewQuestion()
        }

        //FloatingActionButton für Aufruf newWordActivity
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, ActivityNewWord::class.java)
            intent.putExtra("category",categoryFile)
            startActivityForResult(intent, newWordActivityRequestCode)
        }

        //Button zum Abspeichern des Highscores
        val score = findViewById<Button>(R.id.save_highscore)
        score.setOnClickListener {
            val intent = Intent(this@MainActivity, ActivityHighscoreEntry::class.java)
            intent.putExtra("category",categoryFile)
            intent.putExtra("score",highscoreCounter.toString())
            startActivityForResult(intent, highscoreEntryRequestCode)
        }
        findViewById<Button>(R.id.save_highscore).isEnabled = false
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       return when (item.itemId) {
            R.id.Sprache -> {
                showChangeLang()
                true
            }
           R.id.Lernrichtung -> {
               val intent = Intent(this@MainActivity, ActivityLearningDirection::class.java)
               startActivityForResult(intent, lernrichtungRequestCode)
               true
           }
           R.id.Kategorie -> {
               val intent = Intent(this@MainActivity, ActivityChangeCategory::class.java)
               startActivityForResult(intent, changeCategoryCode)
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

        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            loadNewGame()
            loadNewQuestion()
        }
        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(applicationContext, R.string.empty_not_saved, Toast.LENGTH_LONG).show()
        }

        if (requestCode == lernrichtungRequestCode && resultCode == Activity.RESULT_OK) {
            println("DE-EN")
        }
        if (requestCode == lernrichtungRequestCode && resultCode == Activity.RESULT_OK) {
            println("EN-DE") // zweimal der gleiche resultCode funktioniert IMMER BEIDES.
        }

        if (requestCode == resetHighscoreRequestCode && resultCode == Activity.RESULT_OK) {
            loadNewGame()
            loadNewQuestion()
        }

        if (requestCode == highscoreRequestCode && resultCode == Activity.RESULT_OK) {
            println("highscore_view")
        }
        if (requestCode == highscoreEntryRequestCode && resultCode == Activity.RESULT_OK) {
            Toast.makeText(applicationContext, "Highscore saved", Toast.LENGTH_LONG).show()
            loadNewGame()
            loadNewQuestion()
            highscoreCounter = 0
            var highscoreCounterString = "in a row: $highscoreCounter"
            findViewById<TextView>(R.id.highscore_counter).text = highscoreCounterString
            findViewById<Button>(R.id.save_highscore).isEnabled = highscoreCounter > existingHighscore
        }
        if (requestCode == highscoreEntryRequestCode && resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(applicationContext, "not saved", Toast.LENGTH_LONG).show()
        }

        if (requestCode == changeCategoryCode && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                categoryFile = data.getStringExtra("categoryChosen").toString()
                if (categoryFile == "category1.txt") { categoryRaw = R.raw.category1 }
                if (categoryFile == "category2.txt") { categoryRaw = R.raw.category2 }
                if (categoryFile == "category3.txt") { categoryRaw = R.raw.category3 }
                if (categoryFile == "category4.txt") { categoryRaw = R.raw.category4 }
            }
            loadNewGame()
            loadNewQuestion()
        }

        if(requestCode == changeLanguageRequestCode && resultCode == Activity.RESULT_OK) {

        }
    }

    private fun showChangeLang() {

        val listItems = arrayOf("English", "Deutsch")

        val mBuilder = AlertDialog.Builder(this@MainActivity)
        mBuilder.setTitle("Choose Language")  //TODO: Titel automatisch in anderer Sprache anzeigen
        mBuilder.setSingleChoiceItems(listItems, -1) {dialog, which ->
            if (which == 0) {
                setLocate("en")
                recreate()
            } else if (which == 1) {
                setLocate ("de")
                recreate()
            }
            dialog.dismiss()
        }
        val mDialog = mBuilder.create()

        mDialog.show()
    }

    private fun setLocate(Lang: String) {
        val locale = Locale(Lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        val editor = getSharedPreferences("Settings", Activity.MODE_PRIVATE).edit()
        editor.putString("My_Lang", Lang)
        editor.apply()
    }

    private fun loadLocate() {
        val sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString("My_Lang", "")
        setLocate(language!!)
    }

}