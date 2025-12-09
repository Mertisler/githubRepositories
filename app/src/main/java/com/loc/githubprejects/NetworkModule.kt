package com.loc.githubprejects

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        // Retrofit'in nasıl üretileceğini tarif ediyoruz
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideGithubApiService(retrofit: Retrofit): GithubApiService {
        // Hilt, yukarıdaki provideRetrofit fonksiyonundan gelen sonucu
        // otomatik olarak buraya parametre (retrofit) olarak verir.
        return retrofit.create(GithubApiService::class.java)
    }
}