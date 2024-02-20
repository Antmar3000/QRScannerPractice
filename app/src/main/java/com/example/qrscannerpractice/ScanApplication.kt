package com.example.qrscannerpractice

import android.app.Application
import com.example.qrscannerpractice.room.ScanRepository
import com.example.qrscannerpractice.room.ScanResultsDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ScanApplication : Application () {
//    private val database by lazy { ScanResultsDatabase.getDatabase(this) }
//    val repository by lazy { ScanRepository(database.scanResultDao()) }
}