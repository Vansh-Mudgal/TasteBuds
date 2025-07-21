package com.example.tastebuds

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartViewModel(
    val foodName : String?,
    val price : Long?,
    val image : String?,
    val restName : String?,
    val foodId : String) : Parcelable
