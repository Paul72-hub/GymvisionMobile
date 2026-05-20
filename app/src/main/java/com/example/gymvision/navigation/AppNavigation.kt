package com.example.gymvision.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.gymvision.ui.screens.*

object Routes {
    const val ACCUEIL = "accueil"
    const val RECHERCHER = "rechercher"
    const val SEANCES = "seances"
    const val PROFIL = "profil"
    const val FAVORIS = "favoris"
    const val NOUVELLE_SEANCE = "nouvelle_seance"
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
                onNavigateToFavoris = { navController.navigate(Routes.FAVORIS) }
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
            FavorisScreen(
                onRetour = { navController.popBackStack() },
                onNavigateToDetailExercice = { id -> navController.navigate("detail_exercice/$id") },
                onNavigateToDetailSeance = { id -> navController.navigate("detail_seance/$id") }
            )
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
