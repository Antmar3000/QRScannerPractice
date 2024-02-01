package com.example.qrscannerpractice.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qrscannerpractice.R
import com.example.qrscannerpractice.databinding.FragmentFavouritesBinding
import com.example.qrscannerpractice.viewmodels.ScanViewModel


class FavouritesFragment : Fragment() {

    private lateinit var binding: FragmentFavouritesBinding

    private val viewModel : ScanViewModel by lazy {
        ViewModelProvider(requireActivity()).get(
            ScanViewModel::class.java
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setRecyclerView (){
        viewModel.favouritesList.observe(viewLifecycleOwner) {
            binding.favRecycler.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = ScanHistoryAdapter()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance () = FavouritesFragment()
    }


}