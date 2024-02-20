package com.example.qrscannerpractice.di

import android.content.Context
import androidx.room.Room
import com.example.qrscannerpractice.room.ScanRepository
import com.example.qrscannerpractice.room.ScanResultsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun provideDatabase (@ApplicationContext app : Context) : ScanResultsDatabase{
        return Room.databaseBuilder(app, ScanResultsDatabase::class.java,
            "database.db").build()
    }

    @Provides
    @Singleton
    fun provideDao (db : ScanResultsDatabase) = db.scanResultDao()

    @Provides
    @Singleton
    fun provideRepo (db: ScanResultsDatabase) = ScanRepository(db.scanResultDao())
}