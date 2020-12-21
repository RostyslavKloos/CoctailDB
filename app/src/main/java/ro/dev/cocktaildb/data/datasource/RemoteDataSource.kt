package ro.dev.cocktaildb.data.datasource

import ro.dev.cocktaildb.data.remote.IApiService

class RemoteDataSource(private val currentWeatherDao: IApiService): BaseDataSource() {
    suspend fun getFilters() = getResult { currentWeatherDao.getFilters()}
    suspend fun getDrinks(categoryName: String) = currentWeatherDao.getDrinksList(categoryName)
}