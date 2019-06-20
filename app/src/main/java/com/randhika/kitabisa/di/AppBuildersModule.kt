package com.randhika.kitabisa.di

import android.arch.lifecycle.ViewModel
import com.randhika.kitabisa.feature.MainActivity
import com.randhika.kitabisa.feature.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Created by randhika
 */
@Module
abstract class AppBuildersModule {

    /**
     * Main Screen
     */
    @ActivityScope
    @ContributesAndroidInjector
    internal abstract fun bindMainActivity(): MainActivity

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun provideMainViewModel(viewModel: MainViewModel): ViewModel
}