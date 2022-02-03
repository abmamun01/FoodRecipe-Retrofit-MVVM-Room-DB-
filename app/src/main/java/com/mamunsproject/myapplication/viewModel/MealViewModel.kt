package com.mamunsproject.myapplication.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mamunsproject.myapplication.pojo.Meal
import com.mamunsproject.myapplication.pojo.MealsList
import com.mamunsproject.myapplication.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel() : ViewModel() {

    private var mealDetailsLiveData = MutableLiveData<Meal>()

    fun getMealDetails(id: String) {
        RetrofitInstance.api.getMealDetails(id).enqueue(object : Callback<MealsList> {
            override fun onResponse(call: Call<MealsList>, response: Response<MealsList>) {

                if (response.body() != null) {
                    mealDetailsLiveData.value = response.body()!!.meals[0]
                }
                else return
            }

            override fun onFailure(call: Call<MealsList>, t: Throwable) {

                Log.d("INADAPTERSITUATION", "text ${t.message}")

            }

        })
    }
    fun observerMealDetailsLiveData():LiveData<Meal>{
        return mealDetailsLiveData
    }
}