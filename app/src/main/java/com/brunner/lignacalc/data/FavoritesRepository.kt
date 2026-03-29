package com.brunner.lignacalc.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "lignacalc_prefs")

class FavoritesRepository(private val context: Context) {

    companion object {
        private val FAVORITES_KEY = stringSetPreferencesKey("favorite_tools")
    }

    val favorites: Flow<Set<String>> = context.dataStore.data.map { prefs ->
        prefs[FAVORITES_KEY] ?: emptySet()
    }

    suspend fun toggleFavorite(toolRoute: String) {
        context.dataStore.edit { prefs ->
            val current = prefs[FAVORITES_KEY] ?: emptySet()
            prefs[FAVORITES_KEY] = if (toolRoute in current) {
                current - toolRoute
            } else {
                current + toolRoute
            }
        }
    }

    suspend fun isFavorite(toolRoute: String): Boolean {
        var result = false
        context.dataStore.data.collect { prefs ->
            result = toolRoute in (prefs[FAVORITES_KEY] ?: emptySet())
        }
        return result
    }
}
