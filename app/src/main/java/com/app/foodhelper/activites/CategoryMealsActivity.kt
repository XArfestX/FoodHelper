package com.app.foodhelper.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.app.foodhelper.adapters.CategoryMealsAdapter
import com.app.foodhelper.databinding.ActivityCategoryMealsBinding
import com.app.foodhelper.fragments.HomeFragment
import com.app.foodhelper.viewModel.CategoryMealsViewModel

class CategoryMealsActivity : AppCompatActivity() {
    lateinit var binding: ActivityCategoryMealsBinding
    lateinit var categoryMealsViewModel: CategoryMealsViewModel
    lateinit var categoryMealsAdapter: CategoryMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareRecyclerView()

        categoryMealsViewModel = ViewModelProviders.of(this)[CategoryMealsViewModel::class.java]

        categoryMealsViewModel.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)

        categoryMealsViewModel.observerMealsLiveData().observe(this, Observer { mealsList->
            binding.tvCategoryCount.text = mealsList.size.toString()
            categoryMealsAdapter.setMealsList(mealsList)
        })
    }

    private fun prepareRecyclerView() {
        categoryMealsAdapter = CategoryMealsAdapter()
        binding.rcViewMeals.apply {
            layoutManager = GridLayoutManager(context, 2 , GridLayoutManager.VERTICAL, false)
            adapter = categoryMealsAdapter
        }
    }
}