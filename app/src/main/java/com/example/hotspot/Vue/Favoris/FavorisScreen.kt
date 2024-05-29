package com.example.hotspot.Vue.Favoris

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hotspot.Vue.Profil.ProfilScreenOnPage
import com.example.hotspot.Vue.Profil.SallesListe
import com.example.hotspot.ui.theme.HotSpotTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavorisScreen(innerPadding: PaddingValues) {
    Scaffold(
        topBar = {
            Surface(
                modifier = Modifier
                    .height(100.dp)
                    //.fillMaxWidth()
                    .padding(top = 50.dp, start = 40.dp, end = 40.dp),
                shape = RoundedCornerShape(50),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                        //.padding(top = 40.dp),
                    //contentAlignment = androidx.compose.ui.Alignment.TopCenter,

                ) {
                    TextField(
                        singleLine = true,
                        modifier = Modifier.fillMaxSize(),
                        value = "",
                        onValueChange = { /* Mettre à jour la valeur de la recherche */ },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null
                            )
                        },
                        placeholder = { Text(text = "Recherche") },

                        // Autres propriétés de TextField selon vos besoins
                    )
                }
            }

        }
    ) {  it
        Box (
            modifier = Modifier.fillMaxSize()
        ){
            Column (
                modifier = Modifier.padding(top = 40.dp)
            ){
                //ProfilScreenOnPage()
                SallesListe()
            }
        }

        // Contenu de l'écran des favoris ici
    }
}

@Preview(showBackground = true)
@Composable
fun FavorisScreenPreview() {
    HotSpotTheme {
        FavorisScreen(innerPadding = PaddingValues())
    }
}