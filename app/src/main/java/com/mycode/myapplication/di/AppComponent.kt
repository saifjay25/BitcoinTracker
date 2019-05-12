package com.mycode.myapplication.di

import android.app.Application
import com.mycode.myapplication.BaseApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class,
    ViewModelFactoryModule::class,
    ActivityBuilderModule::class,
    AppModule::class])
interface AppComponent : AndroidInjector<BaseApplication> {

    @Component.Builder
    interface Builder
    {
        @BindsInstance
        fun application(app : Application) : Builder

        fun build() : AppComponent


    }

}