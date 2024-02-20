package com.example.qrscannerpractice.ui

import javax.inject.Inject

class Router @Inject constructor()  {

    fun choose (key : Key) = when (key) {
            is Scanner -> ScannerFragment()
            is Recent -> RecentFragment()
            is Favourites -> FavouritesFragment()
        }

//    companion object {
//        val router = Router()
//    }

}

sealed interface Key

data object Scanner : Key
data object Recent : Key
data object Favourites : Key

