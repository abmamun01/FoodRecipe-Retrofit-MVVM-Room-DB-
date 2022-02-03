package com.mamunsproject.myapplication.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.mamunsproject.myapplication.R
import com.mamunsproject.myapplication.activities.MealActivity
import com.mamunsproject.myapplication.adapter.CategoriesAdapter
import com.mamunsproject.myapplication.adapter.MostPopularMealAdapter
import com.mamunsproject.myapplication.pojo.MealseByCategory
import com.mamunsproject.myapplication.pojo.Meal
import com.mamunsproject.myapplication.viewModel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    private lateinit var homeMVVM: HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popularItemsAdapter: MostPopularMealAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter


    companion object {
        const val MEAL_ID = "com.mamunsproject.myapplication.fragment.idMeal"
        const val MEAL_NAME = "com.mamunsproject.myapplication.fragment.nameMeal"
        const val MEAL_THUMB = "com.mamunsproject.myapplication.fragment.thumbMeal"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //  homeMVVM = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        homeMVVM = ViewModelProviders.of(this)[HomeViewModel::class.java]
        popularItemsAdapter = MostPopularMealAdapter()

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePopularItemRecyclerView()
        homeMVVM.getRandomMeal()
        observerRandomMeal()
        onRandomMealClick()

        homeMVVM.getPopularItems()
        observePopularItemsLiveData()
        onPopularItemClick()


        prepareCategoriesRecyclerView()
        homeMVVM.getCategories()
        onObserveCategoriesLiveData()

    }

    private fun prepareCategoriesRecyclerView() {

        categoriesAdapter=CategoriesAdapter()
        rec_view_categories.apply {
            layoutManager=GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter=categoriesAdapter
        }
    }

    private fun onObserveCategoriesLiveData() {
        homeMVVM.observeCategoriesLiveData().observe(viewLifecycleOwner, Observer { categories ->
            categories.forEach {category->

                categoriesAdapter.setCategoryList(categories)

                Log.d("TEST", "onObserveCategoriesLiveData: ${category.strCategory}")
            }
        })
    }

    private fun onPopularItemClick() {

        popularItemsAdapter.onItemClick = { meal ->
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, meal.idMeal)
            intent.putExtra(MEAL_NAME, meal.strMeal)
            intent.putExtra(MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun preparePopularItemRecyclerView() {
        rec_view_meals_popular.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularItemsAdapter
        }
    }

    private fun observePopularItemsLiveData() {

        homeMVVM.observePopularItemsLiveData()
            .observe(viewLifecycleOwner, object : Observer<List<MealseByCategory>> {
                override fun onChanged(mealList: List<MealseByCategory>?) {

                    popularItemsAdapter.setMeal(mealList as ArrayList<MealseByCategory>)
                }


            })


    }

    private fun onRandomMealClick() {
        random_meal_card.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)

            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME, randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB, randomMeal.strMealThumb)

            startActivity(intent)
        }
    }

    private fun observerRandomMeal() {
        homeMVVM.observeRandomMealLiveData().observe(viewLifecycleOwner, { meal ->

            Glide.with(this@HomeFragment).load(meal!!.strMealThumb).into(random_meal_img)
            this.randomMeal = meal

        })
    }

}