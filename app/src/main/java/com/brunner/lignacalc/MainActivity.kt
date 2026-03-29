package com.brunner.lignacalc

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import com.brunner.lignacalc.data.PreferencesRepository
import com.brunner.lignacalc.ui.components.LanguageDialog
import com.brunner.lignacalc.ui.navigation.AppNavigation
import com.brunner.lignacalc.ui.theme.LignaCalcTheme
import kotlinx.coroutines.launch
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefsRepo = PreferencesRepository(this)

        enableEdgeToEdge()
        setContent {
            val scope = rememberCoroutineScope()
            var currentLocale by remember { mutableStateOf(Locale.getDefault().language) }
            var showLanguageDialog by remember { mutableStateOf(false) }

            // Gespeicherte Sprache laden
            LaunchedEffect(Unit) {
                prefsRepo.language.collect { savedLang ->
                    if (savedLang != null && savedLang != currentLocale) {
                        currentLocale = savedLang
                        applyLocale(savedLang)
                    }
                }
            }

            LignaCalcTheme {
                val navController = rememberNavController()
                AppNavigation(
                    navController = navController,
                    onOpenSettings = { showLanguageDialog = true }
                )

                if (showLanguageDialog) {
                    LanguageDialog(
                        currentLanguage = currentLocale,
                        onLanguageSelected = { langCode ->
                            scope.launch {
                                prefsRepo.setLanguage(langCode)
                            }
                            currentLocale = langCode
                            applyLocale(langCode)
                            showLanguageDialog = false
                        },
                        onDismiss = { showLanguageDialog = false }
                    )
                }
            }
        }
    }

    private fun applyLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration(resources.configuration)
        config.setLocale(locale)
        @Suppress("DEPRECATION")
        resources.updateConfiguration(config, resources.displayMetrics)
        recreate()
    }
}
