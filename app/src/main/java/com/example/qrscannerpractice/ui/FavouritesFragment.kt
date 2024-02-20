package com.example.qrscannerpractice.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qrscannerpractice.ClickListener
import com.example.qrscannerpractice.base.BaseFragment
import com.example.qrscannerpractice.databinding.FragmentFavouritesBinding
import com.example.qrscannerpractice.room.ScanItem
import com.example.qrscannerpractice.viewmodels.ScanViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouritesFragment : BaseFragment<FragmentFavouritesBinding>() {


    private val viewModel: ScanViewModel by viewModels()

    private val listener = object : ClickListener {
        override fun openScanItem(scanItem: ScanItem) {
            viewModel.onItemClicked(scanItem)
        }

        override fun swipeItem(scanItem: ScanItem) {
            viewModel.deleteItem(scanItem)
        }

    }
    private val adapter = ScanHistoryAdapter(listener)
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFavouritesBinding =
        FragmentFavouritesBinding::inflate


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
    }

    private fun setRecyclerView() {
        binding.favRecycler.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = this@FavouritesFragment.adapter
        }
        viewModel.favouritesList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }


}