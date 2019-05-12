package com.mycode.myapplication.database

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.room.Dao
import com.github.mikephil.charting.utils.Utils.init
import com.mycode.myapplication.entity.BitcoinPrice

class Repository(app: Application) {
    private var dao : BitcoinDAO
    private var everything : LiveData<MutableList<BitcoinPrice>>

    init {
        val database : BitcoinDatabase = BitcoinDatabase.getInstance(app)
        dao = database.ToDoDao()
        everything = dao.getEverything()
    }

    fun add (todo : BitcoinPrice){
        AddConcurrently(dao).execute(todo)
    }

    fun getEverything() : LiveData<MutableList<BitcoinPrice>>{
        return everything
    }

    fun deleteEverything(){
        deleteConcurrently(dao).execute()
    }

    companion object {

        //pass the object, do not need progress and return nothing
        //since class is static you cannot access the DAO of the repository
        private class AddConcurrently(private var dao: BitcoinDAO) : AsyncTask<BitcoinPrice, Void, Void>() {
            override fun doInBackground(vararg params: BitcoinPrice?): Void? {
                //only get one todo object
                dao.add(params[0]!!)
                return null
            }
        }

        private class deleteConcurrently(private var dao : BitcoinDAO) : AsyncTask<Void,Void,Void>(){
            override fun doInBackground(vararg params: Void?): Void? {
                dao.deleteEverything()
                return null
            }

        }
    }
}