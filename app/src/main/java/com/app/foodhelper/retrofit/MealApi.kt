package com.app.foodhelper.retrofit

import com.app.foodhelper.pojo.CategoryList
import com.app.foodhelper.pojo.MealsByCategoryList
import com.app.foodhelper.pojo.MealList
import com.app.foodhelper.pojo.MealsByCategory
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    fun getRandomMeal(): Call<MealList>

    @GET("lookup.php?")
    fun getMealDetails(@Query("i") id:String): Call<MealList>

    @GET("filter.php?")
    fun getPopularItems(@Query("c") categoryName:String) :Call<MealsByCategoryList>

    @GET("categories.php")
    fun getCategories(): Call<CategoryList>

    @GET("filter.php")
    fun getMealsByCategory(@Query("c") categoryName: String) : Call<MealsByCategoryList>
}