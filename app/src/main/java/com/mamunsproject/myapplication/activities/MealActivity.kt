package com.mamunsproject.myapplication.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.mamunsproject.myapplication.R
import com.mamunsproject.myapplication.fragment.HomeFragment
import com.mamunsproject.myapplication.pojo.Meal
import com.mamunsproject.myapplication.viewModel.MealViewModel
import kotlinx.android.synthetic.main.activity_meal.*

class MealActivity : AppCompatActivity() {

    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var mealMVVM: MealViewModel
    private lateinit var youtubeLink: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal)

        mealMVVM = ViewModelProviders.of(this)[MealViewModel::class.java]

        getMealInformation()
        setInformationInViews()

        loadingCase()
        mealMVVM.getMealDetails(mealId)
        observerMealDetailsLiveData()

        onYoutubeImageClick()
    }

    private fun onYoutubeImageClick() {

        img_youtube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private fun observerMealDetailsLiveData() {
        mealMVVM.observerMealDetailsLiveData().observe(this, object : Observer<Meal> {
            override fun onChanged(t: Meal?) {
                onResponseCase()

                val meal = t

                tv_category.text = "Category ${meal!!.strCategory}"
                tv_area.text = "Area :${meal!!.strArea}"
                tv_instruction.text = meal.strInstructions
                youtubeLink = meal.strYoutube

            }

        })
    }

    private fun loadingCase() {

        progress_bar.visibility = View.VISIBLE
        add_to_favorite.visibility = View.INVISIBLE
        tv_instruction.visibility = View.INVISIBLE
        tv_category.visibility = View.INVISIBLE
        tv_area.visibility = View.INVISIBLE
        img_youtube.visibility = View.INVISIBLE

    }

    private fun onResponseCase() {

        progress_bar.visibility = View.INVISIBLE
        add_to_favorite.visibility = View.VISIBLE
        tv_instruction.visibility = View.VISIBLE
        tv_category.visibility = View.VISIBLE
        tv_area.visibility = View.VISIBLE
        img_youtube.visibility = View.VISIBLE
    }

    private fun setInformationInViews() {

        Glide.with(applicationContext)
            .load(mealThumb)
            .into(img_meal_detail)

        collapsing_toolbar.title = mealName
        collapsing_toolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        collapsing_toolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getMealInformation() {

        val intent = intent

        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!

    }
}