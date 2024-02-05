package com.example.qrscannerpractice.room

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class ScanRepository (private val scanResultDao: ScanResultDao) {

    val scanItems : Flow<List<ScanItem>> = scanResultDao.allItems()
    val favouriteItems: Flow<List<ScanItem>> = scanResultDao.favouriteItems()

    @WorkerThread
    suspend fun insertItem (scanItem: ScanItem) {
        scanResultDao.insertItem(scanItem)
    }

    suspend fun updateItem (scanItem: ScanItem) {
        scanResultDao.updateItem(scanItem)
    }

    @WorkerThread
    suspend fun deleteItem (scanItem: ScanItem) {
        scanResultDao.deleteItem(scanItem)
    }
}