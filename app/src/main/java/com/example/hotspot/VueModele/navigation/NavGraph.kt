package com.example.hotspot.VueModele.navigation


import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.core.content.ContextCompat.startActivities
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.hotspot.Vue.DetailScreenActivity
import com.example.hotspot.Vue.Favoris.FavorisScreen
import com.example.hotspot.screen.HomeScreen
import com.example.hotspot.Vue.Profil.ProfilScreen
import com.example.hotspot.Vue.Profil.Salle
import com.example.hotspot.Vue.ProfilActivity
import com.example.hotspot.afterLogin

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
        composable(Screens.Detail.route) {
            startDetailScreenActivity(context)
        }
        composable(Screens.AfterLogin.route) {
            afterLogin()
        }
    }
}


fun startDetailScreenActivity(context: Context) {
    val intent = Intent(context, DetailScreenActivity::class.java)
    context.startActivity(intent)

}
