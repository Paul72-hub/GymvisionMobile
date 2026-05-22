package com.example.gymvision.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.gymvision.ui.screens.*
import com.google.firebase.auth.FirebaseAuth

object Routes {
    const val ACCUEIL = "accueil"
    const val RECHERCHER = "rechercher"
    const val SEANCES = "seances"
    const val PROFIL = "profil"
    const val FAVORIS = "favoris"
    const val NOUVELLE_SEANCE = "nouvelle_seance"
    const val QR_SCANNER = "qr_scanner"
    const val DETAIL_SEANCE = "detail_seance/{seanceId}"
    const val DETAIL_EXERCICE = "detail_exercice/{exerciceId}"
    const val EXERCICE_COMMENCE = "exercice_commence/{exerciceId}"
}

private fun NavHostController.toAccueil() {
    navigate(Routes.ACCUEIL) {
        popUpTo(Routes.ACCUEIL) { inclusive = false }
        launchSingleTop = true
    }
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.ACCUEIL) {

        composable(Routes.ACCUEIL) {
            AccueilScreen(
                onNavigateToSeances = { navController.navigate(Routes.SEANCES) },
                onNavigateToRechercher = { navController.navigate(Routes.RECHERCHER) },
                onNavigateToFavoris = { navController.navigate(Routes.FAVORIS) },
                onNavigateToQrScanner = { navController.navigate(Routes.QR_SCANNER) }
            )
        }

        composable(Routes.QR_SCANNER) {
            QrScannerScreen(
                onQrCodeDetected = { id -> navController.navigate("detail_exercice/$id") },
                onRetour = { navController.popBackStack() }
            )
        }

        composable(Routes.RECHERCHER) {
            RechercherScreen(
                onNavigateToDetailExercice = { id -> navController.navigate("detail_exercice/$id") },
                onNavigateToAccueil = { navController.toAccueil() }
            )
        }

        composable(Routes.SEANCES) {
            SeancesScreen(
                onNavigateToDetailSeance = { id -> navController.navigate("detail_seance/$id") },
                onNavigateToNouvelleSeance = { navController.navigate(Routes.NOUVELLE_SEANCE) },
                onRetour = { navController.popBackStack() }
            )
        }

        composable(Routes.PROFIL) {
            ProfilScreen(
                onNavigateToAccueil = { navController.toAccueil() }
            )
        }

        composable(Routes.FAVORIS) {
            val auth = remember { FirebaseAuth.getInstance() }
            var isLoggedIn by remember { mutableStateOf(auth.currentUser != null) }

            if (!isLoggedIn) {
                LoginScreen(onLoginSuccess = { isLoggedIn = true })
            } else {
                FavorisScreen(
                    onRetour = { navController.popBackStack() },
                    onNavigateToDetailExercice = { id -> navController.navigate("detail_exercice/$id") },
                    onNavigateToDetailSeance = { id -> navController.navigate("detail_seance/$id") }
                )
            }
        }

        composable(Routes.NOUVELLE_SEANCE) {
            NouvelleSeanceScreen(
                onRetour = { navController.popBackStack() },
                onNavigateToRechercher = { navController.navigate(Routes.RECHERCHER) }
            )
        }

        composable(
            route = Routes.DETAIL_SEANCE,
            arguments = listOf(navArgument("seanceId") { type = NavType.IntType })
        ) { backStackEntry ->
            val seanceId = backStackEntry.arguments?.getInt("seanceId") ?: 0
            DetailSeanceScreen(
                seanceId = seanceId,
                onRetour = { navController.popBackStack() },
                onNavigateToDetailExercice = { id -> navController.navigate("detail_exercice/$id") }
            )
        }

        composable(
            route = Routes.DETAIL_EXERCICE,
            arguments = listOf(navArgument("exerciceId") { type = NavType.IntType })
        ) { backStackEntry ->
            val exerciceId = backStackEntry.arguments?.getInt("exerciceId") ?: 0
            DetailExerciceScreen(
                exerciceId = exerciceId,
                onRetour = { navController.popBackStack() },
                onCommencer = { id -> navController.navigate("exercice_commence/$id") }
            )
        }

        composable(
            route = Routes.EXERCICE_COMMENCE,
            arguments = listOf(navArgument("exerciceId") { type = NavType.IntType })
        ) { backStackEntry ->
            val exerciceId = backStackEntry.arguments?.getInt("exerciceId") ?: 0
            ExerciceCommenceScreen(
                exerciceId = exerciceId,
                onRetour = { navController.popBackStack() }
            )
        }
    }
}
