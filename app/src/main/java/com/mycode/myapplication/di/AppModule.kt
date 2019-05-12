package com.mycode.myapplication.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mycode.myapplication.util.Constants
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object AppModule {

    @Singleton
    @JvmStatic
    @Provides
    fun providesGson() : Gson = GsonBuilder().create()

    @Singleton
    @JvmStatic
    @Provides
    internal fun provideRetrofitInstance(gson : Gson, client : OkHttpClient) : Retrofit
    {
        return Retrofit.Builder()
            .baseUrl(Constants.baseURL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    @Singleton
    @JvmStatic
    @Provides
    fun providesHTTPClient() :OkHttpClient
    {
        val builder = OkHttpClient.Builder()
        return builder.build()
    }

}