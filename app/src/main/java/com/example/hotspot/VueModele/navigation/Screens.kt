package com.example.hotspot.VueModele.navigation

sealed class Screens (var route : String) {
    object  Home : Screens("Home")
    object  Profil : Screens("Profil")
    object Favoris : Screens("Favoris")
}