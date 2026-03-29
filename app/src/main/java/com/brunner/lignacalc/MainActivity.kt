package com.brunner.lignacalc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.brunner.lignacalc.ui.navigation.AppNavigation
import com.brunner.lignacalc.ui.theme.LignaCalcTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LignaCalcTheme {
                val navController = rememberNavController()
                AppNavigation(navController = navController)
            }
        }
    }
}
