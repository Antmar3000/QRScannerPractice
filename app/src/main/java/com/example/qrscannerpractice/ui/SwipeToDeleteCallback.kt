package com.example.qrscannerpractice.ui

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.qrscannerpractice.ClickListener

abstract class SwipeToDeleteCallback (private val listener : ClickListener) : ItemTouchHelper.SimpleCallback(
    0, ItemTouchHelper.LEFT)
 {
     override fun onMove(
         recyclerView: RecyclerView,
         viewHolder: RecyclerView.ViewHolder,
         target: RecyclerView.ViewHolder
     ): Boolean {
         return false
     }


 }