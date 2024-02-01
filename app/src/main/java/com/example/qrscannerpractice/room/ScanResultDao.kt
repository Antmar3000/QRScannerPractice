package com.example.qrscannerpractice.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ScanResultDao {

    @Query ("SELECT * FROM scan_table ORDER BY id ASC")
    fun allItems () : Flow<List<ScanItem>>

    @Query("SELECT * FROM scan_table WHERE favourite = true ORDER BY id ASC")
    fun favouriteItems() : Flow<List<ScanItem>>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem (scanItem : ScanItem)

    @Delete
    suspend fun deleteItem (scanItem: ScanItem)

}