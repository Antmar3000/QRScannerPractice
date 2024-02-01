package com.example.qrscannerpractice.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database (entities = [ScanItem::class], version = 1, exportSchema = true)
abstract class ScanResultsDatabase : RoomDatabase () {

    abstract fun scanResultDao () : ScanResultDao

    companion object {
        @Volatile
        private var INSTANCE: ScanResultsDatabase? = null

        fun getDatabase (context: Context) : ScanResultsDatabase {
            return  INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext,
                    ScanResultsDatabase::class.java, "scan_results_database").build()
                INSTANCE = instance
                instance
            }
        }
    }
}