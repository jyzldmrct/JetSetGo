package ph.edu.auf.dimarucut.jayzel.jetsetgo

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ph.edu.auf.dimarucut.jayzel.jetsetgo.model.TripViewModel
import ph.edu.auf.dimarucut.jayzel.jetsetgo.models.*
import ph.edu.auf.dimarucut.jayzel.jetsetgo.ui.theme.*
import java.util.UUID
import androidx.compose.ui.text.font.FontWeight
import java.util.Currency
import java.util.Locale
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.ExperimentalFoundationApi
import ph.edu.auf.dimarucut.jayzel.jetsetgo.model.SharedViewModel


@Composable
fun FlightsScreen(tripViewModel: TripViewModel, sharedViewModel: SharedViewModel, modifier: Modifier = Modifier) {

    val trips by tripViewModel.trips.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var showTripDialog by remember { mutableStateOf(false) }
    var showTransportationDialog by remember { mutableStateOf(false) }
    var selectedTripId by remember { mutableStateOf<String?>(null) }
    var showBudgetDialog by remember { mutableStateOf(false) }
    var showPackingDialog by remember { mutableStateOf(false) }
    var budgetDetails: BudgetDetails? by remember { mutableStateOf(null) }
    var packingChecklist: List<PackingItem> by remember { mutableStateOf(emptyList()) }
    var transportationOptions: List<TransportationOption> by remember { mutableStateOf(emptyList()) }
    var selectedTrip by remember { mutableStateOf<Trip?>(null) }
    var showEditTripDialog by remember { mutableStateOf(false) }
    val trip = selectedTrip
    var showChosenTransportationDialog by remember { mutableStateOf(false) }
    var selectedTransportationOptions: List<TransportationOption> by remember { mutableStateOf(emptyList()) }

    val TransportationCategories = listOf(
        TransportationCategory(
            name = "Air Travel",
            options = listOf(
                TransportationOption("Commercial flights"),
                TransportationOption("Charter flights"),
                TransportationOption("Private jets"),
                TransportationOption("Domestic flights"),
                TransportationOption("Low-cost carriers")
            )
        ),
        TransportationCategory(
            name = "Ground Transportation",
            options = listOf(
                TransportationOption("Car rentals"),
                TransportationOption("Taxis"),
                TransportationOption("Buses"),
                TransportationOption("Trains"),
                TransportationOption("Motorbikes"),
                TransportationOption("Jeepneys"),  // Iconic mode of transport in the Philippines
                TransportationOption("Tricycles"),  // Common in rural areas
                TransportationOption("Vans for hire")  // Common for group travel
            )
        ),
        TransportationCategory(
            name = "Water Travel",
            options = listOf(
                TransportationOption("Ferries"),
                TransportationOption("Cruises"),
                TransportationOption("Yachts"),
                TransportationOption("Speedboats")
            )
        ),
        TransportationCategory(
            name = "Public Transport",
            options = listOf(
                TransportationOption("City buses"),
                TransportationOption("Subways"),
                TransportationOption("Trams"),
                TransportationOption("UV Express"),  // Popular shared van service in the Philippines
                TransportationOption("P2P Buses")  // Point-to-point bus services
            )
        ),
        TransportationCategory(
            name = "Specialty",
            options = listOf(
                TransportationOption("Cable cars"),
                TransportationOption("Tuk-tuks"),
                TransportationOption("Horse-drawn carriages"),
                TransportationOption("Sidecars"),  // Often used with motorcycles
                TransportationOption("Scooters")  // Increasingly popular for personal transport
            )
        )
    )



    val context = LocalContext.current

    if (showBudgetDialog && selectedTripId != null) {
        BudgetPlannerDialog(budgetDetails, onDismiss = { showBudgetDialog = false })
    }

    if (showPackingDialog && selectedTripId != null) {
        PackingChecklistDialog(packingChecklist, onDismiss = { showPackingDialog = false })
    }

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = "Flights and Trips Overview",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(0.dp, 16.dp, 0.dp, 0.dp),
                fontFamily = PaytoneOne,
                fontSize = 24.sp,
                color = DenimBlue,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Plan and track your upcoming flights and trips.",
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
                    .background(SkyBlue, shape = RoundedCornerShape(20.dp))
                    .clip(RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.flightspageimage),
                    contentDescription = "Your Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                )
            }

            Button(
                onClick = { showDialog = true },
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(containerColor = DenimBlue),
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .height(35.dp)
                    .width(280.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "ADD TRIPS",
                        fontFamily = GlacialIndifference,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add icon",
                        modifier = Modifier
                            .size(16.dp)
                            .align(Alignment.TopEnd),
                        tint = Color.White
                    )
                }
            }

            if (showDialog) {
                AddTripDialog(
                    showDialog = showDialog,
                    onDismiss = { showDialog = false },
                    onSubmit = { trip ->
                        tripViewModel.addTrip(trip)
                        showDialog = false
                    },
                    tripViewModel = tripViewModel,
                    sharedViewModel = sharedViewModel
                )
            }
        }

        if (trips.isEmpty()) {
            item {
                Text(text = "No upcoming trips.", style = MaterialTheme.typography.bodyMedium)
            }
        } else {
            items(trips) { trip ->
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    val boxModifier = Modifier
                        .size(200.dp)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(25.dp))
                        .background(SunsetOrange)
                    item {
                        Box(
                            modifier = boxModifier
                        ) {
                            IconButton(
                                onClick = {
                                    selectedTrip = trip
                                    showTripDialog = true
                                },
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = "Info Icon",
                                    tint = Color.White
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${trip.destination}, ${trip.countryCode}",
                                    fontFamily = PaytoneOne,
                                    fontSize = 30.sp,
                                    color = Color.White,
                                    lineHeight = 40.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                    item {
                        Box(
                            modifier = Modifier
                                .size(200.dp)
                                .padding(8.dp)
                                .clip(RoundedCornerShape(25.dp))
                                .background(
                                    brush = Brush.linearGradient(
                                        colors = listOf(SunsetOrange,LightSunsetOrange),
                                        start = Offset(0f, 0f), // Start at the top-left corner
                                        end = Offset.Infinite // End at the bottom-right corner
                                    )
                                )
                        ){
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.End,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {

                                        Icon(
                                            imageVector = Icons.Default.AddCircle,
                                            contentDescription = "Add Icon",
                                            tint = Color.White,
                                            modifier = Modifier
                                                .size(24.dp)
                                                .clickable {
                                                    showTransportationDialog = true
                                                }
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Icon(
                                            imageVector = Icons.Default.Info,
                                            contentDescription = "Info Icon",
                                            tint = Color.White,
                                            modifier = Modifier
                                                .size(24.dp)
                                                .clickable {
                                                    showChosenTransportationDialog = true
                                                }
                                        )
                                    }
                                }
                                Divider(color = Color.White,
                                    thickness = 2.dp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                )

                                Text(
                                    text = "Transportation",
                                    modifier = Modifier.align(Alignment.CenterHorizontally),
                                    style = TextStyle(
                                        fontFamily = PaytoneOne,
                                        fontSize = 21.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Icon(
                                    imageVector = Icons.Default.DirectionsCar,
                                    contentDescription = "Transportation Icon",
                                    tint = Color.White,
                                    modifier = Modifier.size(80.dp)
                                )
                            }
                        } }
                    item {
                        Box(
                            modifier = Modifier
                                .size(200.dp)
                                .padding(8.dp)
                                .clip(RoundedCornerShape(25.dp))
                                .background(
                                    brush = Brush.linearGradient(
                                        colors = listOf(SunsetOrange, LightSunsetOrange),
                                        start = Offset(200f, 0f),   // Start at the top-right corner
                                        end = Offset(0f, 200f)      // End at the bottom-left corner
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            BudgetCalculator(
                                budgetDetails = trip.budgetDetails,
                                countryCode = trip.countryCode
                            )
                        }
                    }
                    item {
                        Box(modifier = boxModifier) {
                            PackingChecklistScreen(
                                packingItems = trip.packingChecklist,
                                onAddItem = { newItem ->
                                    val updatedChecklist =
                                        trip.packingChecklist + PackingItem(newItem)
                                    tripViewModel.updatePackingChecklist(trip.id, updatedChecklist)
                                    Toast.makeText(context, "Item added!", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    if (showTransportationDialog) {
        TransportationDialog(
            showDialog = showTransportationDialog,
            onDismiss = { showTransportationDialog = false },
            onSubmit = { transportationOption ->
                selectedTransportationOptions = selectedTransportationOptions + transportationOption // Add selected option to the list
                showTransportationDialog = false
                Toast.makeText(context, "Transportation option added: ${transportationOption.name}", Toast.LENGTH_SHORT).show()
            },
            transportationCategories = TransportationCategories
        )
    }

    if (showTripDialog && trip != null) {
        AlertDialog(
            onDismissRequest = { showTripDialog = false },
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
                                        showEditTripDialog = true
                                    },
                                tint = LightDenimBlue
                            )
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Close Icon",
                                modifier = Modifier
                                    .size(24.dp)
                                    .clickable {
                                        showTripDialog = false
                                    },
                                tint = LightDenimBlue
                            )
                        }
                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            color = LightDenimBlue
                        )
                        Text(
                            "Trip Details",
                            fontFamily = PaytoneOne,
                            fontSize = 28.sp,
                            textAlign = TextAlign.Center,
                            color = DenimBlue
                        )
                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp, 8.dp, 8.dp, 0.dp),
                            color = LightDenimBlue
                        )
                    }
                }
            },
            text = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    Column {
                        Text(
                            "Destination:",
                            fontFamily = GlacialIndifference,
                            fontSize = 16.sp,
                            color = LightDenimBlue
                        )
                        Text(
                            "${trip.destination}",
                            fontFamily = GlacialIndifference,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = DenimBlue
                        )
                        Text(
                            "Departure Date: ",
                            fontFamily = GlacialIndifference,
                            fontSize = 16.sp,
                            color = LightDenimBlue
                        )
                        Text(
                            "${trip.startDate}",
                            fontFamily = GlacialIndifference,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = DenimBlue
                        )
                        Text(
                            "Return Date:",
                            fontFamily = GlacialIndifference,
                            fontSize = 16.sp,
                            color = LightDenimBlue
                        )
                        Text(
                            "${trip.endDate}",
                            fontFamily = GlacialIndifference,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = DenimBlue
                        )
                        Text(
                            "Country Code:",
                            fontFamily = GlacialIndifference,
                            fontSize = 16.sp,
                            color = LightDenimBlue
                        )
                        Text(
                            "${trip.countryCode}",
                            fontFamily = GlacialIndifference,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = DenimBlue
                        )
                        Text(
                            "Duration:",
                            fontFamily = GlacialIndifference,
                            fontSize = 16.sp,
                            color = LightDenimBlue
                        )
                        Text(
                            trip.duration,
                            fontFamily = GlacialIndifference,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = DenimBlue
                        )
                    }
                }
            },
            confirmButton = {}
        )

        if (showEditTripDialog) {
            EditTripDialog(
                trip = trip,
                onDismiss = { showEditTripDialog = false },
                onSubmit = { updatedTrip ->
                    tripViewModel.updateTrip(updatedTrip)
                    selectedTrip = updatedTrip
                    showEditTripDialog = false
                }
            )
        }
    }

    if (showChosenTransportationDialog) {
        ChosenTransportationDialog(
            showDialog = showChosenTransportationDialog,
            onDismiss = { showChosenTransportationDialog = false },
            selectedTransportationOptions = selectedTransportationOptions,
            onDelete = { transportationOption ->
                selectedTransportationOptions = selectedTransportationOptions - transportationOption // Remove the selected option from the list
                Toast.makeText(context, "Transportation option removed: ${transportationOption.name}", Toast.LENGTH_SHORT).show()
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChosenTransportationDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    selectedTransportationOptions: List<TransportationOption>,
    onDelete: (TransportationOption) -> Unit
) {
    if (!showDialog) return

    var showConfirmationDialog by remember { mutableStateOf(false) }
    var transportationOptionToDelete by remember { mutableStateOf<TransportationOption?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Close Icon",
                        tint = DenimBlue,
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable { onDismiss() } // Close the dialog when clicked
                    )
                }
                Divider(color = SkyBlue, thickness = 1.dp)
                Text(
                    "Selected Vehicle Options",
                    fontFamily = PaytoneOne,
                    fontSize = 28.sp,
                    color = SkyBlue,
                    textAlign = TextAlign.Center
                )
                Divider(color = SkyBlue, thickness = 1.dp)
            }
        },
        text = {
            Column {
                if (selectedTransportationOptions.isNotEmpty()) {
                    selectedTransportationOptions.forEachIndexed { index, option ->
                        Text(
                            text = "${index + 1}. ${option.name}",
                            fontFamily = GlacialIndifference,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .combinedClickable(
                                    onClick = {},
                                    onLongClick = {
                                        transportationOptionToDelete = option
                                        showConfirmationDialog = true
                                    }
                                )
                                .padding(8.dp)
                        )
                    }
                } else {
                    Text("No transportation options selected.")
                }
            }
        },
        confirmButton = {}
    )

    if (showConfirmationDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmationDialog = false },
            title = { Text("Confirm Deletion",
                fontFamily = PaytoneOne,
                color = DenimBlue,
                fontSize = 28.sp,
            ) },
            text = { Text("Are you sure you want to delete this transportation option?",
                fontFamily = GlacialIndifference,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            ) },
            confirmButton = {
                Button(
                    onClick = {
                        transportationOptionToDelete?.let { onDelete(it) }
                        showConfirmationDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = DenimBlue)
                ) {
                    Text(
                        "Delete",
                        fontFamily = PaytoneOne,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            },
            dismissButton = {
                Button(
                    onClick = { showConfirmationDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = LightDenimBlue)
                ) {
                    Text(
                        "Cancel",
                        fontFamily = PaytoneOne,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = DirtyWhite
                    )
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTripDialog(trip: Trip, onDismiss: () -> Unit, onSubmit: (Trip) -> Unit) {
    var destination by remember { mutableStateOf(trip.destination) }
    var startDate by remember { mutableStateOf(trip.startDate) }
    var endDate by remember { mutableStateOf(trip.endDate) }
    var countryCode by remember { mutableStateOf(trip.countryCode) }

    val context = LocalContext.current

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
                                    val updatedTrip = trip.copy(
                                        destination = destination,
                                        startDate = startDate,
                                        endDate = endDate,
                                        countryCode = countryCode
                                    )
                                    onSubmit(updatedTrip)
                                    Toast.makeText(context, "Trip updated!", Toast.LENGTH_SHORT).show()
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
                        "Edit Trip Details",
                        fontFamily = PaytoneOne,
                        fontSize = 28.sp,
                        textAlign = TextAlign.Center,
                        color = SkyBlue
                    )
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp, 8.dp, 8.dp, 0.dp),
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
                        value = destination,
                        onValueChange = { destination = it },
                        label = { Text("Destination") },
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
                        value = startDate,
                        onValueChange = { startDate = it },
                        label = { Text("Departure Date") },
                        textStyle = TextStyle(
                            fontFamily = GlacialIndifference,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = DenimBlue
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
                        value = endDate,
                        onValueChange = { endDate = it },
                        label = { Text("Return Date") },
                        textStyle = TextStyle(
                            fontFamily = GlacialIndifference,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = DenimBlue
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
                        value = countryCode,
                        onValueChange = { countryCode = it },
                        label = { Text("Country Code") },
                        textStyle = TextStyle(
                            fontFamily = GlacialIndifference,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = DenimBlue
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
        confirmButton = { }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetCalculator(budgetDetails: BudgetDetails, countryCode: String) {
    var totalBudget by remember { mutableStateOf(budgetDetails.totalBudget) }
    var expenseName by remember { mutableStateOf("") }
    var expenseCost by remember { mutableStateOf("") }
    var expenses by remember { mutableStateOf(budgetDetails.items) }
    var isExpanded by remember { mutableStateOf(false) }
    var isAddingExpense by remember { mutableStateOf(false) }



    // Get currency symbol based on the country code
    val locale = Locale("", countryCode)
    val currencySymbol = Currency.getInstance(locale).symbol

    val totalExpenses = expenses.sumOf { it.cost }
    val remainingBudget = totalBudget - expenses.sumOf { it.cost }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { isExpanded = true },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(8.dp),
            contentAlignment = Alignment.Center  // Centers the content inside the Box
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally  // Centers the content inside the Column
            ) {
                Text(
                    text = "Balance Left:",
                    fontSize = 20.sp,
                    fontFamily = PaytoneOne,
                    color = Color.White
                )
                Text(
                    text = "$currencySymbol${remainingBudget}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = GlacialIndifference,
                    color = Color.White
                )
            }
        }
    }

    // Show dialog if isExpanded is true
    if (isExpanded) {
        AlertDialog(
            onDismissRequest = { isExpanded = false },
            title = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Divider(color = SkyBlue, thickness = 1.dp)
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Budget Calculator",
                            fontSize = 28.sp,
                            fontFamily = PaytoneOne,
                            color = SkyBlue,
                        )
                    }
                    Divider(color = SkyBlue, thickness = 1.dp)
                }
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(
                        value = totalBudget.toString(),
                        onValueChange = { totalBudget = it.toDoubleOrNull() ?: 0.0 },
                        label = { Text("Total Budget",
                            fontFamily = GlacialIndifference,
                            fontSize = 16.sp
                        ) },
                        textStyle = TextStyle(
                            fontFamily = GlacialIndifference,
                            fontSize = 24.sp
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
                    Spacer(modifier = Modifier.height(8.dp))

                    if (isAddingExpense) {

                        TextField(
                            value = expenseName,
                            onValueChange = { expenseName = it },
                            label = { Text("Expense Name",
                                fontFamily = GlacialIndifference,
                                fontSize = 16.sp) },

                            textStyle = TextStyle(
                                fontFamily = GlacialIndifference,
                                fontSize = 24.sp
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

                        Spacer(modifier = Modifier.height(8.dp))

                        TextField(
                            value = expenseCost,
                            onValueChange = { expenseCost = it },
                            label = { Text("Expense Cost",
                                fontFamily = GlacialIndifference,
                                fontSize = 16.sp) },

                            textStyle = TextStyle(
                                fontFamily = GlacialIndifference,
                                fontSize = 24.sp
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

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(onClick = {
                            val cost = expenseCost.toDoubleOrNull() ?: 0.0
                            if (expenseName.isNotBlank() && cost > 0.0) {
                                expenses = expenses + BudgetItem(expenseName, cost)
                                expenseName = ""
                                expenseCost = ""
                                isAddingExpense = false
                            }
                        },     colors = ButtonDefaults.buttonColors(containerColor = SkyBlue)

                        ) {
                            Text(
                                "Add Expense",
                                fontFamily = PaytoneOne,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    if (!isAddingExpense) {
                        Button(onClick = { isAddingExpense = true },
                            colors = ButtonDefaults.buttonColors(containerColor = SkyBlue)
                        ) {
                            Text("Add More Expenses",
                                fontFamily = PaytoneOne,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = "Expenses:", fontFamily = GlacialIndifference, fontSize = 16.sp)

                    LazyColumn {
                        items(expenses) { expense ->
                            Text(text = "${expense.name}: $currencySymbol${expense.cost}",
                                fontFamily = GlacialIndifference,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Remaining Budget:",
                        fontFamily = GlacialIndifference,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "$currencySymbol${remainingBudget}",
                        fontFamily = GlacialIndifference,
                        fontSize = 20.sp
                    )
                }
            },
            confirmButton = {
                Button(onClick = { isExpanded = false },
                    colors = ButtonDefaults.buttonColors(containerColor = LightDenimBlue)
                ) {
                    Text("Close",
                        fontFamily = PaytoneOne,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White)
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PackingChecklistScreen(packingItems: List<PackingItem>, onAddItem: (String) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }
    var newItemName by remember { mutableStateOf("") }
    // State to hold the packing items
    val packingItemsState = remember { mutableStateListOf<PackingItem>().apply { addAll(packingItems) } }

    // Show dialog when the box is clicked
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally // Center aligns the contents horizontally
                ) {
                    Divider(color = SkyBlue, thickness = 1.dp)

                    Text(
                        text = "Travel Essentials List",
                        fontFamily = PaytoneOne,
                        fontSize = 24.sp,
                        color = SkyBlue
                    )

                    Divider(color = SkyBlue, thickness = 1.dp)
                }
            },

            text = {
                Column {
                    // Show all items (checked and unchecked)
                    packingItemsState.forEach { item ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = item.isChecked,
                                onCheckedChange = { checked ->
                                    // Update the item's checked state
                                    val index = packingItemsState.indexOf(item)
                                    if (index != -1) {
                                        packingItemsState[index] = item.copy(isChecked = checked)
                                    }
                                },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = SkyBlue,
                                    uncheckedColor = LightDenimBlue
                                )
                            )
                            Text(text = item.name,
                                modifier = Modifier.padding(start = 8.dp),
                                fontFamily = GlacialIndifference,
                                fontSize = 18.sp, // Adjust font size
                                color = if (item.isChecked) DenimBlue else LightDenimBlue)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Input for new item
                    TextField(
                        value = newItemName,
                        onValueChange = { newItemName = it },
                        label = { Text("Add New Item") },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = TextStyle(
                            fontFamily = GlacialIndifference,
                            fontSize = 24.sp,
                            color = DenimBlue
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

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            if (newItemName.isNotBlank()) {
                                onAddItem(newItemName)
                                packingItemsState.add(PackingItem(name = newItemName, isChecked = false)) // Add new item to state
                                newItemName = ""
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = SkyBlue)
                    ) {
                        Text("Add Item",
                            fontFamily = PaytoneOne,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { showDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = DenimBlue) // Set the background color to Denim Blue
                ) {
                    Text(
                        "Close",
                        fontFamily = PaytoneOne,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White // Text color remains white
                    )
                }
            }
        )
    }

    Box(
        modifier = Modifier
            .size(200.dp)
            .padding(8.dp)
            .background(SunsetOrange)
            .clip(RoundedCornerShape(25.dp))
            .clickable { showDialog = true },
        contentAlignment = Alignment.Center
    ) {
        when {
            packingItemsState.isEmpty() -> {
                Text(
                    text = "No packing items available.",
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontFamily = PaytoneOne,
                    fontSize = 20.sp
                )
            }
            packingItemsState.all { it.isChecked } -> {
                Text(
                    text = "All items checked!",
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontFamily = PaytoneOne,
                    fontSize = 20.sp
                )
            }
            else -> {
                Text(
                    text = "Tap here to view your packing checklist.",
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontFamily = PaytoneOne,
                    fontSize = 20.sp
                )
            }
        }
    }

}



@Composable
fun AddItemDialog(onDismiss: () -> Unit, onAddItem: (String) -> Unit) {
    var newItem by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Checklist Item") },
        text = {
            TextField(
                value = newItem,
                onValueChange = { newItem = it },
                label = { Text("Item Name") }
            )
        },
        confirmButton = {
            Button(onClick = {
                if (newItem.isNotBlank()) {
                    onAddItem(newItem)
                }
            }) {
                Text("Add")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun PackingChecklistDialog(packingChecklist: List<PackingItem>, onDismiss: () -> Unit) {
    TODO("Not yet implemented")
}

@Composable
fun BudgetPlannerDialog(budgetDetails: BudgetDetails?, onDismiss: () -> Unit) {

}

@Composable
fun TransportationDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onSubmit: (TransportationOption) -> Unit,
    transportationCategories: List<TransportationCategory>
) {
    if (!showDialog) return

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        tint = DenimBlue,
                        contentDescription = "Close Icon",
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable { onDismiss() }
                    )
                }
                Divider(color = SunsetOrange, thickness = 1.dp)
                Text(
                    "Transportation Options",
                    fontFamily = PaytoneOne,
                    fontSize = 28.sp,
                    color = SunsetOrange,
                    textAlign = TextAlign.Center
                )
                Divider(color = SunsetOrange, thickness = 1.dp)
            }
        },
        text = {
            Column {
                if (transportationCategories.isNotEmpty()) {
                    var expandedCategoryIndex by remember { mutableStateOf(-1) } // Track the currently expanded category

                    transportationCategories.forEachIndexed { index, category ->
                        val isExpanded = expandedCategoryIndex == index // Check if this category is expanded
                        Column {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        // Toggle the current category
                                        expandedCategoryIndex = if (isExpanded) -1 else index
                                    }
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = category.name,
                                    fontFamily = PaytoneOne,
                                    fontSize = 18.sp,
                                    color = DenimBlue,
                                    modifier = Modifier.weight(1f)
                                )
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = "Dropdown Icon",
                                    tint = SunsetOrange
                                )
                            }
                            // Show the options directly below the category when expanded
                            if (isExpanded) {
                                Column {
                                    category.options.forEach { option ->
                                        Text(
                                            text = option.name,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable {
                                                    onSubmit(option)
                                                    expandedCategoryIndex = -1
                                                }
                                                .padding(8.dp),
                                            color = DenimBlue,
                                            fontSize = 16.sp,
                                            fontFamily = GlacialIndifference,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }

                        }
                    }
                } else {
                    Text("No transportation options available.", modifier = Modifier.padding(16.dp))
                }
            }
        },
        confirmButton = { {
        }
        }
    )
}

@Composable
fun TripItemWithNoSelection(
    trip: Trip,
    onTripSelected: () -> Unit,
    selectedTransportationOption: TransportationOption?,
    onInfoClicked: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
    ) {
        Column(
            modifier = Modifier
                .clickable { onTripSelected() }
                .padding(16.dp)
        ) {
            Text(
                text = trip.destination,
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = GlacialIndifference,
                fontSize = 18.sp,
                color = DenimBlue
            )
            Text(
                text = "Departure Date: ${trip.startDate}",
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = GlacialIndifference,
                fontSize = 14.sp,
                color = DenimBlue
            )
            Text(
                text = "Return Date: ${trip.endDate}",
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = GlacialIndifference,
                fontSize = 14.sp,
                color = DenimBlue
            )
            Text(
                text = "Duration: ${trip.duration}",
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = GlacialIndifference,
                fontSize = 14.sp,
                color = DenimBlue
            )

            if (selectedTransportationOption != null) {
                Text(text = "Transportation: ${selectedTransportationOption.name}", color = Color.Gray)
            }

            IconButton(onClick = onInfoClicked) {
                Icon(imageVector = Icons.Default.Info, contentDescription = "Info")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTripDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onSubmit: (Trip) -> Unit,
    tripViewModel: TripViewModel,
    sharedViewModel: SharedViewModel
) {
    if (!showDialog) return

    var destination by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var countryCode by remember { mutableStateOf("") }
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
                        "Add New Trip",
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
                    value = destination,
                    onValueChange = { destination = it },
                    label = {
                        Text(
                            "Destination",
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
                    value = startDate,
                    onValueChange = { startDate = it },
                    label = {
                        Text(
                            "Departure Date",
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
                    value = endDate,
                    onValueChange = { endDate = it },
                    label = {
                        Text(
                            "Return Date",
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
                    value = countryCode,
                    onValueChange = { countryCode = it },
                    label = {
                        Text(
                            "Country Code",
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
                val trip = Trip(
                    id = UUID.randomUUID().toString(),
                    destination = destination,
                    startDate = startDate,
                    endDate = endDate,
                    countryCode = countryCode,
                    transportationOptions = emptyList(), // Initialize as empty
                    budgetDetails = BudgetDetails(0.0, emptyList()),
                    packingChecklist = emptyList()
                )
                onSubmit(trip)
                sharedViewModel.addTrip(trip) // Ensure trip is added to SharedViewModel
                Toast.makeText(context, "Trip added!", Toast.LENGTH_SHORT).show()
                onDismiss()
            },
                colors = ButtonDefaults.buttonColors(containerColor = SunsetOrange) ) {
                Text(
                    "Add Trip",
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

@Preview(showBackground = true)
@Composable
fun TripItemPreviewInFlightsScreen() {
    val trip = Trip(
        id = "1",
        destination = "Destination",
        startDate = "2022-01-01",
        endDate = "2022-01-10",
        countryCode = "PH",
        transportationOptions = emptyList(),
        budgetDetails = BudgetDetails(0.0, emptyList()),
        packingChecklist = emptyList()
    )
    JetSetGoTheme {
        TripItem(
            trip = trip,
            onTripSelected = {
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TripItemWithNoSelectionPreview() {
    val trip = Trip(
        id = "1",
        destination = "Destination",
        startDate = "2022-01-01",
        endDate = "2022-01-10",
        countryCode = "PH",
        transportationOptions = emptyList(),
        budgetDetails = BudgetDetails(0.0, emptyList()),
        packingChecklist = emptyList()
    )
    JetSetGoTheme {
        TripItemWithNoSelection(
            trip = trip,
            onTripSelected = {},
            selectedTransportationOption = null,
            onInfoClicked = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LazyRowPreview() {
    var showTransportationDialog by remember { mutableStateOf(false) }
    var showChosenTransportationDialog by remember { mutableStateOf(false) } // Ensure this is declared


    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(25.dp))
                    .background(SunsetOrange)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(6.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Bangkok City, TH",
                        modifier = Modifier.align(Alignment.Start),
                        fontFamily = PaytoneOne,
                        fontSize = 30.sp,
                        color = Color.White,
                        lineHeight = 40.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }

        }
        item {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(25.dp))
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(SunsetOrange,LightSunsetOrange),
                            start = Offset(0f, 0f), // Start at the top-left corner
                            end = Offset.Infinite // End at the bottom-right corner
                        )
                    )
            ){
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            Icon(
                                imageVector = Icons.Default.AddCircle,
                                contentDescription = "Add Icon",
                                tint = Color.White,
                                modifier = Modifier
                                    .size(24.dp)
                                    .clickable {
                                        showTransportationDialog = true
                                    }
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "Info Icon",
                                tint = Color.White,
                                modifier = Modifier
                                    .size(24.dp)
                                    .clickable {
                                        showChosenTransportationDialog = true
                                    }
                            )
                        }
                    }
                    Divider(color = Color.White,
                        thickness = 2.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )

                    Text(
                        text = "Transportation",
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        style = TextStyle(
                            fontFamily = PaytoneOne,
                            fontSize = 21.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Icon(
                        imageVector = Icons.Default.DirectionsCar,
                        contentDescription = "Transportation Icon",
                        tint = Color.White,
                        modifier = Modifier.size(80.dp)
                    )
                }
            } }
        item { Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(25.dp))
                    .background(SunsetOrange)
            ) {
                BudgetCalculator(budgetDetails = BudgetDetails(0.0, emptyList()), countryCode = "PH")
            }
        } }
        item { Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(25.dp))
                    .background(SunsetOrange)
            ) {
                PackingChecklistScreen(
                    packingItems = emptyList(),
                    onAddItem = { }
                )
            }
        } }
    }
}

@Preview(showBackground = true)
@Composable
fun TripDetailsPreview() {
    AlertDialog(
        onDismissRequest = { },
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
                                .size(20.dp),
                            tint = LightDenimBlue

                        )
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Add Icon",
                            modifier = Modifier
                                .size(20.dp),
                            tint = LightDenimBlue
                        )
                    }
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        color = LightDenimBlue
                    )
                    Text(
                        "Trip Details",
                        fontFamily = PaytoneOne,
                        fontSize = 28.sp,
                        textAlign = TextAlign.Center,
                        color = DenimBlue
                    )

                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp, 8.dp, 8.dp, 0.dp),
                        color = LightDenimBlue
                    )
                }
            }
        },
        text = {
            Box(
                modifier = Modifier
                    .width(500.dp)
                    .height(200.dp)
            ) {
                Column {
                    Text(
                        "Destination:",
                        fontFamily = GlacialIndifference,
                        fontSize = 16.sp,
                        color = LightDenimBlue
                    )

                    Text(
                        "Cubao",
                        fontFamily = GlacialIndifference,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = DenimBlue
                    )

                    Text(
                        "Departure Date: ",
                        fontFamily = GlacialIndifference,
                        fontSize = 18.sp,
                        color = LightDenimBlue
                    )
                    Text(
                        "2024-05-05",
                        fontFamily = GlacialIndifference,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = DenimBlue
                    )

                    Text(
                        "Return Date:",
                        fontFamily = GlacialIndifference,
                        fontSize = 18.sp,
                        color = LightDenimBlue
                    )
                    Text(
                        "2024-05-09",
                        fontFamily = GlacialIndifference,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = DenimBlue
                    )
                    Text(
                        "Country Code:",
                        fontFamily = GlacialIndifference,
                        fontSize = 18.sp,
                        color = LightDenimBlue
                    )
                    Text(
                        "PH",
                        fontFamily = GlacialIndifference,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = DenimBlue
                    )
                    Text(
                        "Duration:",
                        fontFamily = GlacialIndifference,
                        fontSize = 18.sp,
                        color = LightDenimBlue
                    )

                    Text(
                        "5 days and 4 nights.",
                        fontFamily = GlacialIndifference,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = DenimBlue
                    )
                }
            }
        },
        confirmButton = {
            Button(onClick = { },
                colors = ButtonDefaults.buttonColors( DarkRed)) {
                Text("CLOSE",
                    fontFamily = PaytoneOne,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White)
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ChosenTransportationDialogPreview() {
    val sampleTransportationOptions = listOf(
        TransportationOption("Commercial flights"),
        TransportationOption("Car rentals"),
        TransportationOption("Ferries")
    )

    JetSetGoTheme {
        ChosenTransportationDialog(
            showDialog = true,
            onDismiss = { /* Do nothing */ },
            selectedTransportationOptions = sampleTransportationOptions,
            onDelete = { /* Do nothing */ }
        )
    }
}






