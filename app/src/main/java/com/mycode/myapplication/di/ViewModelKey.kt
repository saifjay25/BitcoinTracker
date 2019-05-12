package com.mycode.myapplication.di;

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

//map key used to map similar dependecies and group them together
@MapKey
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)
