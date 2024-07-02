package com.example.hotspot.Vue

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hotspot.Vue.ui.theme.HotSpotTheme
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Date
import java.util.Locale

class ReservationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val receivedUidUser = intent.getStringExtra("UidUser").toString()
            val context = LocalContext.current
            HotSpotTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //Text(text = receivedUidUser)
                    CardListScreen(
                        documentId = receivedUidUser,
                        //userId = receivedUidUser,
                        onItemClick = { /* handle item click */ }
                    )
                }
            }
        }
    }
}


fun getCardList(documentId: String, onSuccess: (List<String>) -> Unit, onFailure: () -> Unit) {
    val firestore = Firebase.firestore
    val docRef = firestore.collection("users").document(documentId)
    docRef.get()
        .addOnSuccessListener { document ->
            if (document.exists()) {
                val cardList = document.get("cardlist") as? List<String>
                if (cardList != null) {
                    onSuccess(cardList)
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



/*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardListScreen(documentId: String, onItemClick: (String) -> Unit) {
    val context = LocalContext.current
    val cardList = remember { mutableStateListOf<String>() }

    var selectedItem by remember { mutableStateOf<String?>(null) }

    // Load card list from Firestore
    LaunchedEffect(documentId) {
        getCardList(
            documentId = documentId,
            onSuccess = { list ->
                cardList.clear()
                cardList.addAll(list)
            },
            onFailure = {
                Toast.makeText(context, "Failed to load card list", Toast.LENGTH_SHORT).show()
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Card List") },
                navigationIcon = {
                    IconButton(onClick = { /* handle back navigation */ }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Surface(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            LazyColumn(
                contentPadding = PaddingValues(16.dp)
            ) {
                items(cardList) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 5.dp)
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .clickable { selectedItem = item },
                        elevation = CardDefaults.cardElevation(4.dp),
                    ) {
                        Text(
                            text = item,
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }

    selectedItem?.let { item ->
        showReservationDialog(
            context = context,
            item = item,
            onDismiss = { selectedItem = null }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun showReservationDialog(
    context: Context,
    item: String,
    onDismiss: () -> Unit
) {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val currentDate = remember { mutableStateOf(dateFormat.format(Date())) }
    val personCount = remember { mutableStateOf(1) }
    val pricePerPerson = 7

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Réserver", fontWeight = FontWeight.Bold) },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DatePicker(currentDate)
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Nombre de personnes: ")
                    TextField(
                        value = personCount.value.toString(),
                        onValueChange = { value ->
                            personCount.value = value.toIntOrNull() ?: 1
                        },
                        modifier = Modifier.width(80.dp)
                    )
                }
                Text("Prix total: ${personCount.value * pricePerPerson} $")
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onDismiss()
                    Toast.makeText(context, "Réservation effectuée pour $item", Toast.LENGTH_SHORT).show()
                }
            ) {
                Text("Confirmer")
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text("Annuler")
            }
        }
    )
}

@Composable
fun DatePicker(selectedDate: MutableState<String>) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            selectedDate.value = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Button(
        onClick = {
            datePickerDialog.show()
        }
    ) {
        Text(text = "Sélectionner une date")
    }
    Text(text = "Date sélectionnée: ${selectedDate.value}")
}

 */

/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardListScreen(documentId: String, onItemClick: (String) -> Unit) {
    val context = LocalContext.current
    val cardList = remember { mutableStateListOf<String>() }
    var selectedItem by remember { mutableStateOf<String?>(null) }

    // Load card list from Firestore
    LaunchedEffect(documentId) {
        getCardList(
            documentId = documentId,
            onSuccess = { list ->
                cardList.clear()
                cardList.addAll(list)
            },
            onFailure = {
                Toast.makeText(context, "Failed to load card list", Toast.LENGTH_SHORT).show()
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Card List") },
                navigationIcon = {
                    IconButton(onClick = { /* handle back navigation */ }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Surface(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            LazyColumn(
                contentPadding = PaddingValues(16.dp)
            ) {
                items(cardList) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 5.dp)
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .clickable { selectedItem = item },
                        elevation = CardDefaults.cardElevation(4.dp),
                    ) {
                        Text(
                            text = item,
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }

    selectedItem?.let { item ->
        showReservationDialog(
            documentId = documentId,
            context = context,
            item = item,
            onDismiss = { selectedItem = null }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun showReservationDialog(
    documentId: String,
    context: Context,
    item: String,
    onDismiss: () -> Unit
) {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val currentDate = remember { mutableStateOf(dateFormat.format(Date())) }
    val personCount = remember { mutableStateOf(1) }
    val pricePerPerson = 7

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Réserver", fontWeight = FontWeight.Bold) },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DatePicker(currentDate)
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Nombre de personnes: ")
                    TextField(
                        value = personCount.value.toString(),
                        onValueChange = { value ->
                            personCount.value = value.toIntOrNull() ?: 1
                        },
                        modifier = Modifier.width(80.dp)
                    )
                }
                Text("Prix total: ${personCount.value * pricePerPerson} $")
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val reservation = mapOf(
                        "auteur" to "Utilisateur", // Changez ceci en nom réel de l'utilisateur
                        "commentaire" to "Commentaire", // Changez ceci pour le vrai commentaire
                        "date" to currentDate.value
                    )

                    checkAndSaveReservation(
                        userId = documentId,
                        salleId = item,
                        reservation = reservation,
                        context = context,
                        onDismiss = onDismiss
                    )
                }
            ) {
                Text("Confirmer")
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text("Annuler")
            }
        }
    )
}

@Composable
fun DatePicker(selectedDate: MutableState<String>) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            selectedDate.value = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Button(
        onClick = {
            datePickerDialog.show()
        }
    ) {
        Text(text = "Sélectionner une date")
    }
    Text(text = "Date sélectionnée: ${selectedDate.value}")
}



fun checkAndSaveReservation(
    userId: String,
    salleId: String,
    reservation: Map<String, String>,
    context: Context,
    onDismiss: () -> Unit
) {
    val firestore = Firebase.firestore
    val userDocRef = firestore.collection("users").document(userId)
    val salleDocRef = firestore.collection("salles").document(salleId)

    // Check credit card information
    userDocRef.get().addOnSuccessListener { userDoc ->
        val creditCard = userDoc.get("creditCard") as? Map<String, String>
        val cardNumber = creditCard?.get("cardNumber")

        if (cardNumber == null || cardNumber.length < 15) {
            Toast.makeText(context, "Numéro de carte de crédit invalide.", Toast.LENGTH_SHORT).show()
            return@addOnSuccessListener
        }

        // Check for existing reservation date in salle
        salleDocRef.get().addOnSuccessListener { salleDoc ->
            val reservations = salleDoc.get("reservations") as? List<Map<String, String>> ?: emptyList()
            val dateExists = reservations.any { it["date"] == reservation["date"] }

            if (dateExists) {
                Toast.makeText(context, "Date déjà réservée.", Toast.LENGTH_SHORT).show()
                return@addOnSuccessListener
            }

            // Add reservation to both collections
            val batch = firestore.batch()
            batch.update(userDocRef, "reservations", FieldValue.arrayUnion(reservation))
            batch.update(salleDocRef, "reservations", FieldValue.arrayUnion(reservation))

            batch.commit().addOnSuccessListener {
                Toast.makeText(context, "Réservation réussie.", Toast.LENGTH_SHORT).show()
                onDismiss()
            }.addOnFailureListener {
                Toast.makeText(context, "Erreur lors de la réservation.", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(context, "Erreur de vérification des réservations de salle.", Toast.LENGTH_SHORT).show()
        }
    }.addOnFailureListener {
        Toast.makeText(context, "Erreur de vérification de la carte de crédit.", Toast.LENGTH_SHORT).show()
    }
}*/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardListScreen(documentId: String, onItemClick: (String) -> Unit) {
    val context = LocalContext.current
    val cardList = remember { mutableStateListOf<String>() }
    var selectedItem by remember { mutableStateOf<String?>(null) }

    // Load card list from Firestore
    LaunchedEffect(documentId) {
        getCardList(
            documentId = documentId,
            onSuccess = { list ->
                cardList.clear()
                cardList.addAll(list)
            },
            onFailure = {
                Toast.makeText(context, "Failed to load card list", Toast.LENGTH_SHORT).show()
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Card List") },
                navigationIcon = {
                    IconButton(onClick = { /* handle back navigation */ }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Surface(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            LazyColumn(
                contentPadding = PaddingValues(16.dp)
            ) {
                items(cardList) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 5.dp)
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .clickable { selectedItem = item },
                        elevation = CardDefaults.cardElevation(4.dp),
                    ) {
                        Text(
                            text = item,
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }

    selectedItem?.let { item ->
        showReservationDialog(
            documentId = documentId,
            context = context,
            item = item,
            onDismiss = { selectedItem = null }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun showReservationDialog(
    documentId: String,
    context: Context,
    item: String,
    onDismiss: () -> Unit
) {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val currentDate = remember { mutableStateOf(dateFormat.format(Date())) }
    val personCount = remember { mutableStateOf(1) }
    val pricePerPerson = 7

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Réserver", fontWeight = FontWeight.Bold) },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DatePicker(currentDate)
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Nombre de personnes: ")
                    TextField(
                        value = personCount.value.toString(),
                        onValueChange = { value ->
                            personCount.value = value.toIntOrNull() ?: 1
                        },
                        modifier = Modifier.width(80.dp)
                    )
                }
                Text("Prix total: ${personCount.value * pricePerPerson} $")
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val reservation = mapOf(
                        "auteur" to "Utilisateur", // Changez ceci en nom réel de l'utilisateur
                        "item" to item,
                        "date" to currentDate.value,
                        "personCount" to personCount.value.toString(),
                        "totalPrice" to (personCount.value * pricePerPerson).toString()
                    )

                    checkAndSaveReservation(
                        userId = documentId,
                        salleId = item,
                        reservation = reservation,
                        context = context,
                        onDismiss = onDismiss
                    )
                }
            ) {
                Text("Confirmer")
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text("Annuler")
            }
        }
    )
}

@Composable
fun DatePicker(selectedDate: MutableState<String>) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            selectedDate.value = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Button(
        onClick = {
            datePickerDialog.show()
        }
    ) {
        Text(text = "Sélectionner une date")
    }
    Text(text = "Date sélectionnée: ${selectedDate.value}")
}


fun checkAndSaveReservation(
    userId: String,
    salleId: String,
    reservation: Map<String, String>,
    context: Context,
    onDismiss: () -> Unit
) {
    val firestore = Firebase.firestore
    val userDocRef = firestore.collection("users").document(userId)
    val salleDocRef = firestore.collection("salles").document(salleId)

    // Check credit card information
    userDocRef.get().addOnSuccessListener { userDoc ->
        val creditCard = userDoc.get("creditCard") as? Map<String, String>
        val cardNumber = creditCard?.get("cardNumber")

        if (cardNumber == null || cardNumber.length < 15) {
            Toast.makeText(context, "Numéro de carte de crédit invalide.", Toast.LENGTH_SHORT).show()
            return@addOnSuccessListener
        }

        // Check for existing reservation date in salle
        salleDocRef.get().addOnSuccessListener { salleDoc ->
            val reservations = salleDoc.get("reservations") as? List<Map<String, String>> ?: emptyList()
            val dateExists = reservations.any { it["date"] == reservation["date"] }

            if (dateExists) {
                Toast.makeText(context, "Date déjà réservée.", Toast.LENGTH_SHORT).show()
                return@addOnSuccessListener
            }

            // Add reservation to both collections
            val batch = firestore.batch()
            batch.update(userDocRef, "reservations", FieldValue.arrayUnion(reservation))
            batch.update(salleDocRef, "reservations", FieldValue.arrayUnion(reservation))

            batch.commit().addOnSuccessListener {
                Toast.makeText(context, "Réservation réussie.", Toast.LENGTH_SHORT).show()
                onDismiss()
            }.addOnFailureListener {
                Toast.makeText(context, "Erreur lors de la réservation.", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(context, "Erreur de vérification des réservations de salle.", Toast.LENGTH_SHORT).show()
        }
    }.addOnFailureListener {
        Toast.makeText(context, "Erreur de vérification de la carte de crédit.", Toast.LENGTH_SHORT).show()
    }
}
