package com.jhonata.catapp.di

import com.jhonata.catapp.retrofit.TheCatApi
import com.jhonata.catapp.util.SupportInterceptor
import com.jhonata.catapp.util.baseUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideTheCatService(): TheCatApi {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.HEADERS
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val builder = OkHttpClient.Builder().apply {
            addInterceptor(interceptor)
            connectTimeout(20, TimeUnit.SECONDS)
            readTimeout(20, TimeUnit.SECONDS)
            addInterceptor(SupportInterceptor())
        }

        val retrofit = Retrofit.Builder().run {
            client(builder.build())
            baseUrl(baseUrl)
            addConverterFactory(GsonConverterFactory.create())
            build()
        }
        return retrofit.create(TheCatApi::class.java)
    }
}