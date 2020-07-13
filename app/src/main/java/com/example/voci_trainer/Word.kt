package com.example.voci_trainer
/**
 * READ-ME for Room SQLite Database Set-up (original code snipplets)
 * https://codelabs.developers.google.com/codelabs/android-room-with-a-view-kotlin/#0
 */
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


    @Entity(tableName = "word_table")
    class Word(
        @PrimaryKey @ColumnInfo(name = "word") val word: String
    )
