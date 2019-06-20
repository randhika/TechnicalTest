package com.randhika.kitabisa.core

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject
internal constructor(private val mCreators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        var creator: Provider<out ViewModel>? = mCreators[modelClass]
        if (creator == null) {
            for ((key, value) in mCreators) {
                if (modelClass.isAssignableFrom(key)) {
                    creator = value
                    break
                }
            }
        }
        if (creator == null) {
            throw IllegalArgumentException("unknown model class $modelClass")
        }
        try {
            return creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }
}
