package com.app.foodhelper.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.foodhelper.databinding.CategoryItemsBinding
import com.app.foodhelper.pojo.Category
import com.bumptech.glide.Glide

class CategoriesAdapter(): RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    private var categoriesList = ArrayList<Category>()
    var onItemClick: ((Category)-> Unit)? = null

    fun setCategoryList(categoriesList:List<Category>){
        this.categoriesList = categoriesList as ArrayList<Category>
        notifyDataSetChanged()
    }

    inner class CategoryViewHolder(val binding: CategoryItemsBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            CategoryItemsBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(categoriesList[position].strCategoryThumb)
            .into(holder.binding.imgCategory)
        holder.binding.tvCategoryName.text = categoriesList[position].strCategory

        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(categoriesList[position])
        }
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }
}