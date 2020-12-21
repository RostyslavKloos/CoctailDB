package ro.dev.cocktaildb.utills

import android.content.Context
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class SharedManager (context: Context) {
    private val dataStore = context.createDataStore(name = "filterStore")

    companion object {
        val FILTER_STATE_KEY = preferencesKey<Map<String, Boolean>>("FILTER_STATE")
    }

    suspend fun storeFilterState(item: Map<String, Boolean>) {
        dataStore.edit {
            it[FILTER_STATE_KEY] = item
        }
    }

    val filterStateFlow: Flow<Map<String, Boolean>> = dataStore.data.map {
        it[FILTER_STATE_KEY] ?: mapOf()
    }

}