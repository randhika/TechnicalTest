package com.randhika.kitabisa.di

import javax.inject.Qualifier

/**
 * A dagger qualifier to annotates the declaration to return API BaseUrl string
 *
 * As short information, Qualifier is useful if we need two different objects of the same return
 * type
 *
 *
 * You can read more about it in the [Dependency Injection with Dagger 2]
 * (https://github.com/codepath/android_guides/wiki/Dependency-Injection-with-Dagger-2).
 *
 * Created by randhika.
 */

@Qualifier
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class ApiBaseUrl