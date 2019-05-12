package com.mycode.myapplication.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "bitcointable")
class BitcoinPrice :Serializable {

    @SerializedName("market_price_usd")
    @Expose
    private var price : Double = 0.0

    @SerializedName("hash_rate")
    @Expose
    private var hashRate : String = ""

    @SerializedName("timestamp")
    @Expose
    private var timestamp : String = ""

    @SerializedName("difficulty")
    @Expose
    private var difficulty : String = ""

    @SerializedName("total_fees_btc")
    @Expose
    private var fees : String = ""

    private var time : String = ""

    @PrimaryKey(autoGenerate = true)
    private var id :Int = 0

    fun getPrice() : Double {
        return price
    }

    fun setPrice(price : Double) {
        this.price = price
    }

    fun getFees() : String {
        return fees
    }

    fun setFees(fees : String) {
        this.fees = fees
    }

    fun getTimestamp() : String {
        return timestamp
    }

    fun setTimestamp(timestamp : String) {
        this.timestamp = timestamp
    }

    fun getDifficulty() : String {
        return difficulty
    }

    fun setDifficulty(difficulty : String) {
        this.difficulty = difficulty
    }

    fun getHashRate() : String {
        return hashRate
    }

    fun setHashRate(hashRate : String) {
        this.hashRate = hashRate
    }

    fun setId (id:Int) {
        this.id=id
    }

    fun getId() : Int{
        return id
    }

    fun setTime(time : String){
        this.time = time
    }

    fun getTime() : String{
        return time
    }


}