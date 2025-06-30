package com.example.tastebuds

object FoodMenu {
    private lateinit var dataList : ArrayList<HomeViewModel>
    private lateinit var restaurantList : ArrayList<String>
    fun getData() : ArrayList<HomeViewModel>{
        dataList = ArrayList()

        dataList.add(HomeViewModel("Pancake", "120", R.drawable.pancake))
        dataList.add(HomeViewModel("Noodles", "140", R.drawable.noodles))
        dataList.add(HomeViewModel("Salad", "60", R.drawable.salad))
        dataList.add(HomeViewModel("Cake", "250", R.drawable.cake))
        dataList.add(HomeViewModel("Pancake", "120", R.drawable.pancake))
        dataList.add(HomeViewModel("Salad", "60", R.drawable.salad))
        dataList.add(HomeViewModel("Cake", "250", R.drawable.cake))
        dataList.add(HomeViewModel("Noodles", "140", R.drawable.noodles))
        dataList.add(HomeViewModel("Pancake", "120", R.drawable.pancake))

        return dataList
    }
    fun getRestaurants() : ArrayList<String>{
        restaurantList = ArrayList()

        restaurantList.add("TacoBell")
        restaurantList.add("Wow")
        restaurantList.add("Sandy")
        restaurantList.add("Donald's")
        restaurantList.add("Cakey")
        restaurantList.add("TacoBell")
        restaurantList.add("Frost")
        restaurantList.add("Chings")
        restaurantList.add("First Meals")

        return restaurantList
    }
}