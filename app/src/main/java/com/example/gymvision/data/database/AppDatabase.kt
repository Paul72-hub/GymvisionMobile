package com.example.gymvision.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.gymvision.data.model.Exercice
import com.example.gymvision.data.model.Seance
import com.example.gymvision.data.model.SeanceExercice
import com.example.gymvision.data.model.UserFavori
import com.example.gymvision.data.model.UserFavoriSeance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [Exercice::class, Seance::class, SeanceExercice::class, UserFavori::class, UserFavoriSeance::class],
    version = 4
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun exerciceDao(): ExerciceDao
    abstract fun seanceDao(): SeanceDao
    abstract fun userFavoriDao(): UserFavoriDao
    abstract fun userFavoriSeanceDao(): UserFavoriSeanceDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "gymvision_db"
                )
                .fallbackToDestructiveMigration()
                .build()
                .also { db ->
                    INSTANCE = db
                    CoroutineScope(Dispatchers.IO).launch {
                        if (db.exerciceDao().count() == 0) prePopuler(db)
                    }
                }
            }
        }

        private suspend fun prePopuler(db: AppDatabase) {
            if (db.seanceDao().count() > 0) return

            val seanceDao = db.seanceDao()
            val exoDao = db.exerciceDao()

            // Insère les séances et récupère leurs IDs
            val seanceIds = listOf(
                Seance(nom = "Dos & Biceps",   dureeMinutes = 50, niveau = "Débutant"),
                Seance(nom = "Jambes",         dureeMinutes = 45, niveau = "Débutant"),
                Seance(nom = "Pectoraux",      dureeMinutes = 50, niveau = "Intermédiaire"),
                Seance(nom = "Épaule & Abdos", dureeMinutes = 40, niveau = "Débutant"),
            ).map { seanceDao.insert(it).toInt() }

            // Exercices groupés par séance (même ordre que les IDs ci-dessus)
            val exercicesParSeance = listOf(
                // Dos & Biceps
                listOf(
                    Exercice(nom = "Tirage poulie haute", description = "Exercice fondamental pour le dos.\nIdéal pour développer la largeur et apprendre le recrutement musculaire.", muscle = "Dos", niveau = "Débutant", conseils = "Garder le dos droit;Gainer les abdos", youtubeVideoId = "BHQ692ZKMRI"),
                    Exercice(nom = "Rowing machine", description = "Exercice de tirage horizontal pour le dos et les biceps.\nPermet un bon recrutement des muscles du milieu du dos.", muscle = "Dos", niveau = "Débutant", conseils = "Garder le dos droit;Ne pas bloquer les épaules;Contrôler la descente", youtubeVideoId = "xkaX66b1oA0"),
                    Exercice(nom = "Curl haltères", description = "Exercice d'isolation pour les biceps.\nEffectuer une rotation du poignet en montant pour maximiser le travail.", muscle = "Biceps", niveau = "Débutant", conseils = "Coudes fixes le long du corps;Ne pas balancer le buste", youtubeVideoId = "bxyocI5XBoA"),
                    Exercice(nom = "Curl pupitre", description = "Isolation complète des biceps grâce au pupitre.\nElimine toute triche et cible directement le biceps.", muscle = "Biceps", niveau = "Débutant", conseils = "Appuyer le bras sur le pupitre;Descendre complètement", youtubeVideoId = "P13VQDU1q9c"),
                ),
                // Jambes
                listOf(
                    Exercice(nom = "Squat barre", description = "Exercice roi du bas du corps.\nSollicite quadriceps, ischio-jambiers, fessiers et dos.", muscle = "Jambes", niveau = "Débutant", conseils = "Pieds à largeur d'épaules;Genoux dans l'axe des pieds;Dos droit", youtubeVideoId = "WVkMZOPtid4"),
                    Exercice(nom = "Leg press", description = "Exercice de poussée des jambes à la machine.\nPermet de charger lourd en toute sécurité.", muscle = "Jambes", niveau = "Débutant", conseils = "Pieds à mi-hauteur de la plateforme;Ne pas verrouiller les genoux;Dos plaqué sur le dossier", youtubeVideoId = "K5n2vg3oZa4"),
                    Exercice(nom = "Fentes marchées", description = "Exercice unilatéral pour les jambes et les fessiers.\nCorrige les déséquilibres musculaires.", muscle = "Jambes", niveau = "Débutant", conseils = "Grand pas en avant;Genou arrière proche du sol;Buste droit", youtubeVideoId = "HH9FQpjXw7A"),
                    Exercice(nom = "Leg curl", description = "Exercice d'isolation pour les ischio-jambiers.\nIndispensable en fin de séance jambes.", muscle = "Jambes", niveau = "Débutant", conseils = "Ne pas décoller les hanches;Contrôler la remontée;Amplitude complète", youtubeVideoId = "l9QbFtlkjCw"),
                ),
                // Pectoraux
                listOf(
                    Exercice(nom = "Développé couché", description = "Exercice de base pour les pectoraux.\nSollicite également les épaules et les triceps.", muscle = "Pectoraux", niveau = "Intermédiaire", conseils = "Barre à hauteur des tétons;Coudes à 45° du corps;Pieds à plat au sol", youtubeVideoId = "feWp7jZopI8"),
                    Exercice(nom = "Écarté haltères", description = "Exercice d'isolation pour les pectoraux.\nExcellent étirement en bas du mouvement.", muscle = "Pectoraux", niveau = "Intermédiaire", conseils = "Légère flexion des coudes;Descendre jusqu'au niveau des épaules;Contrôler la montée", youtubeVideoId = "lynnbMeaMcE"),
                    Exercice(nom = "Pompes", description = "Exercice au poids du corps pour les pectoraux, épaules et triceps.", muscle = "Pectoraux", niveau = "Débutant", conseils = "Corps en ligne droite;Coudes à 45°;Descendre la poitrine jusqu'au sol", youtubeVideoId = "-lkv4E8Aymk"),
                    Exercice(nom = "Croisé poulie", description = "Exercice de finition pour les pectoraux à la poulie.\nTension constante tout au long du mouvement.", muscle = "Pectoraux", niveau = "Intermédiaire", conseils = "Légère inclinaison du buste;Croiser les mains en fin de mouvement;Ne pas bloquer les coudes", youtubeVideoId = "6b6jE69AkIs"),
                ),
                // Épaule & Abdos
                listOf(
                    Exercice(nom = "Élévation latérale", description = "Exercice d'isolation pour le faisceau moyen des deltoïdes.\nEssentiel pour élargir les épaules.", muscle = "Épaules", niveau = "Débutant", conseils = "Légère flexion des coudes;Monter jusqu'à l'horizontale;Ne pas balancer le corps", youtubeVideoId = "7L8KCZMj6_g"),
                    Exercice(nom = "Développé militaire", description = "Exercice polyarticulaire pour les épaules.\nSollicite les trois faisceaux des deltoïdes.", muscle = "Épaules", niveau = "Débutant", conseils = "Gainer les abdos;Barre devant le visage;Pousser sans cambrer le dos", youtubeVideoId = "t7ZU2bG49ZE"),
                    Exercice(nom = "Crunch", description = "Exercice de base pour les abdominaux.\nCible le droit de l'abdomen.", muscle = "Abdos", niveau = "Débutant", conseils = "Mains derrière la nuque sans tirer;Décoller uniquement les épaules;Expirer à la montée", youtubeVideoId = "7ZwodZ1B0s4"),
                    Exercice(nom = "Gainage", description = "Exercice isométrique pour les abdominaux profonds.\nRenforce le core et protège le bas du dos.", muscle = "Abdos", niveau = "Débutant", conseils = "Corps aligné tête-pieds;Ne pas laisser les hanches tomber;Respirer régulièrement", youtubeVideoId = "uXSljAP_kJU"),
                ),
            )

            // Insère exercices + crée les liens SeanceExercice
            exercicesParSeance.forEachIndexed { seanceIndex, exercices ->
                exercices.forEachIndexed { ordre, exercice ->
                    val exerciceId = exoDao.insert(exercice).toInt()
                    seanceDao.insertSeanceExercice(
                        SeanceExercice(
                            seanceId = seanceIds[seanceIndex],
                            exerciceId = exerciceId,
                            repetitions = 12,
                            series = 3,
                            ordre = ordre
                        )
                    )
                }
            }
        }
    }
}
