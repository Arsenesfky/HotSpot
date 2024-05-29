package com.example.hotspot.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hotspot.R
import com.example.hotspot.Vue.Profil.ProfilScreenOnPage
import com.example.hotspot.VueModele.navigation.Screens
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(innerPadding: PaddingValues){
    val categories = listOf(
        "Fete",
        "Reunion",
        "Hotel",
        "Espace vert"
    )
    val scrollState = rememberScrollState()
    Scaffold(
        modifier = Modifier
            .padding(top = 30.dp)
    ){it
        Box(
            modifier = Modifier
                .fillMaxSize()
        ){
            Column(
                modifier = Modifier
                    .padding(all = 10.dp),
                verticalArrangement = Arrangement.Top,
            ) {
                Row(
                    modifier = Modifier
                        .height(height = 350.dp),
                    //.horizontalScroll(rememberScrollState())
                    //.padding(10.dp),
                    content = {
                        ImageBoxExample()
                        // Ajoutez d'autres images ici
                    }
                )
                Spacer(modifier = Modifier.height(height = 10.dp))
                Text(text = "Categorie")
                LazyRow() {
                    items(categories.chunked(2)) { rowCategories ->
                        Row(Modifier.fillMaxWidth()) {
                            rowCategories.forEach { category ->
                                CategoryItem(category)
                            }
                        }
                    }
                }
                //ProfilScreenOnPage()

            }
        }


    }
}







@Composable
fun ImageBoxExample() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.test),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentScale = ContentScale.Fit
        )
    }
}


@Composable
fun CategoryItem(category: String) {
    Box(
        modifier = Modifier
            //.weight(1f)
            .padding(8.dp)
            .height(100.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.test),
                contentDescription = null,
                modifier = Modifier.width(100.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = category,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}