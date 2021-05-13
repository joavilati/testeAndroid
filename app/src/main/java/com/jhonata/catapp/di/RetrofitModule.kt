package com.jhonata.catapp.di

import com.jhonata.catapp.BuildConfig
import com.jhonata.catapp.retrofit.TheCatApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

const val BASE_URL = BuildConfig.BASE_URL

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideTheCatService(supportInterceptor: Interceptor): TheCatApi {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.HEADERS
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val builder = OkHttpClient.Builder().apply {
            addInterceptor(interceptor)
            connectTimeout(20, TimeUnit.SECONDS)
            readTimeout(20, TimeUnit.SECONDS)
            addInterceptor(supportInterceptor)
        }

        val retrofit = Retrofit.Builder().run {
            client(builder.build())
            baseUrl(BASE_URL)
            addConverterFactory(GsonConverterFactory.create())
            build()
        }
        return retrofit.create(TheCatApi::class.java)
    }

    @Singleton
    @Provides
    fun providesSupportInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request().newBuilder().run {
                addHeader("Content-Type", "application/json")
                addHeader("Accept", "application/json")
                build()
            }
            chain.proceed(request)
        }
    }
}