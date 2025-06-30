package com.example.tastebuds

object Notifications {
    private lateinit var dataList : ArrayList<NotificationViewModel>
    fun getData() : ArrayList<NotificationViewModel>{
        dataList = ArrayList()
        dataList.add(NotificationViewModel(R.drawable.illustration,"Your Order Has Been Placed Successfully!"))
        dataList.add(NotificationViewModel(R.drawable.sademoji, "Your Order Has Been Cancelled Successfully!"))
        dataList.add(NotificationViewModel(R.drawable.truck, "Your Order Is Out For Delivery"))

        return dataList
    }
}