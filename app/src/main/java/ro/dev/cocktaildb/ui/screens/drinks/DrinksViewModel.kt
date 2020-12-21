package ro.dev.cocktaildb.ui.screens.drinks

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import ro.dev.cocktaildb.data.datasource.RemoteDataSource
import ro.dev.cocktaildb.data.model.drink.DrinksListResponseModel
import ro.dev.cocktaildb.data.model.filter.CategoryDrink
import ro.dev.cocktaildb.data.remote.ApiClient
import ro.dev.cocktaildb.data.repository.Repository

class DrinksViewModel : ViewModel() {
    private val retrofitService = ApiClient.retrofitService
    private val remoteDataSource = RemoteDataSource(retrofitService)
    private val repository: Repository = Repository(remoteDataSource)

    suspend fun multipleDrinksRequest(categories: Array<CategoryDrink>): ArrayList<DrinksListResponseModel>{
        val array = ArrayList<DrinksListResponseModel>()
        withContext(CoroutineScope(Dispatchers.Default).coroutineContext) {
            categories.forEach {
                try {
                    val request = async { repository.getDrinks(it.strCategory) }
                    request.await().body()?.let {
                        array.add(it)
                    }
                } catch (exception: Exception) {
                    Log.e("TAG", exception.message.toString())
                }
            }
        }
        return array
    }
}