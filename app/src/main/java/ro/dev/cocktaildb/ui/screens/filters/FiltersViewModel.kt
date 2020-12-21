package ro.dev.cocktaildb.ui.screens.filters

import androidx.lifecycle.ViewModel
import ro.dev.cocktaildb.data.datasource.RemoteDataSource
import ro.dev.cocktaildb.data.remote.ApiClient
import ro.dev.cocktaildb.data.repository.Repository

class FiltersViewModel : ViewModel() {
    private val retrofitService = ApiClient.retrofitService
    private val remoteDataSource = RemoteDataSource(retrofitService)
    private val repository: Repository = Repository(remoteDataSource)

    val filters = repository.getFilters()
}