package com.randhika.kitabisa.di

import com.randhika.kitabisa.KitaBisaApplication

class AppInjector {

    companion object {
        fun init(application: KitaBisaApplication) {
            DaggerAppComponent
                .builder()
                .application(application)
                .build()
                .inject(application)
        }
    }
}