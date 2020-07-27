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
import java.io.*

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
    private var categoryFile = "category4.txt"

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
        var resReader = application.resources.openRawResource(R.raw.category4).bufferedReader()
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
        val strs = stringBuilder.toString().split(",").toTypedArray()
        var i = 0
        while (i < (strs.size - 1) ) {
            questionWords.add(strs[i])
            answerWords.add(strs[i+1])
            solutionMap[strs[i]] = strs[i+1]
            i += 2
        }
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
            loadNewGame()
            loadNewQuestion()
            //Toast.makeText(applicationContext,R.string.word_added,Toast.LENGTH_SHORT).show()
        }
        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(applicationContext, R.string.empty_not_saved, Toast.LENGTH_LONG).show()
        }

        //learning_direction Result Handler todo: Logik implementieren je resultCode
        if (requestCode == lernrichtungRequestCode && resultCode == Activity.RESULT_OK) {
            println("DE-EN")
        }
        if (requestCode == lernrichtungRequestCode && resultCode == Activity.RESULT_OK) {
            println("EN-DE") // zweimal der gleiche resultCode funktioniert IMMER BEIDES.
        }

        //change Language Result Handler todo: Logik implementieren je resultCode
        if (requestCode == changeLanguageRequestCode && resultCode == Activity.RESULT_OK) {
            println("Deutsch")
        }
        if (requestCode == changeLanguageRequestCode && resultCode == Activity.RESULT_OK) {
            println("English")
        }

        //Highscore Result Handler todo: Logik implementieren je resultCode
        if (requestCode == highscoreRequestCode && resultCode == Activity.RESULT_OK) {
            println("highscore_view")
        }

    }
}