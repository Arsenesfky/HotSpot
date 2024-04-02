package com.example.hotspot.Vue.Profil

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hotspot.R
import com.example.hotspot.Modele.Salles.ItemDetailsSalles
import com.example.hotspot.VueModele.navigation.Screens
import com.example.hotspot.ui.theme.HotSpotTheme
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilScreen(innerPadding: PaddingValues) {
    val userProfile = remember { getDummyUserProfile() } // Obtenez les données du profil de l'utilisateur

    Scaffold(
        modifier = Modifier.padding(bottom = 40.dp),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Action when FAB is clicked */ },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Default.ShoppingCart, contentDescription = "Modifier")
            }
        }
    ) { scaffoldPadding ->
        Surface(modifier = Modifier
            .padding(scaffoldPadding)
            .fillMaxSize()) {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ImageFonction()

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = userProfile.name,
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = userProfile.email,
                    style = TextStyle(fontSize = 16.sp)
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Salles déjà réservées")

                ProfilScreenOnPage()


            }
        }

    }
}

data class UserProfile(val name: String, val email: String)

fun getDummyUserProfile(): UserProfile {
    return UserProfile(
        name = "John Doe",
        email = "johndoe@example.com"
    )
}

@Composable
fun ImageFonction() {
    OutlinedImage(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = "Profile Picture",
        modifier = Modifier
            .size(120.dp)
            .clip(CircleShape),
        borderWidth = 2.dp,
        borderColor = Color.Black
    )

}

@Composable
fun OutlinedImage(
    painter: Painter,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    borderWidth: Dp = 2.dp,
    borderColor: Color = Color.Black
) {
    Box(
        modifier = modifier
            .border(borderWidth, borderColor, CircleShape)
    ) {
        Image(
            painter = painter,
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize()
        )
    }
}



@Composable
fun ProfilScreenContent(salles: List<Salle>, onItemClick: (Salle) -> Unit) {
    Surface(modifier = Modifier.padding(top = 50.dp)) {
        LazyColumn(
            contentPadding = PaddingValues(12.dp)
        ) {
            items(items = salles) { salles ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 5.dp)
                        .clickable { onItemClick(salles) }
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    shape = RoundedCornerShape(corner = CornerSize((16.dp))),
                    elevation = cardElevation(4.dp),

                ) {
                    CustomItemSalles(salle = salles) {
                        onItemClick(salles)
                    }
                }
            }
        }
    }
}

@Composable
fun ProfilScreenOnPage() {
    val sallesRepository = sallesRepository()
    val getAllDataSalles = sallesRepository.getAllDataSalles()
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.Profil.route
    ) {
        composable(Screens.Profil.route) {
            ProfilScreenContent(getAllDataSalles) { salle ->
                navController.navigate("itemDetails/${salle.id}")
            }
        }
        composable("itemDetails/{itemId}") { backStackEntry ->
            val itemId = backStackEntry.arguments?.getInt("itemId")
            itemId?.let { id ->
                val salle = getAllDataSalles.firstOrNull { it.id == id }
                salle?.let {
                    ItemDetailsSalles(item = it)
                }
            }
        }
    }
}





@Composable
fun CustomItemSalles(salle: Salle, onItemClick: () -> Unit) {
    Column {
        Row(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .padding(24.dp)
                .clickable(onClick = onItemClick),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Image(
                painter = painterResource(salle.image),
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
            Column {


                Text(
                    text = salle.name,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Voir Detail")
            }

        }

    }

}


data class Salle(
    val id: Int,
    val name: String,
    val location_: String,
    val places_assise: Int,
    val image: Int
)

class sallesRepository {
    fun getAllDataSalles(): List<Salle> {
        return listOf(
            Salle(
                id = 0,
                name = "Hypnose",
                location_ = "Av mama yemo",
                places_assise = 250,
                image = R.drawable.test
            ),
            Salle(
                id = 1,
                name = "la boheme",
                location_ = "Av Kasaie",
                places_assise = 150,
                image = R.drawable.test
            ),
            Salle(
                id = 2,
                name = "Hexagone",
                location_ = "Av Malela",
                places_assise = 450,
                image = R.drawable.test
            ),
        )
    }
}



@Preview(showBackground = true)
@Composable
fun ProfilScreenPreview() {
    HotSpotTheme {
        ProfilScreen(innerPadding = PaddingValues())
    }
    
}