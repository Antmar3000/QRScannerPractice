package com.example.qrscannerpractice.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.qrscannerpractice.ClickListener
import com.example.qrscannerpractice.databinding.ScanItemBinding
import com.example.qrscannerpractice.room.ScanItem

class ScanListViewHolder(private val binding: ScanItemBinding, private val listener: ClickListener) :
    RecyclerView.ViewHolder(binding.root) {

        fun bindItem (scanItem: ScanItem) = with(binding) {
            scannedTextView.text = scanItem.message
            favouriteImage.visibility = if (scanItem.favourite) View.VISIBLE else View.INVISIBLE
            binding.scanItemCardView.setOnClickListener{listener.openScanItem(scanItem)}
        }
}