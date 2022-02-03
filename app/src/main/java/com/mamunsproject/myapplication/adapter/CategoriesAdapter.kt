package com.mamunsproject.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mamunsproject.myapplication.databinding.CategoryItemLayoutBinding
import com.mamunsproject.myapplication.pojo.Category
import com.mamunsproject.myapplication.pojo.CategoryList

class CategoriesAdapter() : RecyclerView.Adapter<CategoriesAdapter.CateoryViewHolder>() {

    private var categoryList = ArrayList<Category>()

    fun setCategoryList(categoryList: List<Category>) {
        this.categoryList = categoryList as ArrayList<Category>
        notifyDataSetChanged()
    }


    class CateoryViewHolder(val binding: CategoryItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CateoryViewHolder {
        return CateoryViewHolder(
            CategoryItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CateoryViewHolder, position: Int) {

        Glide.with(holder.itemView).load(categoryList[position].strCategoryThumb).into(holder.binding.imgCategory)
        holder.binding.tvCategoryName.text = categoryList[position].strCategory

    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}