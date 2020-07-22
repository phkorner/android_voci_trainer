package com.example.voci_trainer

import org.junit.Test

import org.junit.Assert.*
import java.io.File

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testFileConnection() {

        var file = File("src/main/res/raw/database.txt")
        var englishWords = mutableListOf<String>()
        var germanWords = mutableListOf<String>()

        file.forEachLine {
            val strs = it.split(",").toTypedArray()
            englishWords.add(strs[0])
            germanWords.add(strs[1])
        }

        englishWords.shuffle()
        println(englishWords[0])
        println(englishWords[1])
        println(englishWords[2])
        println(germanWords[0])
        println(germanWords[1])
        println(germanWords[2])

    }
}