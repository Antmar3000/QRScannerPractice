package com.example.qrscannerpractice.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.qrscannerpractice.MainActivity
import com.example.qrscannerpractice.R
import com.example.qrscannerpractice.databinding.FragmentScannerBinding
import com.example.qrscannerpractice.room.ScanItem
import com.example.qrscannerpractice.viewmodels.ScanViewModel
import me.dm7.barcodescanner.zbar.Result
import me.dm7.barcodescanner.zbar.ZBarScannerView


class ScannerFragment : Fragment(), ZBarScannerView.ResultHandler {

    private lateinit var zBarView: ZBarScannerView


    private val viewModel: ScanViewModel by lazy {
        ViewModelProvider(requireActivity()).get(
            ScanViewModel::class.java
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        zBarView = ZBarScannerView(requireActivity())
        return zBarView
    }

    override fun onPause() {
        super.onPause()
        zBarView.stopCamera()
    }

    override fun onDestroy() {
        super.onDestroy()
        zBarView.stopCamera()
    }

    override fun onResume() {
        super.onResume()
        zBarView.setResultHandler(this)
        zBarView.startCamera()
    }

    override fun onStart() {
        super.onStart()
        zBarView.startCamera()
    }

    override fun handleResult(result: Result?) {
        qrResult(result?.contents)
        activity?.let {
            (it as MainActivity).showDialog(result?.contents)
        }
    }

    private fun qrResult(content: String?) {
        if (content.isNullOrEmpty()) {
            Toast.makeText(context, R.string.empty_result, Toast.LENGTH_SHORT).show()
        } else {
            saveData(content)
        }
    }

    private fun saveData(content: String) {
        val newResult = ScanItem (content, false)
        viewModel.insertItem(newResult)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ScannerFragment()
    }

}