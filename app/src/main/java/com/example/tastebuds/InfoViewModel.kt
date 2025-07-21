package com.example.tastebuds

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InfoViewModel : ViewModel() {

    private val itemInfo = MutableLiveData<CartViewModel>()

    fun setData(itemData : CartViewModel){
        itemInfo.value = itemData
    }
    fun getData() : LiveData<CartViewModel>{
        return itemInfo
    }
}