package com.example.gymvision.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.gymvision.ui.screens.*
import com.example.gymvision.ui.viewmodel.DetailSeanceViewModel
import com.example.gymvision.ui.viewmodel.NouvelleSeanceViewModel
import com.google.firebase.auth.FirebaseAuth

object Routes {
    const val ACCUEIL = "accueil"
    const val RECHERCHER = "rechercher"
    const val RECHERCHER_SELECTION = "rechercher_selection"
    const val SEANCES = "seances"
    const val PROFIL = "profil"
    const val FAVORIS = "favoris"
    const val NOUVELLE_SEANCE = "nouvelle_seance"
    const val QR_SCANNER = "qr_scanner"
    const val DETAIL_SEANCE = "detail_seance/{seanceId}"
    const val DETAIL_EXERCICE = "detail_exercice/{exerciceId}"
    const val EXERCICE_COMMENCE = "exercice_commence/{exerciceId}"
    const val SEANCE_EN_COURS = "seance_en_cours/{seanceId}/{exerciceIndex}"
    const val SEANCE_DETAIL_EXERCICE = "seance_detail_exercice/{seanceId}/{exerciceIndex}"
    const val SEANCE_TERMINEE = "seance_terminee/{seanceId}"
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
                onNavigateToQrScanner = { navController.navigate(Routes.QR_SCANNER) },
                onNavigateToProfil = { navController.navigate(Routes.PROFIL) }
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

        composable(Routes.RECHERCHER_SELECTION) {
            RechercherScreen(
                selectionMode = true,
                onNavigateToDetailExercice = {},
                onNavigateToAccueil = {},
                onExerciceSelectionne = { id ->
                    navController.previousBackStackEntry?.savedStateHandle?.set("exercice_id", id)
                    navController.popBackStack()
                }
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
            ProfilScreen(onNavigateToAccueil = { navController.toAccueil() })
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

        composable(Routes.NOUVELLE_SEANCE) { backStackEntry ->
            val viewModel: NouvelleSeanceViewModel = viewModel()

            // Résultat retourné par RechercherSelection
            val selectedId by backStackEntry.savedStateHandle
                .getStateFlow("exercice_id", -1)
                .collectAsState()

            LaunchedEffect(selectedId) {
                if (selectedId != -1) {
                    viewModel.ajouterExercice(selectedId)
                    backStackEntry.savedStateHandle["exercice_id"] = -1
                }
            }

            NouvelleSeanceScreen(
                viewModel = viewModel,
                onRetour = { navController.popBackStack() },
                onAjouterExercice = { navController.navigate(Routes.RECHERCHER_SELECTION) }
            )
        }

        composable(
            route = Routes.DETAIL_SEANCE,
            arguments = listOf(navArgument("seanceId") { type = NavType.IntType })
        ) { backStackEntry ->
            val seanceId = backStackEntry.arguments?.getInt("seanceId") ?: 0
            val detailViewModel: DetailSeanceViewModel = viewModel(backStackEntry)

            DetailSeanceScreen(
                seanceId = seanceId,
                onRetour = { navController.popBackStack() },
                onNavigateToDetailExercice = { id -> navController.navigate("detail_exercice/$id") },
                onCommencerSeance = { id -> navController.navigate("seance_detail_exercice/$id/0") },
                detailViewModel = detailViewModel
            )
        }

        // Flux séance : page détail de l'exercice avec contexte séance
        composable(
            route = Routes.SEANCE_DETAIL_EXERCICE,
            arguments = listOf(
                navArgument("seanceId") { type = NavType.IntType },
                navArgument("exerciceIndex") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val seanceId = backStackEntry.arguments?.getInt("seanceId") ?: 1
            val exerciceIndex = backStackEntry.arguments?.getInt("exerciceIndex") ?: 0

            // Récupère le ViewModel de DetailSeance (encore dans la back stack)
            val detailSeanceEntry = remember(navController.currentBackStackEntry) {
                try { navController.getBackStackEntry(Routes.DETAIL_SEANCE) } catch (e: Exception) { null }
            }
            val detailViewModel: DetailSeanceViewModel? = detailSeanceEntry?.let { viewModel(it) }
            val exerciceIds by detailViewModel?.exercices?.collectAsState() ?: remember { mutableStateOf(emptyList()) }
            val exerciceId = exerciceIds.getOrNull(exerciceIndex)?.exerciceId

            if (exerciceId != null) {
                DetailExerciceScreen(
                    exerciceId = exerciceId,
                    onRetour = { navController.popBackStack() },
                    onCommencer = { _ -> navController.navigate("seance_en_cours/$seanceId/$exerciceIndex") }
                )
            }
        }

        // Flux séance : écran exercice commencé
        composable(
            route = Routes.SEANCE_EN_COURS,
            arguments = listOf(
                navArgument("seanceId") { type = NavType.IntType },
                navArgument("exerciceIndex") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val seanceId = backStackEntry.arguments?.getInt("seanceId") ?: 1
            val exerciceIndex = backStackEntry.arguments?.getInt("exerciceIndex") ?: 0

            val detailSeanceEntry = remember(navController.currentBackStackEntry) {
                try { navController.getBackStackEntry(Routes.DETAIL_SEANCE) } catch (e: Exception) { null }
            }
            val detailViewModel: DetailSeanceViewModel? = detailSeanceEntry?.let { viewModel(it) }
            val exerciceIds by detailViewModel?.exercices?.collectAsState() ?: remember { mutableStateOf(emptyList()) }

            val exerciceId = exerciceIds.getOrNull(exerciceIndex)?.exerciceId ?: 1
            val isLast = exerciceIndex == exerciceIds.size - 1

            ExerciceCommenceScreen(
                exerciceId = exerciceId,
                onRetour = { navController.popBackStack() },
                onSuivant = {
                    if (isLast) {
                        navController.navigate("seance_terminee/$seanceId") {
                            popUpTo(Routes.DETAIL_SEANCE) { inclusive = false }
                        }
                    } else {
                        navController.navigate("seance_detail_exercice/$seanceId/${exerciceIndex + 1}")
                    }
                },
                dernierExercice = isLast
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

        composable(
            route = Routes.SEANCE_TERMINEE,
            arguments = listOf(navArgument("seanceId") { type = NavType.IntType })
        ) {
            SeanceTermineeScreen(onRetour = { navController.popBackStack() })
        }
    }
}
