package com.example.hotspot.screen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
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
import coil.compose.rememberImagePainter
import com.example.hotspot.R
import com.example.hotspot.Vue.Profil.ProfilScreenOnPage
import com.example.hotspot.VueModele.navigation.Screens
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



/*

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




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(innerPadding: PaddingValues) {
    Column(modifier = Modifier.fillMaxSize()) {
        val imageResources = listOf(
            R.drawable.test,
            R.drawable.test2,
            R.drawable.test3
        )

        var currentImageIndex by remember { mutableStateOf(0) }
        LaunchedEffect(Unit) {
            while (true) {
                kotlinx.coroutines.delay(5000)
                currentImageIndex = (currentImageIndex + 1) % imageResources.size
            }
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
        ) {
            Image(
                painter = painterResource(id = imageResources[currentImageIndex]),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Text(text = "")
        }

        Text(
            text = "Popular Destination",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(16.dp)
        )

        val destinations = listOf(
            "Paris" to R.drawable.test,
            "Spain" to R.drawable.test
        )

        LazyRow(
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(destinations) { destination ->
                DestinationCard(destination.first, destination.second)
            }
        }
    }
}

@Composable
fun DestinationCard(name: String, imageRes: Int) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .clickable { /* Handle click */ },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                contentScale = ContentScale.Crop
            )
            Text(
                text = name,
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}




@Composable
fun HomeScreen(innerPadding: PaddingValues) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        // Image Slider
        val imageResources = listOf(
            R.drawable.test,
            R.drawable.test2,
            R.drawable.test3
        )

        var currentImageIndex by remember { mutableStateOf(0) }
        LaunchedEffect(Unit) {
            while (true) {
                kotlinx.coroutines.delay(3000)
                currentImageIndex = (currentImageIndex + 1) % imageResources.size
            }
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
        ) {
            Image(
                painter = painterResource(id = imageResources[currentImageIndex]),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp),
            ) {
                Text(

                    text = "Cape Town",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Extraordinary five-star outdoor activities",
                    fontSize = 16.sp,
                    color = Color.White,
                    modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
                )
            }

        }

        Spacer(modifier = Modifier.height(16.dp))

        // Popular Destinations
        Text(
            text = "Popular Categories",
            style = MaterialTheme.typography.labelLarge,
            color = Color.Black,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        val categories = listOf(
            "Reunion" to R.drawable.test,
            "fête" to R.drawable.test3,
            "Espace vert" to R.drawable.test2
        )

        LazyRow(
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(categories) { category ->
                CategoryCard(category.first, category.second)
            }
        }
    }
}

@Composable
fun CategoryCard(name: String, imageRes: Int) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .clickable { /* Handle click */ },
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Text(
                text = name,
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
        }
    }
}

 */
@Composable
fun HomeScreen(innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Image Slider
        val imageResources = listOf(
            R.drawable.test,
            R.drawable.test2,
            R.drawable.test3
        )

        var currentImageIndex by remember { mutableStateOf(0) }
        val transitionState = remember { MutableTransitionState(currentImageIndex) }
        transitionState.targetState = currentImageIndex

        LaunchedEffect(Unit) {
            while (true) {
                delay(5000)
                currentImageIndex = (currentImageIndex + 1) % imageResources.size
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(450.dp)
        ) {
            val animatedScale = remember { Animatable(1f) }

            LaunchedEffect(currentImageIndex) {
                animatedScale.snapTo(1f)
                animatedScale.animateTo(
                    targetValue = 1.2f,
                    animationSpec = tween(durationMillis = 10000, easing = LinearEasing)
                )
            }

            Image(
                painter = painterResource(id = imageResources[currentImageIndex]),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .scale(animatedScale.value),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp),
            ) {
                Text(
                    text = "Lubumbashi",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Trouvez les endroits chaud et calme de la ville",
                    fontSize = 16.sp,
                    color = Color.White,
                    modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Popular Destinations
        Text(
            text = "Popular Categories",
            style = MaterialTheme.typography.labelLarge,
            color = Color.Black,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        val categories = listOf(
            "Reunion" to R.drawable.test,
            "fête" to R.drawable.test3,
            "Espace vert" to R.drawable.test2
        )

        LazyRow(
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(categories) { category ->
                CategoryCard(category.first, category.second)
            }
        }
    }
}

@Composable
fun CategoryCard(name: String, imageRes: Int) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .clickable { /* Handle click */ },
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Text(
                text = name,
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
        }
    }
}