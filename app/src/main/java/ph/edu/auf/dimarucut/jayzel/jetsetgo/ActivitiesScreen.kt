package ph.edu.auf.dimarucut.jayzel.jetsetgo

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ph.edu.auf.dimarucut.jayzel.jetsetgo.model.SharedViewModel
import ph.edu.auf.dimarucut.jayzel.jetsetgo.models.BudgetDetails
import ph.edu.auf.dimarucut.jayzel.jetsetgo.models.PackingItem
import ph.edu.auf.dimarucut.jayzel.jetsetgo.models.TransportationOption
import ph.edu.auf.dimarucut.jayzel.jetsetgo.models.Trip
import ph.edu.auf.dimarucut.jayzel.jetsetgo.ui.theme.DenimBlue
import ph.edu.auf.dimarucut.jayzel.jetsetgo.ui.theme.GlacialIndifference
import ph.edu.auf.dimarucut.jayzel.jetsetgo.ui.theme.LightDenimBlue
import ph.edu.auf.dimarucut.jayzel.jetsetgo.ui.theme.PaytoneOne
import ph.edu.auf.dimarucut.jayzel.jetsetgo.ui.theme.SkyBlue
import ph.edu.auf.dimarucut.jayzel.jetsetgo.ui.theme.SunsetOrange

@Composable
fun ActivitiesScreen(sharedViewModel: SharedViewModel, modifier: Modifier = Modifier) {
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
                    text = "Explore Your Activities",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(0.dp, 16.dp, 0.dp, 0.dp),
                    fontFamily = PaytoneOne,
                    fontSize = 24.sp,
                    color = DenimBlue,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Plan and keep track of your exciting adventures and experiences.",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(0.dp),
                    fontFamily = GlacialIndifference,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp
                )

                Box(
                    modifier = Modifier
                        .width(280.dp)
                        .height(200.dp)
                        .padding(vertical = 16.dp)
                        .background(SkyBlue, shape = RoundedCornerShape(20.dp))
                        .clip(RoundedCornerShape(20.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.activitiesimage),
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
                text = "No upcoming trips",
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
                    ActivityTripItem(trip = trip, onAddAccommodation = { selectedTrip ->
                        // Handle add accommodation action
                    })
                }
            }
        }
    }
}

@Composable
fun ActivityTripItem(trip: Trip, onAddAccommodation: (Trip) -> Unit) {
    val context = LocalContext.current
    var showAddActivityDialog by remember { mutableStateOf(false) }
    var showEditActivityDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .padding(8.dp)
            .wrapContentSize()
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "${trip.destination}, ${trip.countryCode}",
                fontFamily = PaytoneOne,
                color = DenimBlue,
                fontSize = 24.sp,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = { showAddActivityDialog = true },
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(containerColor = DenimBlue),
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .height(35.dp)
                    .width(150.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "ADD ACTIVITY",
                        fontFamily = GlacialIndifference,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            }
        }
    }

    if (showAddActivityDialog) {
        AddActivityDialog(
            showDialog = showAddActivityDialog,
            onDismiss = { showAddActivityDialog = false },
            onAddActivity = { category, name, date, time, description ->
                // Handle the added activity here
                Toast.makeText(context, "Activity Added: $name", Toast.LENGTH_SHORT).show()
            }
        )
    }

    if (showEditActivityDialog) {
        // Add your EditActivityDialog composable here
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddActivityDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onAddActivity: (String, String, String, String, String) -> Unit
) {
    if (showDialog) {
        var category by remember { mutableStateOf("") }
        var name by remember { mutableStateOf("") }
        var date by remember { mutableStateOf("") }
        var time by remember { mutableStateOf("") }
        var description by remember { mutableStateOf("") }

        val context = LocalContext.current

        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(0.dp)) {
                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp),
                            color = SunsetOrange
                        )
                        Text(
                            "Add Activity",
                            fontFamily = PaytoneOne,
                            fontSize = 28.sp,
                            textAlign = TextAlign.Center,
                            color = SunsetOrange
                        )
                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp),
                            color = SunsetOrange
                        )
                    }
                }
            },

            text = {
                Column {
                    TextField(
                        value = category,
                        onValueChange = { category = it },
                        label = { Text("Category",
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
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Name",
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
                        value = date,
                        onValueChange = { date = it },
                        label = { Text("Date",
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
                        value = time,
                        onValueChange = { time = it },
                        label = { Text("Time",
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
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Description",
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
                Button(
                    onClick = {
                        onAddActivity(category, name, date, time, description)
                        Toast.makeText(context, "Activity added!", Toast.LENGTH_SHORT).show()
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SunsetOrange) ) {
                    Text("Submit",
                        fontFamily = PaytoneOne, // Apply custom font
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            },
            dismissButton = {
                Button(onClick = onDismiss,
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
}

@Preview(showBackground = true)
@Composable
fun ActivitiesScreenPreview() {
    val sharedViewModel = remember { SharedViewModel() }
    sharedViewModel.addTrip(
        Trip(
            id = "1",
            destination = "Boracay",
            startDate = "2022-12-01",
            endDate = "2022-12-05",
            countryCode = "PH",
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
        )
    )
    ActivitiesScreen(sharedViewModel = sharedViewModel)
}

@Preview(showBackground = true)
@Composable
fun AddActivityDialogPreview() {
    AddActivityDialog(
        showDialog = true,
        onDismiss = {},
        onAddActivity = { category, name, date, time, description ->
            // Handle the added activity here
        }
    )
}