package com.mycode.myapplication

import com.mycode.myapplication.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication


class BaseApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication>
    {
        return DaggerAppComponent.builder().application(this).build()
    }

}