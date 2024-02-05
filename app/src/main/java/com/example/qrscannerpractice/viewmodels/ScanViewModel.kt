package com.example.qrscannerpractice.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.qrscannerpractice.room.ScanItem
import com.example.qrscannerpractice.room.ScanRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class ScanViewModel (private val repository: ScanRepository) : ViewModel() {

    var historyList: LiveData<List<ScanItem>> = repository.scanItems.asLiveData()
    var favouritesList: LiveData<List<ScanItem>> = repository.favouriteItems.asLiveData()

    fun insertItem(scanItem: ScanItem) = viewModelScope.launch {
        repository.insertItem(scanItem)
    }

    fun updateItem (scanItem: ScanItem) = viewModelScope.launch {
        repository.updateItem(scanItem)
    }

    fun deleteItem (scanItem: ScanItem) = viewModelScope.launch {
        repository.deleteItem(scanItem)
    }

}

class ScanItemModelFactory (private val repository: ScanRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScanViewModel::class.java))
            return ScanViewModel(repository) as T
        throw IllegalArgumentException("Unknown class")
    }
}