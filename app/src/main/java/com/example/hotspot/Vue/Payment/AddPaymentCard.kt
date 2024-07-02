package com.example.hotspot.Vue.Payment

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
//import androidx.compose.material.Button
//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.hotspot.R
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import android.widget.Toast


private fun getUidFromSharedPreferences(context: Context): String? {
    val sharedPreferences = context.getSharedPreferences("com.example.hotspot", Context.MODE_PRIVATE)
    return sharedPreferences.getString("user_id", null)
}

fun addCreditCardToFirestore(
    context: Context,
    name: String,
    cardNumber: String,
    expiryDate: String,
    cvc: String,
    onSuccess: () -> Unit,
    onFailure: () -> Unit
) {
    val uid = getUidFromSharedPreferences(context)
    if (uid != null) {
        val firestore = Firebase.firestore
        val usersCollection = firestore.collection("users")
        val docRef = usersCollection.document(uid)

        val creditCardData = mapOf(
            "name" to name,
            "cardNumber" to cardNumber,
            "expiryDate" to expiryDate,
            "cvc" to cvc
        )

        docRef.update("creditCard", creditCardData)
            .addOnSuccessListener {
                Toast.makeText(context, "Card added successfully", Toast.LENGTH_SHORT).show()
                onSuccess()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Failed to add card", Toast.LENGTH_SHORT).show()
                onFailure()
            }
    } else {
        Toast.makeText(context, "UID not available in shared preferences", Toast.LENGTH_SHORT).show()
    }
}
/*
fun getCreditCardFromFirestore(
    context: Context,
    onSuccess: (String, String, String, String) -> Unit,
    onFailure: () -> Unit
) {
    val uid = getUidFromSharedPreferences(context)
    if (uid != null) {
        val firestore = Firebase.firestore
        val usersCollection = firestore.collection("users")
        val docRef = usersCollection.document(uid)

        docRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val creditCardData = document.get("creditCard") as? Map<String, String>
                    if (creditCardData != null) {
                        val name = creditCardData["name"] ?: ""
                        val cardNumber = creditCardData["cardNumber"] ?: ""
                        val expiryDate = creditCardData["expiryDate"] ?: ""
                        val cvc = creditCardData["cvc"] ?: ""
                        onSuccess(name, cardNumber, expiryDate, cvc)
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
    } else {
        Toast.makeText(context, "UID not available in shared preferences", Toast.LENGTH_SHORT).show()
    }
}*/


fun getCreditCardFromFirestore(
    context: Context,
    onSuccess: (String, String, String, String) -> Unit,
    onFailure: () -> Unit
) {
    val uid = getUidFromSharedPreferences(context)
    if (uid != null) {
        val firestore = Firebase.firestore
        val usersCollection = firestore.collection("users")
        val docRef = usersCollection.document(uid)

        docRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val creditCardData = document.get("creditCard") as? Map<String, String>
                    if (creditCardData != null) {
                        val name = creditCardData["name"] ?: ""
                        val cardNumber = creditCardData["cardNumber"] ?: ""
                        val expiryDate = creditCardData["expiryDate"] ?: ""
                        val cvc = creditCardData["cvc"] ?: ""
                        onSuccess(name, cardNumber, expiryDate, cvc)
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
    } else {
        Toast.makeText(context, "UID not available in shared preferences", Toast.LENGTH_SHORT).show()
    }
}

/*
@ExperimentalAnimationApi
@Composable
fun AddPaymentCard(context: Context) {

    var nameText by remember { mutableStateOf(TextFieldValue()) }
    var cardNumber by remember { mutableStateOf(TextFieldValue()) }
    var expiryNumber by remember { mutableStateOf(TextFieldValue()) }
    var cvcNumber by remember { mutableStateOf(TextFieldValue()) }

    // Load credit card information from Firestore
    LaunchedEffect(Unit) {
        getCreditCardFromFirestore(
            context = context,
            onSuccess = { name, number, expiry, cvc ->
                nameText = TextFieldValue(name)
                cardNumber = TextFieldValue(number)
                expiryNumber = TextFieldValue(expiry)
                cvcNumber = TextFieldValue(cvc)
            },
            onFailure = {
                // Handle failure if needed, e.g., show a toast message
            }
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        PaymentCard(
            nameText,
            cardNumber,
            expiryNumber,
            cvcNumber
        )
        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            item {
                InputItem(
                    textFieldValue = nameText,
                    label = stringResource(id = R.string.card_holder_name),
                    onTextChanged = { nameText = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                )
            }

            item {
                InputItem(
                    textFieldValue = cardNumber,
                    label = stringResource(id = R.string.card_holder_number),
                    keyboardType = KeyboardType.Number,
                    onTextChanged = { cardNumber = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    visualTransformation = CreditCardFilter
                )
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    InputItem(
                        textFieldValue = expiryNumber,
                        label = stringResource(id = R.string.expiry_date),
                        keyboardType = KeyboardType.Number,
                        onTextChanged = { expiryNumber = it },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                    )
                    InputItem(
                        textFieldValue = cvcNumber,
                        label = stringResource(id = R.string.cvc),
                        keyboardType = KeyboardType.Number,
                        onTextChanged = { cvcNumber = it },
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    )
                }
            }

            item {
                Button(
                    onClick = {
                        addCreditCardToFirestore(
                            context = context,
                            name = nameText.text,
                            cardNumber = cardNumber.text,
                            expiryDate = expiryNumber.text,
                            cvc = cvcNumber.text,
                            onSuccess = { /* Success callback if needed */ },
                            onFailure = { /* Failure callback if needed */ }
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.save),
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .padding(horizontal = 30.dp, vertical = 8.dp)
                    )
                }
            }
        }
    }
}


 */

@ExperimentalAnimationApi
@Composable
fun AddPaymentCard(context: Context) {

    var nameText by remember { mutableStateOf(TextFieldValue()) }
    var cardNumber by remember { mutableStateOf(TextFieldValue()) }
    var expiryNumber by remember { mutableStateOf(TextFieldValue()) }
    var cvcNumber by remember { mutableStateOf(TextFieldValue()) }

    var cvcError by remember { mutableStateOf(false) }
    var expiryError by remember { mutableStateOf(false) }

    // Load credit card information from Firestore
    LaunchedEffect(Unit) {
        getCreditCardFromFirestore(
            context = context,
            onSuccess = { name, number, expiry, cvc ->
                nameText = TextFieldValue(name)
                cardNumber = TextFieldValue(number)
                expiryNumber = TextFieldValue(expiry)
                cvcNumber = TextFieldValue(cvc)
            },
            onFailure = {
                // Handle failure if needed, e.g., show a toast message
            }
        )
    }

    fun validateCVC(cvc: String): Boolean {
        return cvc.length == 3 && cvc.all { it.isDigit() }
    }

    fun validateExpiry(expiry: String): Boolean {
        if (expiry.length != 4) return false
        val month = expiry.substring(0, 2).toIntOrNull() ?: return false
        val year = expiry.substring(2, 4).toIntOrNull() ?: return false
        return month in 1..12 && year >= 23
    }

    Column(modifier = Modifier.fillMaxSize()) {
        PaymentCard(
            nameText,
            cardNumber,
            expiryNumber,
            cvcNumber
        )
        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            item {
                InputItem(
                    textFieldValue = nameText,
                    label = stringResource(id = R.string.card_holder_name),
                    onTextChanged = { nameText = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                )
            }

            item {
                InputItem(
                    textFieldValue = cardNumber,
                    label = stringResource(id = R.string.card_holder_number),
                    keyboardType = KeyboardType.Number,
                    onTextChanged = { cardNumber = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    visualTransformation = CreditCardFilter
                )
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                        InputItem(
                            textFieldValue = expiryNumber,
                            label = stringResource(id = R.string.expiry_date),
                            keyboardType = KeyboardType.Number,
                            onTextChanged = {
                                expiryNumber = it
                                expiryError = !validateExpiry(it.text)
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                        if (expiryError) {
                            Text(
                                text = "Invalid expiry date",
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                    Column(modifier = Modifier.weight(1f).padding(start = 8.dp)) {
                        InputItem(
                            textFieldValue = cvcNumber,
                            label = stringResource(id = R.string.cvc),
                            keyboardType = KeyboardType.Number,
                            onTextChanged = {
                                cvcNumber = it
                                cvcError = !validateCVC(it.text)
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                        if (cvcError) {
                            Text(
                                text = "Invalid CVC",
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }

            item {
                Button(
                    onClick = {
                        if (!cvcError && !expiryError) {
                            addCreditCardToFirestore(
                                context = context,
                                name = nameText.text,
                                cardNumber = cardNumber.text,
                                expiryDate = expiryNumber.text,
                                cvc = cvcNumber.text,
                                onSuccess = { /* Success callback if needed */ },
                                onFailure = { /* Failure callback if needed */ }
                            )
                        } else {
                            Toast.makeText(context, "Please correct the errors", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.save),
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .padding(horizontal = 30.dp, vertical = 8.dp)
                    )
                }
            }
        }
    }
}


