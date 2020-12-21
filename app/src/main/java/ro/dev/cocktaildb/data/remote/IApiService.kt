package ro.dev.cocktaildb.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ro.dev.cocktaildb.data.model.drink.DrinksListResponseModel
import ro.dev.cocktaildb.data.model.filter.CategoriesResponse

/*
https://www.thecocktaildb.com/api/json/v1/1/list.php?c=list
https://www.thecocktaildb.com/api/json/v1/1/filter.php?c=Ordinary%20Drink
 */

interface IApiService {
    @GET(value = "list.php")
    suspend fun getFilters(
        @Query("c") list: String = "list"
    ): Response<CategoriesResponse>

    @GET(value = "filter.php")
    suspend fun getDrinksList(
        @Query("c") filterName: String
    ): Response<DrinksListResponseModel>
}