package com.example.qrscannerpractice.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity (tableName = "scan_table")
data class ScanItem (
    @ColumnInfo ("message") val message : String,
    @ColumnInfo ("favourite") val favourite : Boolean,
    @PrimaryKey (true) val id : Int = 0
)