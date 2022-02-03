package com.mamunsproject.myapplication.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mamunsproject.myapplication.pojo.*
import com.mamunsproject.myapplication.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeViewModel() : ViewModel() {

    private var randomMealMutableLiveData = MutableLiveData<Meal>()
    private var popularItemsLiveData = MutableLiveData<List<MealseByCategory>>()
    private var categoriesLiveData = MutableLiveData<List<Category>>()


    fun getRandomMeal() {

        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealsList> {
            override fun onResponse(call: Call<MealsList>, response: Response<MealsList>) {

                if (response.body() != null) {

                    val randomMeal: Meal = response.body()!!.meals[0]
                    randomMealMutableLiveData.value = randomMeal


                } else {
                    return
                }
            }

            override fun onFailure(call: Call<MealsList>, t: Throwable) {

            }

        })


    }

    fun getCategories() {
        RetrofitInstance.api.getCategories().enqueue(object : Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                response.body()?.let { categoryList ->
                    categoriesLiveData.postValue(categoryList.categories)
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {

            }

        })
    }


    fun getPopularItems() {
        RetrofitInstance.api.getPopularItems("Seafood")
            .enqueue(object : Callback<MealsByCategoryList> {
                override fun onResponse(
                    call: Call<MealsByCategoryList>,
                    response: Response<MealsByCategoryList>
                ) {


                    if (response.body() != null) {
                        popularItemsLiveData.value = response.body()!!.meals


                    }
                }

                override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
    }

    //LiveData just read korte parbe but MutableLiveData read & write 2ta e korte parbe
    fun observeRandomMealLiveData(): LiveData<Meal> {
        return randomMealMutableLiveData
    }

    fun observePopularItemsLiveData(): LiveData<List<MealseByCategory>> {
        return popularItemsLiveData
    }

    fun observeCategoriesLiveData() :LiveData<List<Category>>{

        return categoriesLiveData
    }
}