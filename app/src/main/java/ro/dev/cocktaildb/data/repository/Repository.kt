package ro.dev.cocktaildb.data.repository

import ro.dev.cocktaildb.data.datasource.RemoteDataSource
import ro.dev.cocktaildb.utills.performGetOperation

class Repository(private val remoteDataSource: RemoteDataSource) {
    suspend fun getDrinks(categoryName: String) = remoteDataSource.getDrinks(categoryName)
    fun getFilters() = performGetOperation { remoteDataSource.getFilters() }
}