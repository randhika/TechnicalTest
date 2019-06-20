package com.randhika.kitabisa.di

import android.app.Application
import com.randhika.kitabisa.KitaBisaApplication
import com.randhika.kitabisa.core.BaseModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created: by randhika.
 */
@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class, BaseModule::class])

interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(application: KitaBisaApplication)
}