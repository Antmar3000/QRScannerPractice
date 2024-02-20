package com.example.qrscannerpractice.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.qrscannerpractice.ClickListener
import com.example.qrscannerpractice.base.BaseFragment
import com.example.qrscannerpractice.databinding.FragmentRecentBinding
import com.example.qrscannerpractice.room.ScanItem
import com.example.qrscannerpractice.viewmodels.ScanViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecentFragment : BaseFragment<FragmentRecentBinding>() {

    private val viewModel: ScanViewModel by viewModels()


    private val listener: ClickListener = object : ClickListener {
        override fun openScanItem(scanItem: ScanItem) {
            viewModel.onItemClicked(scanItem)
        }

        override fun swipeItem(scanItem: ScanItem) {
            viewModel.deleteItem(scanItem)
        }

    }
    private val adapter = ScanHistoryAdapter(listener)
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRecentBinding =
        FragmentRecentBinding::inflate


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()


    }

    private fun setRecyclerView() {
        binding.recentRecycler.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = this@RecentFragment.adapter
            ItemTouchHelper(swipeToDeleteCallback).attachToRecyclerView(this)
        }
        viewModel.historyList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private val swipeToDeleteCallback = object : SwipeToDeleteCallback(listener) {
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            when (direction) {
                ItemTouchHelper.LEFT -> listener.swipeItem(adapter.currentList[viewHolder.adapterPosition])
            }
        }
    }

}