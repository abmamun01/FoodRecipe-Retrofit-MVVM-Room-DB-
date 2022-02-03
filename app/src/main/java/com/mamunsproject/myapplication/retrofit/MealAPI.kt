package com.mamunsproject.myapplication.retrofit

import com.mamunsproject.myapplication.pojo.CategoryList
import com.mamunsproject.myapplication.pojo.MealsByCategoryList
import com.mamunsproject.myapplication.pojo.MealsList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealAPI {

    @GET("random.php")
    fun getRandomMeal(): Call<MealsList>


    @GET("lookup.php?")
    fun getMealDetails(@Query("i") id: String): Call<MealsList>

    @GET("filter.php?")
    fun getPopularItems(@Query("c") categoryName: String): Call<MealsByCategoryList>

    @GET("categories.php")
    fun getCategories():Call<CategoryList>

}