package com.example.qrscannerpractice.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qrscannerpractice.databinding.FragmentRecentBinding
import com.example.qrscannerpractice.viewmodels.ScanViewModel


class RecentFragment : Fragment() {

    private lateinit var binding: FragmentRecentBinding

    private val viewModel: ScanViewModel by lazy {
        ViewModelProvider(requireActivity()).get(
            ScanViewModel::class.java
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()

    }

    private fun setRecyclerView (){
        viewModel.historyList.observe(viewLifecycleOwner){
            binding.recentRecycler.apply{
                layoutManager = LinearLayoutManager(activity)
                adapter = ScanHistoryAdapter()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = RecentFragment()
    }
}