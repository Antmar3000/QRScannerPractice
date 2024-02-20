package com.example.qrscannerpractice.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.qrscannerpractice.R
import com.example.qrscannerpractice.room.ScanItem
import com.example.qrscannerpractice.room.ScanRepository
import com.example.qrscannerpractice.ui.Favourites
import com.example.qrscannerpractice.ui.Key
import com.example.qrscannerpractice.ui.Recent
import com.example.qrscannerpractice.ui.Scanner
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import javax.inject.Inject

@HiltViewModel
class ScanViewModel @Inject constructor(
    private val repository: ScanRepository) : ViewModel() {

    val historyList: LiveData<List<ScanItem>> = repository.scanItems.asLiveData()
    val favouritesList: LiveData<List<ScanItem>> = repository.favouriteItems.asLiveData()

    private val _showDialog: MutableLiveData<ScanItem> = MutableLiveData()
    val showDialog: LiveData<ScanItem> = _showDialog
    private val _fragment: MutableLiveData<Key> = MutableLiveData()
    val fragment: LiveData<Key> = _fragment


    fun onItemScanned(scanItem: ScanItem) = viewModelScope.launch {
        repository.insertItem(scanItem)
        _showDialog.value = scanItem
    }

    fun deleteItem(scanItem: ScanItem) = viewModelScope.launch {
        repository.deleteItem(scanItem)
    }

    fun updateItemFavourite(scanItem: ScanItem) = viewModelScope.launch {
        repository.updateItem(
            if (scanItem.favourite) scanItem.copy(message = scanItem.message, favourite = false)
            else scanItem.copy(message = scanItem.message, favourite = true)
        )
    }

    fun onItemClicked(scanItem: ScanItem) {
        _showDialog.value = scanItem
    }

    fun onMenuClicked(itemId: Int) {
        when (itemId) {
            R.id.scan -> _fragment.value = Scanner
            R.id.recent -> _fragment.value = Recent
            R.id.favourites -> _fragment.value = Favourites
        }
    }

    fun onPermissionGranted () {
        _fragment.value = Scanner
    }


}

//@AssistedFactory
//class ScanItemModelFactory(private val repository: ScanRepository) : ViewModelProvider.Factory {
//
//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(ScanViewModel::class.java)) return ScanViewModel(repository) as T
//        throw IllegalArgumentException("Unknown class")
//    }
//}

@AssistedFactory
interface ScanItemModelFactory {
    fun create (repository: ScanRepository) : ScanViewModel
}