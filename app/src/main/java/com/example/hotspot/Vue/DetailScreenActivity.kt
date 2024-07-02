package com.example.hotspot.Vue

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.hotspot.MainActivity
import com.example.hotspot.Vue.Profil.Salle
import com.example.hotspot.Vue.ui.theme.HotSpotTheme
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val receivedNomDesalle = intent.getStringExtra("nomDeSalle").toString()
            HotSpotTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SalleDetail(onBackPressed = {
                        /*val navigate = Intent(this@DetailScreenActivity,MainActivity::class.java)
                        startActivities(arrayOf(navigate))*/
                        finish()
                    }, nom = receivedNomDesalle)
                }
            }

        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SalleDetail(onBackPressed: () -> Unit,nom : String) {

    BackHandler(onBack = onBackPressed)
    val comments = remember { mutableStateListOf<CommentData>() }
    val context = LocalContext.current
    val imageUrl = remember { mutableStateOf<String?>(null) }




    val (description, setDescription) = remember { mutableStateOf<String?>(null) }
    val (services, setServices) = remember { mutableStateOf<String?>(null) }
    val (latitude, setLatitude) = remember { mutableStateOf<Int?>(null) }
    val (longitude, setLongitude) = remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(nom) {
        getImageUrl(
            nom = nom,
            onSuccess = { url ->
                imageUrl.value = url
            },
            onFailure = {
                // Gérer les erreurs de récupération de l'URL de l'image
            }
        )
        getDescription(nom,
            onSuccess = { desc ->
                setDescription(desc)
            },
            onFailure = {
                // Gérer l'échec de la récupération de la description
            }
        )

        getServices(nom,
            onSuccess = { serv ->
                setServices(serv)
            },
            onFailure = {
                // Gérer l'échec de la récupération des services
            }
        )

        getCoordinates(nom,
            onSuccess = { lat, long ->
                setLatitude(lat)
                setLongitude(long)
            },
            onFailure = {
                // Gérer l'échec de la récupération des coordonnées
            }
        )
        getComments(
            nom = nom,
            onSuccess = { commentsList ->
                comments.addAll(commentsList)
            },
            onFailure = {
                // Gérer les échecs
            }
        )

    }

    Scaffold(
        bottomBar = {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp),
                    ) {
                        AddComments(onCommentAdded = {}, salleId = nom, context = context)

                    }
        },
        topBar = {
            TopAppBar(
                actions = {
                          IconButton(onClick = {
                              openGoogleMaps(
                                  context = context ,
                                  latitude = longitude, latitude)
                          }) {
                              Icon(Icons.Filled.LocationOn, contentDescription = "Show on Maps")
                          }
                },
                title = { Text(text = nom.toUpperCase()) },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {it
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {



            imageUrl.value?.let { url ->
                SalleImage(url)
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Hôtel Example",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (description != null) {
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Services disponibles :",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (services != null) {
                Text(
                    text = services,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Box(
                modifier = Modifier
                    .background(
                        color = Color.LightGray
                    )
                    .height(250.dp)
                    .padding(10.dp)
                    .fillMaxWidth(),
            ) {
                CommentSection(comments = comments)
            }




        }
    }

}

@Composable
fun SalleImage(url: String) {
    val painter = rememberImagePainter(url)
    Image(
        painter = painter,
        contentDescription = "Hotel Image",
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth(),
        contentScale = ContentScale.Crop
    )
}


fun ImageDonwload(nom: String){

}

fun getImageUrl(nom: String, onSuccess: (String) -> Unit, onFailure: () -> Unit) {
    val firestore = Firebase.firestore
    val sallesCollection = firestore.collection("salles")

    // Recherche du document avec l'ID égal au nom donné
    sallesCollection.document(nom)
        .get()
        .addOnSuccessListener { document ->
            if (document != null && document.exists()) {
                val imageUrl = document.getString("image")
                if (!imageUrl.isNullOrBlank()) {
                    onSuccess(imageUrl)
                } else {
                    onFailure()
                }
            } else {
                onFailure()
            }
        }
        .addOnFailureListener {
            onFailure()
        }
}

fun getDescription(nom: String, onSuccess: (String) -> Unit, onFailure: () -> Unit) {
    val firestore = Firebase.firestore
    val sallesCollection = firestore.collection("salles")

    // Recherche du document avec l'ID égal au nom donné
    sallesCollection.document(nom)
        .get()
        .addOnSuccessListener { document ->
            if (document != null && document.exists()) {
                val description = document.getString("description")
                if (!description.isNullOrBlank()) {
                    onSuccess(description)
                } else {
                    onFailure()
                }
            } else {
                onFailure()
            }
        }
        .addOnFailureListener {
            onFailure()
        }
}

fun getServices(nom: String, onSuccess: (String) -> Unit, onFailure: () -> Unit) {
    val firestore = Firebase.firestore
    val sallesCollection = firestore.collection("salles")

    // Recherche du document avec l'ID égal au nom donné
    sallesCollection.document(nom)
        .get()
        .addOnSuccessListener { document ->
            if (document != null && document.exists()) {
                val services = document.getString("services")
                if (!services.isNullOrBlank()) {
                    onSuccess(services)
                } else {
                    onFailure()
                }
            } else {
                onFailure()
            }
        }
        .addOnFailureListener {
            onFailure()
        }
}

fun getCoordinates(nom: String, onSuccess: (Int, Int) -> Unit, onFailure: () -> Unit) {
    val firestore = Firebase.firestore
    val sallesCollection = firestore.collection("salles")

    // Recherche du document avec l'ID égal au nom donné
    sallesCollection.document(nom)
        .get()
        .addOnSuccessListener { document ->
            if (document != null && document.exists()) {
                val latitude = document.getLong("latitude")?.toInt()
                val longitude = document.getLong("longitude")?.toInt()
                if (latitude != null && longitude != null) {
                    onSuccess(latitude, longitude)
                } else {
                    onFailure()
                }
            } else {
                onFailure()
            }
        }
        .addOnFailureListener {
            onFailure()
        }
}



fun getComments(
    nom: String,
    onSuccess: (List<CommentData>) -> Unit,
    onFailure: () -> Unit
) {
    val firestore = Firebase.firestore
    val sallesCollection = firestore.collection("salles")

    // Recherche du document avec l'ID égal au nom donné
    sallesCollection.document(nom)
        .get()
        .addOnSuccessListener { document ->
            if (document != null && document.exists()) {
                val commentsList = mutableListOf<CommentData>()
                val commentsArray = document.get("commentaire") as? List<Map<String, String>>
                commentsArray?.forEach { commentMap ->
                    val auteur = commentMap["auteur"] ?: ""
                    val texte = commentMap["commentaire"] ?: ""
                    val date = commentMap["date"] ?: ""
                    commentsList.add(CommentData(auteur, texte, date))
                }
                onSuccess(commentsList)
            } else {
                onFailure()
            }
        }
        .addOnFailureListener {
            onFailure()
        }
}

data class CommentData(
    val auteur: String,
    val texte: String,
    val date: String
)










private fun openGoogleMaps(context: Context, latitude: Int?, longitude: Int?) {
    val uri = Uri.parse("geo:$latitude,$longitude")
    val intent = Intent(Intent.ACTION_VIEW, uri)
    intent.setPackage("com.google.android.apps.maps")
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    context.startActivity(intent)
}

fun startMapsScreenActivity(context: Context) {
    val intent = Intent(context, MapsActivity::class.java)
    context.startActivity(intent)

}

data class Comment(
    val author : String,
    val content : String
)

@Composable
fun CommentSection(
    comments: List<CommentData>
) {
    Column {
        Text(text = "Commentaires", modifier = Modifier.padding(16.dp))
        LazyColumn {
            items(comments) { comment ->
                Text(
                    text = "${comment.auteur}: ${comment.texte}",
                    modifier = Modifier.padding(8.dp)
                )
                Text(
                    text = "Date: ${comment.date}",
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}
/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddComments(
    nom: String,
    onCommentAdded : (Comment) -> Unit
){
    val author = remember { mutableStateOf("") }
    val content = remember { mutableStateOf("") }
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextField(
            value = content.value,
            onValueChange = {content.value = it},
            label = { Text(text = "Ajoutez un commentaire")},
            modifier = Modifier
                .fillMaxHeight()
                .padding(16.dp)
        )
        Column(
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            FloatingActionButton(
                onClick = {
                    author.value = "Arsene"
                    val comment = Comment(author.value,content.value)
                    onCommentAdded(comment)
                    author.value = ""
                    content.value = ""
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(Icons.Default.AddCircle, contentDescription = "Ajout commentaire")
            }

            FloatingActionButton(
                onClick = {
                          addToCard(context,nom )
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(Icons.Default.ShoppingCart, contentDescription = "WishCard")
            }
        }

    }
}

 */
/*
fun addToCard(context: Context, nom: String) {
    val uid = getUidFromSharedPreferences(context)
    if (uid != null) {
        val firestore = Firebase.firestore
        val usersCollection = firestore.collection("users")
        val docRef = usersCollection.document(uid)

        docRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val cardList = document.get("cardlist") as? MutableList<String>
                    if (cardList != null) {
                        cardList.add(nom)
                        docRef.update("cardlist", cardList)
                            .addOnSuccessListener {
                                Toast.makeText(context, "Nom ajouté à la liste de cartes", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(context, "Échec de l'ajout à la liste de cartes", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        Toast.makeText(context, "Liste de cartes inexistante", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Document inexistant", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Échec de la récupération du document", Toast.LENGTH_SHORT).show()
            }
    } else {
        Toast.makeText(context, "UID non disponible dans les préférences partagées", Toast.LENGTH_SHORT).show()
    }
}

 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddComments(
    context: Context,
    salleId: String,
    onCommentAdded: (Comment) -> Unit
){
    val userId = getUidFromSharedPreferences(context)

    val author = remember { mutableStateOf("") }
    val content = remember { mutableStateOf("") }
    val context = LocalContext.current

    if (userId != null) {
        LaunchedEffect(userId) {
            val db = Firebase.firestore
            db.collection("users").document(userId).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val prenom = document.getString("prenom") ?: ""
                        val nom = document.getString("nom") ?: ""
                        author.value = "$prenom $nom"
                    }
                }
                .addOnFailureListener { exception ->
                    // Handle any errors
                }
        }
    }

    Row(
        modifier = Modifier
            .fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextField(
            value = content.value,
            onValueChange = { content.value = it },
            label = { Text(text = "Ajoutez un commentaire") },
            modifier = Modifier
                .fillMaxHeight()
                .padding(16.dp)
        )
        Column(
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            FloatingActionButton(
                onClick = {
                    val comment = Comment(author.value, content.value)
                    onCommentAdded(comment)
                    addCommentToSalle(salleId, author.value, content.value)
                    content.value = ""

                },
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(Icons.Default.AddCircle, contentDescription = "Ajout commentaire")
            }

            FloatingActionButton(
                onClick = {
                    addToCard(context, salleId)
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(Icons.Default.ShoppingCart, contentDescription = "WishCard")
            }
        }
    }
}



fun addCommentToSalle(salleId: String, author: String, commentText: String) {
    val db = FirebaseFirestore.getInstance()

    // Format the current date as "dd/MM/yyyy"
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val currentDate = dateFormat.format(Date())

    val comment = hashMapOf(
        "auteur" to author,
        "commentaire" to commentText,
        "date" to currentDate
    )

    val salleRef = db.collection("salles").document(salleId)

    salleRef.update("commentaire", FieldValue.arrayUnion(comment))
        .addOnSuccessListener {
            Log.d("Firestore", "Comment added successfully")
        }
        .addOnFailureListener { e ->
            Log.w("Firestore", "Error adding comment", e)
        }
}

fun addToCard(context: Context, nom: String) {
    val uid = getUidFromSharedPreferences(context)
    if (uid != null) {
        val firestore = Firebase.firestore
        val usersCollection = firestore.collection("users")
        val docRef = usersCollection.document(uid)

        docRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val cardList = document.get("cardlist") as? MutableList<String>
                    if (cardList != null) {
                        if (cardList.contains(nom)) {
                            cardList.remove(nom)
                            docRef.update("cardlist", cardList)
                                .addOnSuccessListener {
                                    Toast.makeText(context, "Nom supprimé de la liste de cartes", Toast.LENGTH_SHORT).show()
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(context, "Échec de la suppression de la liste de cartes", Toast.LENGTH_SHORT).show()
                                }
                        } else {
                            cardList.add(nom)
                            docRef.update("cardlist", cardList)
                                .addOnSuccessListener {
                                    Toast.makeText(context, "Nom ajouté à la liste de cartes", Toast.LENGTH_SHORT).show()
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(context, "Échec de l'ajout à la liste de cartes", Toast.LENGTH_SHORT).show()
                                }
                        }
                    } else {
                        Toast.makeText(context, "Liste de cartes inexistante", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Document inexistant", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Échec de la récupération du document", Toast.LENGTH_SHORT).show()
            }
    } else {
        Toast.makeText(context, "UID non disponible dans les préférences partagées", Toast.LENGTH_SHORT).show()
    }
}


private fun getUidFromSharedPreferences(context: Context): String? {
    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    val uid = sharedPreferences.getString("user_uid", "")
    return uid
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailsSalles(innerPadding: PaddingValues, item: Salle) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
            .padding(top = 30.dp)

    ){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Page de : ${item.name} ${item.location_} id : ${item.id}",
                color = Color.White,
                fontSize = 20.sp
            )
        }
    }
    // Affiche les détails de l'élément spécifié
}
