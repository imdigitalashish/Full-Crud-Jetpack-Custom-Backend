package com.example.todoapplication.di

import com.example.todoapplication.data.remote.NoteAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideNoteAPI(): NoteAPI {
        return Retrofit.Builder()
            .baseUrl("http://192.168.29.90:9090")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }
}