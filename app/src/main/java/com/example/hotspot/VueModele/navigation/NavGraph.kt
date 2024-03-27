package com.example.hotspot.VueModele.navigation


import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.hotspot.Vue.Favoris.FavorisScreen
import com.example.hotspot.screen.HomeScreen
import com.example.hotspot.Vue.Profil.ProfilScreen
import com.example.hotspot.Vue.ProfilActivity

@Composable
fun SetUpNavGraph(
    context: Context,
    navController: NavHostController,
    innerPadding: PaddingValues
) {
    NavHost(navController = navController,
        startDestination = Screens.Home.route ){
        composable(Screens.Home.route){
            HomeScreen(innerPadding = innerPadding)
        }
        composable(Screens.Profil.route){
            ProfilScreen(innerPadding = innerPadding)
        }
        composable(Screens.Favoris.route){
            FavorisScreen(innerPadding = innerPadding)
        }
    }
}


fun startProfilActivity(context: Context) {
    val intent = Intent(context, ProfilActivity::class.java)
    context.startActivity(intent)
}

