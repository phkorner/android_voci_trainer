package com.example.voci_trainer

import java.io.File

val file = File("./res/raw/vocabulary1.txt")
val mutableList = mutableListOf<String>()

fun readFile() {

    file.bufferedReader().forEachLine {
        mutableList.add("$it")
    }

}