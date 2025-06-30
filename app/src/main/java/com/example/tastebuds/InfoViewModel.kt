package com.example.tastebuds

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InfoViewModel : ViewModel() {

    private val itemInfo = MutableLiveData<HomeViewModel>()

    fun setData(itemData : HomeViewModel){
        itemInfo.value = itemData
    }
    fun getData() : LiveData<HomeViewModel>{
        return itemInfo
    }
}