package com.example.qrscannerpractice

import com.example.qrscannerpractice.room.ScanItem

interface ClickListener {
    fun openScanItem (scanItem: ScanItem)

    fun swipeItem (scanItem: ScanItem)
}