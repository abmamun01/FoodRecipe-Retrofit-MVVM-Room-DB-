package com.mamunsproject.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mamunsproject.myapplication.databinding.PopularItemsBinding
import com.mamunsproject.myapplication.pojo.MealseByCategory

class MostPopularMealAdapter() :
    RecyclerView.Adapter<MostPopularMealAdapter.PopularMealViewHolder>() {

    private var mealsList = ArrayList<MealseByCategory>()
    lateinit var onItemClick: ((MealseByCategory) -> Unit)


    fun setMeal(listMealseBy: ArrayList<MealseByCategory>) {
        this.mealsList = listMealseBy
        notifyDataSetChanged()
    }

    class PopularMealViewHolder(val binding: PopularItemsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
        return PopularMealViewHolder(
            PopularItemsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealsList[position].strMealThumb)
            .into(holder.binding.imgPopularItem)


        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealsList[position])

        }
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }
}