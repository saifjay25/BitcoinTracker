package com.mycode.myapplication.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mycode.myapplication.entity.BitcoinPrice

@Dao
interface BitcoinDAO {

    @Insert
    fun add(toDoObject : BitcoinPrice)

    @Query("SELECT * FROM bitcointable")
    fun getEverything() : LiveData<MutableList<BitcoinPrice>>

    @Query("DELETE FROM bitcointable")
    fun deleteEverything()
}