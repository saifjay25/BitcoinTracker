package com.mycode.myapplication.di.main

import com.mycode.myapplication.network.MainAPI
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
object MainModule {

    //this module is inside of a subcomponent
    @Provides
    @JvmStatic
    fun provideMainAPI (retrofit : Retrofit): MainAPI
    {
        return retrofit.create(MainAPI::class.java)
    }

}