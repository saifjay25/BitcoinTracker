package com.mycode.myapplication.network

import com.mycode.myapplication.entity.BitcoinPrice
import io.reactivex.Flowable
import retrofit2.http.GET

interface MainAPI {

    @GET("stats")
    fun getPrice(): Flowable<BitcoinPrice>
}