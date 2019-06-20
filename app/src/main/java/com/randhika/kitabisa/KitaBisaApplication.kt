package com.randhika.kitabisa

import android.app.Activity
import android.app.Application
import com.randhika.kitabisa.di.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class KitaBisaApplication : Application(), HasActivityInjector {

    companion object {

        private lateinit var sInstance: KitaBisaApplication
    }

    @Inject
    lateinit var mDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        sInstance = this
        AppInjector.init(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return mDispatchingAndroidInjector
    }
}