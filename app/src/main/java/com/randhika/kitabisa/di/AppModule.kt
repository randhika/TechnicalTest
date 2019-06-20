package com.randhika.kitabisa.di

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.randhika.kitabisa.BuildConfig
import com.randhika.kitabisa.KitaBisaApplication
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created: by randhika
 */

@Module(includes = [AppBuildersModule::class])
class AppModule {

    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    internal fun provideKitaBisaApplication(application: Application): KitaBisaApplication {
        return application as KitaBisaApplication
    }

    @Provides
    @Singleton
    internal fun provideChuckInterceptor(context: Context): ChuckInterceptor {
        return ChuckInterceptor(context)
    }

    @Singleton
    @Provides
    internal fun provideOkHttpClient(
            chuckInterceptor: ChuckInterceptor): OkHttpClient {

        val builder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(logging)
        }

        builder.addInterceptor(chuckInterceptor)

        return builder.build()
    }
}