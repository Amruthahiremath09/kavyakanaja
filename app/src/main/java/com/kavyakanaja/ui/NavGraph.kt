package com.kavyakanaja.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kavyakanaja.ui.home.HomeScreen
import com.kavyakanaja.ui.home.MainViewModel
import com.kavyakanaja.ui.poem.PoemDetailScreen
import com.kavyakanaja.ui.poem.AllPoemsScreen
import com.kavyakanaja.ui.poets.PoetsScreen
import com.kavyakanaja.ui.poets.PoetDetailScreen
import com.kavyakanaja.ui.favourites.FavouritesScreen

object Routes {
    const val HOME        = "home"
    const val POEM_DETAIL = "poem_detail/{poemId}"
    const val ALL_POEMS   = "all_poems"
    const val POETS       = "poets"
    const val POET_DETAIL = "poet_detail/{poetId}"
    const val FAVOURITES  = "favourites"
}

@Composable
fun KavyaNavGraph(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    NavHost(navController = navController, startDestination = Routes.HOME) {

        composable(Routes.HOME) {
            HomeScreen(viewModel = viewModel, navController = navController)
        }

        composable(
            route = Routes.POEM_DETAIL,
            arguments = listOf(navArgument("poemId") { type = NavType.IntType })
        ) { backStack ->
            val poemId = backStack.arguments?.getInt("poemId") ?: return@composable
            val poem = viewModel.allPoems.firstOrNull { it.id == poemId } ?: return@composable
            PoemDetailScreen(
                poem = poem,
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.ALL_POEMS) {
            AllPoemsScreen(
                poems = viewModel.allPoems,
                onPoemClick = { poem ->
                    navController.navigate("poem_detail/${poem.id}")
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.POETS) {
            PoetsScreen(
                poets = viewModel.poets,
                onPoetClick = { poet ->
                    navController.navigate("poet_detail/${poet.id}")
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.POET_DETAIL,
            arguments = listOf(navArgument("poetId") { type = NavType.IntType })
        ) { backStack ->
            val poetId = backStack.arguments?.getInt("poetId") ?: return@composable
            val poet = viewModel.poets.firstOrNull { it.id == poetId } ?: return@composable
            val poems = viewModel.allPoems.filter { it.poetEn == poet.nameEn }
            PoetDetailScreen(
                poet = poet,
                poems = poems,
                onPoemClick = { poem -> navController.navigate("poem_detail/${poem.id}") },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.FAVOURITES) {
            FavouritesScreen(
                viewModel = viewModel,
                onPoemClick = { poem -> navController.navigate("poem_detail/${poem.id}") },
                onBack = { navController.popBackStack() }
            )
        }
    }
}
