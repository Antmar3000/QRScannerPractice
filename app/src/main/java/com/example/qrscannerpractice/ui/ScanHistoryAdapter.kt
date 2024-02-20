package com.example.qrscannerpractice.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.ListAdapter
import com.example.qrscannerpractice.ClickListener
import com.example.qrscannerpractice.databinding.ScanItemBinding
import com.example.qrscannerpractice.room.ScanItem

class ScanHistoryAdapter (private val listener: ClickListener) : ListAdapter<ScanItem, ScanListViewHolder> (DiffUtil())  {

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<ScanItem>(){
        override fun areItemsTheSame(oldItem: ScanItem, newItem: ScanItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ScanItem, newItem: ScanItem): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScanListViewHolder {
        val binding = ScanItemBinding.inflate(LayoutInflater.from(parent.context) , parent, false)
        return ScanListViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ScanListViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

}