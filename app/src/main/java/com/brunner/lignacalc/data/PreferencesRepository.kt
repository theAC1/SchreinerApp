package com.brunner.lignacalc.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.settingsStore: DataStore<Preferences> by preferencesDataStore(name = "lignacalc_settings")

class PreferencesRepository(private val context: Context) {

    companion object {
        private val LANGUAGE_KEY = stringPreferencesKey("app_language")
    }

    val language: Flow<String?> = context.settingsStore.data.map { prefs ->
        prefs[LANGUAGE_KEY]
    }

    suspend fun setLanguage(languageCode: String) {
        context.settingsStore.edit { prefs ->
            prefs[LANGUAGE_KEY] = languageCode
        }
    }
}
