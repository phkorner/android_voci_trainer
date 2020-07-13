package com.example.voci_trainer
/**
 * READ-ME for Room SQLite Database Set-up (original code snipplets)
 * https://codelabs.developers.google.com/codelabs/android-room-with-a-view-kotlin/#0
 */
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

    @Dao
    interface WordDao {

        @Query("SELECT * from word_table ORDER BY word ASC")
        fun getAlphabetizedWords(): LiveData<List<Word>>

        @Insert(onConflict = OnConflictStrategy.IGNORE)
        suspend fun insert(word: Word)

        @Query("DELETE FROM word_table")
        suspend fun deleteAll()
    }
