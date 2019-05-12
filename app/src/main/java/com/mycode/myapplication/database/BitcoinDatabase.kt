package com.mycode.myapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mycode.myapplication.entity.BitcoinPrice

@Database(entities = [BitcoinPrice::class], version = 10)
abstract class BitcoinDatabase : RoomDatabase() {

    abstract fun ToDoDao(): BitcoinDAO

    companion object {

        private var instance : BitcoinDatabase? = null

        @Synchronized fun  getInstance(context: Context) : BitcoinDatabase {
            if(instance ==null){

                instance = Room.databaseBuilder(context.applicationContext,
                    BitcoinDatabase::class.java,"database").fallbackToDestructiveMigration().build()
            }
            return instance as BitcoinDatabase
        }
    }

}