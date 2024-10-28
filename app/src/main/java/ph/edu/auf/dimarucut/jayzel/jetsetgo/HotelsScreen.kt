package ph.edu.auf.dimarucut.jayzel.jetsetgo

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import ph.edu.auf.dimarucut.jayzel.jetsetgo.model.SharedViewModel
import ph.edu.auf.dimarucut.jayzel.jetsetgo.models.BudgetDetails
import ph.edu.auf.dimarucut.jayzel.jetsetgo.models.PackingItem
import ph.edu.auf.dimarucut.jayzel.jetsetgo.models.Trip
import ph.edu.auf.dimarucut.jayzel.jetsetgo.ui.theme.DenimBlue
import ph.edu.auf.dimarucut.jayzel.jetsetgo.ui.theme.GlacialIndifference
import ph.edu.auf.dimarucut.jayzel.jetsetgo.ui.theme.PaytoneOne
import ph.edu.auf.dimarucut.jayzel.jetsetgo.ui.theme.SkyBlue
import ph.edu.auf.dimarucut.jayzel.jetsetgo.models.Accommodation
import ph.edu.auf.dimarucut.jayzel.jetsetgo.models.TransportationOption
import ph.edu.auf.dimarucut.jayzel.jetsetgo.ui.theme.LightDenimBlue
import ph.edu.auf.dimarucut.jayzel.jetsetgo.ui.theme.SunsetOrange

@Composable
fun HotelsScreen(sharedViewModel: SharedViewModel) {
    val tripsState = sharedViewModel.trips.observeAsState(emptyList())
    val trips = tripsState.value

    Column {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    text = "Accommodations",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(0.dp, 16.dp, 0.dp, 0.dp),
                    fontFamily = PaytoneOne,
                    fontSize = 24.sp,
                    color = DenimBlue,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Discover your perfect stay for a memorable trip.",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(0.dp),
                    fontFamily = GlacialIndifference,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )

                Box(
                    modifier = Modifier
                        .width(280.dp)
                        .height(200.dp)
                        .padding(vertical = 16.dp)
                        .background(SunsetOrange, shape = RoundedCornerShape(20.dp))
                        .clip(RoundedCornerShape(20.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.accommodationimage),
                        contentDescription = "Your Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp))
                    )
                }
            }
        }

        if (trips.isEmpty()) {
            Text(
                text = "No upcoming trips.",
                fontFamily = GlacialIndifference,
                fontSize = 16.sp,
                color = DenimBlue,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        } else {
            LazyColumn {
                items(trips) { trip ->
                    TripItem(trip = trip, onAddAccommodation = { selectedTrip ->
                    })
                }
            }
        }
    }
}

@Composable
fun TripItem(trip: Trip, onAddAccommodation: (Trip) -> Unit) {
    var showForm by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var selectedAccommodation by remember { mutableStateOf<Accommodation?>(null) }
    var context = LocalContext.current


    Box(
        modifier = Modifier
            .padding(8.dp)
            .background(SkyBlue, shape = RoundedCornerShape(25.dp))
            .wrapContentSize()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "${trip.destination}, ${trip.countryCode}",
                fontFamily = PaytoneOne,
                color = Color.White,
                fontSize = 24.sp,
                textAlign = TextAlign.Start,
                modifier = Modifier.weight(1f)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = { showForm = true }
                ) {
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = "Add Accommodation",
                        tint = Color.White
                    )
                }
                IconButton(
                    onClick = {
                        if (selectedAccommodation == null) {
                            Toast.makeText(context, "Add accommodation details first.", Toast.LENGTH_SHORT).show()
                        } else {
                            showDialog = true
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "View Accommodation Details",
                        tint = Color.White
                    )
                }
            }
        }
    }

    if (showForm) {
        AccommodationFormDialog(
            onSubmit = { accommodation ->
                selectedAccommodation = accommodation
                showForm = false
            },
            onCancel = { showForm = false }
        )
    }

    if (showDialog && selectedAccommodation != null) {
        AccommodationDetailsDialog(
            showDialog = showDialog,
            onDismiss = { showDialog = false },
            accommodation = selectedAccommodation!!,
            onEdit = { updatedAccommodation ->
                // Handle the updated accommodation here
                selectedAccommodation = updatedAccommodation
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccommodationFormDialog(onSubmit: (Accommodation) -> Unit, onCancel: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var contactInfo by remember { mutableStateOf("") }
    var checkInDate by remember { mutableStateOf("") }
    var checkOutDate by remember { mutableStateOf("") }
    var reservationNumber by remember { mutableStateOf("") }
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = onCancel,
        containerColor = Color.White,
        title = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(0.dp)) {
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .height(1.dp),
                        color = SunsetOrange
                    )
                    Text(
                        "Add Accommodation",
                        fontFamily = PaytoneOne,
                        fontSize = 28.sp,
                        textAlign = TextAlign.Center,
                        color = SunsetOrange
                    )
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .height(1.dp),
                        color = SunsetOrange
                    )
                }
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .padding(0.dp),
            ) {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = {
                        Text("Name",
                            fontFamily = GlacialIndifference,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(top = 0.dp)
                        )
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = SunsetOrange,
                        unfocusedIndicatorColor = LightDenimBlue,
                        focusedLabelColor = SunsetOrange,
                        unfocusedLabelColor = LightDenimBlue,
                        cursorColor = DenimBlue
                    ),
                    textStyle = TextStyle(
                        fontFamily = GlacialIndifference,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = DenimBlue
                    )
                )
                TextField(
                    value = address,
                    onValueChange = { address = it },
                    label = {
                        Text("Address",
                            fontFamily = GlacialIndifference,
                            fontSize = 16.sp
                        )
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = SunsetOrange,
                        unfocusedIndicatorColor = LightDenimBlue,
                        focusedLabelColor = SunsetOrange,
                        unfocusedLabelColor = LightDenimBlue,
                        cursorColor = DenimBlue
                    ),
                    textStyle = TextStyle(
                        fontFamily = GlacialIndifference,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = DenimBlue
                    )
                )
                TextField(
                    value = contactInfo,
                    onValueChange = { contactInfo = it },
                    label = { Text("Contact Info",
                        fontFamily = GlacialIndifference,
                        fontSize = 16.sp
                    )
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = SunsetOrange,
                        unfocusedIndicatorColor = LightDenimBlue,
                        focusedLabelColor = SunsetOrange,
                        unfocusedLabelColor = LightDenimBlue,
                        cursorColor = DenimBlue
                    ),
                    textStyle = TextStyle(
                        fontFamily = GlacialIndifference,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = DenimBlue
                    )
                )
                TextField(
                    value = checkInDate,
                    onValueChange = { checkInDate = it },
                    label = { Text("Check-In Date",
                        fontFamily = GlacialIndifference,
                        fontSize = 16.sp
                    )
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = SunsetOrange,
                        unfocusedIndicatorColor = LightDenimBlue,
                        focusedLabelColor = SunsetOrange,
                        unfocusedLabelColor = LightDenimBlue,
                        cursorColor = DenimBlue
                    ),
                    textStyle = TextStyle(
                        fontFamily = GlacialIndifference,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = DenimBlue
                    )
                )
                TextField(
                    value = checkOutDate,
                    onValueChange = { checkOutDate = it },
                    label = { Text("Check-Out Date",
                        fontFamily = GlacialIndifference,
                        fontSize = 16.sp
                    )
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = SunsetOrange,
                        unfocusedIndicatorColor = LightDenimBlue,
                        focusedLabelColor = SunsetOrange,
                        unfocusedLabelColor = LightDenimBlue,
                        cursorColor = DenimBlue
                    ),
                    textStyle = TextStyle(
                        fontFamily = GlacialIndifference,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = DenimBlue
                    )
                )
                TextField(
                    value = reservationNumber,
                    onValueChange = { reservationNumber = it },
                    label = { Text("Reservation Number",
                        fontFamily = GlacialIndifference,
                        fontSize = 16.sp
                    )
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = SunsetOrange,
                        unfocusedIndicatorColor = LightDenimBlue,
                        focusedLabelColor = SunsetOrange,
                        unfocusedLabelColor = LightDenimBlue,
                        cursorColor = DenimBlue
                    ),
                    textStyle = TextStyle(
                        fontFamily = GlacialIndifference,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = DenimBlue
                    )
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                val accommodation = Accommodation(
                    name = name,
                    address = address,
                    contactInfo = contactInfo,
                    checkInDate = checkInDate,
                    checkOutDate = checkOutDate,
                    reservationNumber = reservationNumber
                )
                onSubmit(accommodation)
                Toast.makeText(context, "Accommodation added!", Toast.LENGTH_SHORT).show()
                onCancel()
            },
                colors = ButtonDefaults.buttonColors(containerColor = SunsetOrange) ) {
                Text("Add",
                    fontFamily = PaytoneOne, // Apply custom font
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        },

        dismissButton = {
            Button(onClick = onCancel,
                colors = ButtonDefaults.buttonColors(containerColor = LightDenimBlue) ) {
                Text(
                    "Cancel",
                    fontFamily = PaytoneOne,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    )
}

@Composable
fun AccommodationDetailsDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    accommodation: Accommodation,
    onEdit: (Accommodation) -> Unit
) {
    var showEditDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Info Icon",
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .size(20.dp)
                                    .clickable {
                                        showEditDialog = true
                                    },
                                tint = LightDenimBlue
                            )
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Add Icon",
                                modifier = Modifier
                                    .padding(start = 8.dp) // Add padding to the start of the second icon
                                    .size(20.dp)
                                    .clickable {
                                        onDismiss()
                                    },
                                tint = LightDenimBlue
                            )
                        }
                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            color = DenimBlue
                        )
                        Text(
                            text = "Accommodation Details",
                            fontFamily = PaytoneOne,
                            fontSize = 24.sp,
                            color = DenimBlue,
                            textAlign = TextAlign.Center
                        )
                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            color = DenimBlue
                        )
                    }
                }
            },
            text = {
                Column {
                    Text("Name:",
                        fontFamily = GlacialIndifference,
                        fontSize = 16.sp,
                        color = LightDenimBlue
                    )

                    Text("${accommodation.name}",
                        fontFamily = GlacialIndifference,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = DenimBlue
                    )

                    Text("Address:",
                        fontFamily = GlacialIndifference,
                        fontSize = 16.sp,
                        color = LightDenimBlue
                    )

                    Text("${accommodation.address}",
                        fontFamily = GlacialIndifference,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = DenimBlue
                    )

                    Text("Contact Info:",
                        fontFamily = GlacialIndifference,
                        fontSize = 16.sp,
                        color = LightDenimBlue
                    )

                    Text("${accommodation.contactInfo}",
                        fontFamily = GlacialIndifference,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = DenimBlue
                    )

                    Text("Check-In Date:",
                        fontFamily = GlacialIndifference,
                        fontSize = 16.sp,
                        color = LightDenimBlue
                    )
                    Text("${accommodation.checkInDate}",
                        fontFamily = GlacialIndifference,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = DenimBlue
                    )

                    Text("Check-Out Date:",
                        fontFamily = GlacialIndifference,
                        fontSize = 16.sp,
                        color = LightDenimBlue
                    )

                    Text("${accommodation.checkOutDate}",
                        fontFamily = GlacialIndifference,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = DenimBlue
                    )

                    Text("Reservation Number:",
                        fontFamily = GlacialIndifference,
                        fontSize = 16.sp,
                        color = LightDenimBlue
                    )

                    Text("${accommodation.reservationNumber}",
                        fontFamily = GlacialIndifference,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = DenimBlue
                    )
                }
            },
            confirmButton = { }
        )
    }
    if (showEditDialog) {
        EditAccommodationDialog(
            showDialog = showEditDialog,
            onDismiss = { showEditDialog = false },
            accommodation = accommodation,
            onSubmit = onEdit
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditAccommodationDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    accommodation: Accommodation,
    onSubmit: (Accommodation) -> Unit
) {
    var name by remember { mutableStateOf(accommodation.name) }
    var address by remember { mutableStateOf(accommodation.address) }
    var contactInfo by remember { mutableStateOf(accommodation.contactInfo) }
    var checkInDate by remember { mutableStateOf(accommodation.checkInDate) }
    var checkOutDate by remember { mutableStateOf(accommodation.checkOutDate) }
    var reservationNumber by remember { mutableStateOf(accommodation.reservationNumber) }
    var showEditDialog by remember { mutableStateOf(false) } // Define showEditDialog here

    val context = LocalContext.current

    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Close Icon",
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .size(24.dp)
                                    .clickable {
                                        onDismiss()
                                    },
                                tint = LightDenimBlue
                            )
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Save Icon",
                                modifier = Modifier
                                    .size(24.dp)
                                    .clickable {
                                        val updatedAccommodation = accommodation.copy(
                                            name = name,
                                            address = address,
                                            contactInfo = contactInfo,
                                            checkInDate = checkInDate,
                                            checkOutDate = checkOutDate,
                                            reservationNumber = reservationNumber
                                        )
                                        onSubmit(updatedAccommodation)
                                        onDismiss()
                                        Toast.makeText(context, "Accommodation updated!", Toast.LENGTH_SHORT).show()
                                    },
                                tint = SkyBlue
                            )
                        }
                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            color = SkyBlue
                        )
                        Text(
                            text = "Edit Accommodation",
                            fontFamily = PaytoneOne,
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center,
                            color = SkyBlue
                        )
                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            color = SkyBlue
                        )
                    }
                }
            },
            text = {
                Box(
                    modifier = Modifier
                        .width(500.dp)
                        .wrapContentHeight()
                ) {
                    Column (
                        modifier = Modifier.background(Color.Transparent)
                    ) {
                        TextField(
                            value = name,
                            onValueChange = { name = it },
                            label = { Text("Name") },
                            textStyle = TextStyle(
                                fontFamily = GlacialIndifference,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = DenimBlue // Text color applied directly here
                            ),
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = Color.Transparent,
                                focusedIndicatorColor = SkyBlue,
                                unfocusedIndicatorColor = LightDenimBlue,
                                focusedLabelColor = SkyBlue,
                                unfocusedLabelColor = LightDenimBlue,
                                cursorColor = DenimBlue
                            )
                        )
                        TextField(
                            value = address,
                            onValueChange = { address = it },
                            label = { Text("Address") },
                            textStyle = TextStyle(
                                fontFamily = GlacialIndifference,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = DenimBlue // Text color applied directly here
                            ),
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = Color.Transparent,
                                focusedIndicatorColor = SkyBlue,
                                unfocusedIndicatorColor = LightDenimBlue,
                                focusedLabelColor = SkyBlue,
                                unfocusedLabelColor = LightDenimBlue,
                                cursorColor = DenimBlue
                            )
                        )

                        TextField(
                            value = contactInfo,
                            onValueChange = { contactInfo = it },
                            label = { Text("Contact Info") },
                            textStyle = TextStyle(
                                fontFamily = GlacialIndifference,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = DenimBlue // Text color applied directly here
                            ),
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = Color.Transparent,
                                focusedIndicatorColor = SkyBlue,
                                unfocusedIndicatorColor = LightDenimBlue,
                                focusedLabelColor = SkyBlue,
                                unfocusedLabelColor = LightDenimBlue,
                                cursorColor = DenimBlue
                            )
                        )

                        TextField(
                            value = checkInDate,
                            onValueChange = { checkInDate = it },
                            label = { Text("Check-In Date") },
                            textStyle = TextStyle(
                                fontFamily = GlacialIndifference,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = DenimBlue // Text color applied directly here
                            ),
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = Color.Transparent,
                                focusedIndicatorColor = SkyBlue,
                                unfocusedIndicatorColor = LightDenimBlue,
                                focusedLabelColor = SkyBlue,
                                unfocusedLabelColor = LightDenimBlue,
                                cursorColor = DenimBlue
                            )
                        )

                        TextField(
                            value = checkOutDate,
                            onValueChange = { checkOutDate = it },
                            label = { Text("Check-Out Date") },
                            textStyle = TextStyle(
                                fontFamily = GlacialIndifference,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = DenimBlue // Text color applied directly here
                            ),
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = Color.Transparent,
                                focusedIndicatorColor = SkyBlue,
                                unfocusedIndicatorColor = LightDenimBlue,
                                focusedLabelColor = SkyBlue,
                                unfocusedLabelColor = LightDenimBlue,
                                cursorColor = DenimBlue
                            )
                        )

                        TextField(
                            value = reservationNumber,
                            onValueChange = { reservationNumber = it },
                            label = { Text("Reservation Number") },
                            textStyle = TextStyle(
                                fontFamily = GlacialIndifference,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = DenimBlue // Text color applied directly here
                            ),
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = Color.Transparent,
                                focusedIndicatorColor = SkyBlue,
                                unfocusedIndicatorColor = LightDenimBlue,
                                focusedLabelColor = SkyBlue,
                                unfocusedLabelColor = LightDenimBlue,
                                cursorColor = DenimBlue
                            )
                        )
                    }
                }
            },
            confirmButton = {},
            dismissButton = {}
        )
    }
}


@Preview(showBackground = true)
@Composable
fun HotelsScreenPreview() {
    HotelsScreen(SharedViewModel())
}

@Preview(showBackground = true)
@Composable
fun TripItemPreviewInHotelsScreen() {
    TripItem(
        trip = Trip(
            id = "1",
            destination = "Paris",
            countryCode = "FR",
            startDate = "2024-05-01",
            endDate = "2024-05-10",
            budgetDetails = BudgetDetails(totalBudget = 2000.0, items = emptyList()),
            packingChecklist = listOf(
                PackingItem(name = "Passport"),
                PackingItem(name = "Tickets"),
                PackingItem(name = "Clothes")
            ),
            transportationOptions = listOf(
                TransportationOption(name = "Flight"),
                TransportationOption(name = "Taxi")
            )
        ),
        onAddAccommodation = { /* Do nothing for preview */ }
    )
}

@Preview(showBackground = true)
@Composable
fun AccommodationFormDialogPreview() {
    AccommodationFormDialog(
        onSubmit = { /* Do nothing for preview */ },
        onCancel = { /* Do nothing for preview */ }
    )
}

@Preview(showBackground = true)
@Composable
fun AccommodationDetailsDialogPreview() {
    AccommodationDetailsDialog(
        showDialog = true,
        onDismiss = { /* Do nothing for preview */ },
        accommodation = Accommodation(
            name = "Hotel California",
            address = "1234 Sunset Blvd",
            contactInfo = "123-456-7890",
            checkInDate = "2024-05-01",
            checkOutDate = "2024-05-10",
            reservationNumber = "123456"
        ),
        onEdit = { /* Do nothing for preview */ }
    )
}

@Preview(showBackground = true)
@Composable
fun EditAccommodationDialogPreview() {
    EditAccommodationDialog(
        showDialog = true,
        onDismiss = { /* Do nothing for preview */ },
        accommodation = Accommodation(
            name = "Sample Hotel",
            address = "123 Sample Street",
            contactInfo = "123-456-7890",
            checkInDate = "2024-05-01",
            checkOutDate = "2024-05-10",
            reservationNumber = "ABC123"
        ),
        onSubmit = { /* Do nothing for preview */ }
    )
}




