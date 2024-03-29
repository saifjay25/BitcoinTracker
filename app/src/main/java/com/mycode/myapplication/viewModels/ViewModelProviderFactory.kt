package com.mycode.myapplication.viewModels

import androidx.lifecycle.ViewModel
import javax.inject.Inject
import javax.inject.Provider
import androidx.lifecycle.ViewModelProvider

//injecting a map of the key or the classes of the viewmodels and value are the actual viewmodels wrapped in a provider
@Suppress("UNCHECKED_CAST")
class ViewModelProviderFactory @Inject
constructor(private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>) : ViewModelProvider.Factory
{

    override fun <T : ViewModel> create(modelClass: Class<T>): T
    {
        var creator: Provider<out ViewModel>? = creators[modelClass]
        if (creator == null) { // if the viewmodel has not been created

            for ((key, value) in creators) {

                if (modelClass.isAssignableFrom(key))
                {
                    creator = value
                    break
                }
            }
        }

        if (creator == null)
        {
            throw IllegalArgumentException("unknown model class $modelClass")
        }

        try {
            return creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }

    companion object
    {
        private val TAG = "ViewModelProviderFactor"
    }
}
