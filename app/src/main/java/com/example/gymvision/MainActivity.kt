package com.example.gymvision

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.gymvision.navigation.AppNavigation
import com.example.gymvision.navigation.Routes
import com.example.gymvision.ui.theme.CompactDimensions
import com.example.gymvision.ui.theme.GymvisionTheme
import com.example.gymvision.ui.theme.MediumDimensions

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val windowSizeClass = calculateWindowSizeClass(this)
            val dimensions = if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact) {
                CompactDimensions
            } else {
                MediumDimensions
            }
            GymvisionTheme(dimensions = dimensions) {
                GymvisionApp()
            }
        }
    }
}

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

val bottomNavItems = listOf(
    BottomNavItem("Accueil", Icons.Default.Home, Routes.ACCUEIL),
    BottomNavItem("Recherche", Icons.Default.Search, Routes.RECHERCHER),
    BottomNavItem("Séances", Icons.Default.FitnessCenter, Routes.SEANCES),
    BottomNavItem("Profil", Icons.Default.Person, Routes.PROFIL),
)

val routesAvecBottomBar = listOf(
    Routes.ACCUEIL, Routes.RECHERCHER, Routes.SEANCES, Routes.PROFIL
)

@Composable
fun GymvisionApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute in routesAvecBottomBar) {
                NavigationBar {
                    bottomNavItems.forEach { item ->
                        NavigationBarItem(
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label) },
                            selected = currentRoute == item.route,
                            onClick = {
                                if (item.route == Routes.ACCUEIL) {
                                    navController.popBackStack(Routes.ACCUEIL, false)
                                } else {
                                    navController.navigate(item.route) {
                                        popUpTo(Routes.ACCUEIL) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            AppNavigation(navController = navController)
        }
    }
}
