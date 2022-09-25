package com.app.foodhelper.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.foodhelper.activites.CategoryMealsActivity
import com.app.foodhelper.activites.MealActivity
import com.app.foodhelper.adapters.CategoriesAdapter
import com.app.foodhelper.adapters.MostPopularAdapter
import com.app.foodhelper.databinding.FragmentHomeBinding
import com.app.foodhelper.pojo.MealsByCategory
import com.app.foodhelper.pojo.Meal
import com.app.foodhelper.viewModel.HomeViewModel
import com.bumptech.glide.Glide

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeMvvm: HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popularItemsAdapter: MostPopularAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter

    companion object{
        const val MEAL_ID = "com.app.foodhelper.fragments.idMeal"
        const val MEAL_NAME = "com.app.foodhelper.fragments.nameMeal"
        const val MEAL_THUMB = "com.app.foodhelper.fragments.thumbMeal"
        const val CATEGORY_NAME = "com.app.foodhelper.fragments.categoryName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm = ViewModelProviders.of(this)[HomeViewModel::class.java]

        popularItemsAdapter = MostPopularAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparesPopularItemsRecyclerView()

        homeMvvm.getRandomMeal()
        observerRandomMeal()
        onRandomMealClick()

        homeMvvm.getPopularItems()
        observerPopularsItemsLiveData()
        onPopularItemClick()

        prepareCategoriesRecyclerView()
        homeMvvm.getCategories()
        observerCategoriesLiveData()
        onCategoryClick()
    }

    private fun onCategoryClick() {
        categoriesAdapter.onItemClick = { category ->
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME, category.strCategory)
            startActivity(intent)
        }
    }

    private fun prepareCategoriesRecyclerView() {
        categoriesAdapter = CategoriesAdapter()
        binding.rcViewCategories.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }
    }

    private fun observerCategoriesLiveData() {
        homeMvvm.observerCategoriesLiveData().observe(viewLifecycleOwner, Observer { categories ->
            categoriesAdapter.setCategoryList(categories)
        })
    }

    private fun onPopularItemClick() {
        popularItemsAdapter.onItemClick = { meal->
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, meal.idMeal)
            intent.putExtra(MEAL_NAME, meal.strMeal)
            intent.putExtra(MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun preparesPopularItemsRecyclerView() {
        binding.rcViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularItemsAdapter
        }
    }

    private fun observerPopularsItemsLiveData() {
        homeMvvm.observerPopularItemsLiveData().observe(viewLifecycleOwner
        ) { mealList ->
            popularItemsAdapter.setMeals(mealsByCategoryList = mealList as ArrayList<MealsByCategory>)
        }
    }

    private fun onRandomMealClick() {
        binding.randomMealCard.setOnClickListener{
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME, randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB, randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observerRandomMeal() {
        homeMvvm.observeRandomMealLiveData().observe(viewLifecycleOwner
        ) { meal ->
            Glide.with(this@HomeFragment)
                .load(meal!!.strMealThumb)
                .into(binding.imgRandomMeal)

            this.randomMeal = meal

        }
    }
}