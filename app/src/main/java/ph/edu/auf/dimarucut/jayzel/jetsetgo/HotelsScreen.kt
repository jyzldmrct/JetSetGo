package ph.edu.auf.dimarucut.jayzel.jetsetgo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.draw.clip
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

        LazyColumn {
            items(trips) { trip ->
                TripItem(trip = trip, onAddAccommodation = { selectedTrip ->
                })
            }
        }
    }
}

@Composable
fun TripItem(trip: Trip, onAddAccommodation: (Trip) -> Unit) {
    var showForm by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var selectedAccommodation by remember { mutableStateOf<Accommodation?>(null) }

    Box(
        modifier = Modifier
            .padding(8.dp)
            .background(SkyBlue, shape = RoundedCornerShape(30.dp))
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
                    onClick = { showDialog = true }
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
                showDialog = true
            },
            onCancel = { showForm = false }
        )
    }
}

@Composable
fun AccommodationFormDialog(onSubmit: (Accommodation) -> Unit, onCancel: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var contactInfo by remember { mutableStateOf("") }
    var checkInDate by remember { mutableStateOf("") }
    var checkOutDate by remember { mutableStateOf("") }
    var reservationNumber by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text(text = "Add Accommodation") },
        text = {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") }
                )
                TextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Address") }
                )
                TextField(
                    value = contactInfo,
                    onValueChange = { contactInfo = it },
                    label = { Text("Contact Info") }
                )
                TextField(
                    value = checkInDate,
                    onValueChange = { checkInDate = it },
                    label = { Text("Check-In Date") }
                )
                TextField(
                    value = checkOutDate,
                    onValueChange = { checkOutDate = it },
                    label = { Text("Check-Out Date") }
                )
                TextField(
                    value = reservationNumber,
                    onValueChange = { reservationNumber = it },
                    label = { Text("Reservation Number") }
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
            }) {
                Text("Submit")
            }
        },
        dismissButton = {
            Button(onClick = onCancel) {
                Text("Cancel")
            }
        }
    )
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

