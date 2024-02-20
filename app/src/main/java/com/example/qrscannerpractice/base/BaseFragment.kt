package com.example.qrscannerpractice.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.qrscannerpractice.viewmodels.ScanViewModel

abstract class BaseFragment <binding : ViewBinding> : Fragment () {

    private var _binding : ViewBinding? = null
    abstract val bindingInflater : (LayoutInflater, ViewGroup?, Boolean) -> binding


    @Suppress("UNCHECKED_CAST")
    protected val binding : binding
        get() = _binding as binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater, container, false)
        return requireNotNull(_binding).root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}