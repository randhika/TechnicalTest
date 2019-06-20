package com.randhika.kitabisa.core;

import android.arch.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;

import javax.inject.Singleton;

@Module
public abstract class BaseModule {

    @Singleton
    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}