package com.example.hotspot.Modele.Salles

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.hotspot.Vue.Profil.Salle

@Composable
fun ItemDetailsSalles(item: Salle) {
    Surface(modifier = Modifier.padding(top = 70.dp).fillMaxSize()){
        Text(text = "Page de : ${item.name} ${item.location_} id : ${item.id}")

    }
    // Affiche les détails de l'élément spécifié
}