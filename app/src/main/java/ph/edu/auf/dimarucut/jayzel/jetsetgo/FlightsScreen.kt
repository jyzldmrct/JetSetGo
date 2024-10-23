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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
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


@Composable
fun FlightsScreen(tripViewModel: TripViewModel, modifier: Modifier = Modifier) {

    val trips by tripViewModel.trips.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var showTripDialog by remember { mutableStateOf(false) }
    var showTransportationDialog by remember { mutableStateOf(false) }
    val selectedTripId by remember { mutableStateOf<String?>(null) }
    var showBudgetDialog by remember { mutableStateOf(false) }
    var showPackingDialog by remember { mutableStateOf(false) }
    var budgetDetails: BudgetDetails? by remember { mutableStateOf(null) }
    var packingChecklist: List<PackingItem> by remember { mutableStateOf(emptyList()) }
    var transportationOptions: List<TransportationOption> by remember { mutableStateOf(emptyList()) }
    var selectedTransportationOption: TransportationOption? by remember { mutableStateOf(null) }
    var selectedTrip by remember { mutableStateOf<Trip?>(null) }
    var showEditTripDialog by remember { mutableStateOf(false) }
    val trip = selectedTrip

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
                            .align(Alignment.TopEnd)
                            .offset(y = (-4).dp),
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
                        Toast.makeText(context, "Trip added!", Toast.LENGTH_SHORT).show() // Use context here
                    },
                    tripViewModel = tripViewModel
                )
            }
        }

        if (trips.isEmpty()) {
            item {
                Text(text = "No upcoming trips", style = MaterialTheme.typography.bodyMedium)
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
                            modifier = boxModifier.clickable(onClick = {
                                selectedTrip = trip
                                showTripDialog = true
                            })
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    text = "${trip.destination}, ${trip.countryCode}",
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
                        Box(modifier = boxModifier) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Transportation Options",
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                trip.transportationOptions.forEach { option ->
                                    Text(
                                        text = option.name,
                                        modifier = Modifier.align(Alignment.CenterHorizontally)
                                    )
                                }
                            }
                        }
                    }
                    item {
                        Box(modifier = boxModifier) {
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

    if (showTransportationDialog && selectedTripId != null) {
        AddTransportationDialog(
            showDialog = showTransportationDialog,
            onDismiss = { showTransportationDialog = false },
            onSubmit = { transportationOption ->
                selectedTripId?.let { tripId ->
                    tripViewModel.addTransportationOption(tripId, transportationOption)
                    selectedTransportationOption = transportationOption
                    Toast.makeText(context, "Transportation option added!", Toast.LENGTH_SHORT).show()
                }
                showTransportationDialog = false
            },
            transportationOptions = transportationOptions
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
                            "${trip.duration}",
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
                            tint = DenimBlue
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
                            focusedIndicatorColor = DenimBlue,
                            unfocusedIndicatorColor = LightDenimBlue,
                            focusedLabelColor = LightDenimBlue,
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
                            focusedIndicatorColor = DenimBlue,
                            unfocusedIndicatorColor = LightDenimBlue,
                            focusedLabelColor = LightDenimBlue,
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
                            focusedIndicatorColor = DenimBlue,
                            unfocusedIndicatorColor = LightDenimBlue,
                            focusedLabelColor = LightDenimBlue,
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
                            focusedIndicatorColor = DenimBlue,
                            unfocusedIndicatorColor = LightDenimBlue,
                            focusedLabelColor = LightDenimBlue,
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
    val remainingBudget = totalBudget - totalExpenses

    // Box that shows the summary and triggers the dialog
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .clickable { isExpanded = true },
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Remaining Budget: $currencySymbol${remainingBudget}", fontSize = 18.sp, fontWeight = FontWeight.Bold)
    }

    // Show dialog if isExpanded is true
    if (isExpanded) {
        AlertDialog(
            onDismissRequest = { isExpanded = false },
            title = {
                Text(text = "Budget Calculator", fontSize = 20.sp, fontWeight = FontWeight.Bold)
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
                        label = { Text("Total Budget") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    if (isAddingExpense) {
                        // Show expense input fields only if adding an expense
                        TextField(
                            value = expenseName,
                            onValueChange = { expenseName = it },
                            label = { Text("Expense Name") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        TextField(
                            value = expenseCost,
                            onValueChange = { expenseCost = it },
                            label = { Text("Expense Cost ($currencySymbol)") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(onClick = {
                            val cost = expenseCost.toDoubleOrNull() ?: 0.0
                            if (expenseName.isNotBlank() && cost > 0.0) {
                                expenses = expenses + BudgetItem(expenseName, cost)
                                totalBudget -= cost
                                expenseName = ""
                                expenseCost = ""
                                isAddingExpense = false
                            }
                        }) {
                            Text("Add Expense")
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    // Only show "Add More Expenses" button if not currently adding an expense
                    if (!isAddingExpense) {
                        Button(onClick = { isAddingExpense = true }) {
                            Text("Add More Expenses")
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = "Expenses:", fontSize = 18.sp, fontWeight = FontWeight.Bold)

                    LazyColumn {
                        items(expenses) { expense ->
                            Text(text = "${expense.name}: $currencySymbol${expense.cost}")
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = "Remaining Budget: $currencySymbol${remainingBudget}", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            },
            confirmButton = {
                Button(onClick = { isExpanded = false }) {
                    Text("Close")
                }
            }
        )
    }
}

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
            title = { Text("Travel Essentials List") },
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
                                }
                            )
                            Text(text = item.name)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Input for new item
                    TextField(
                        value = newItemName,
                        onValueChange = { newItemName = it },
                        label = { Text("Add New Item") },
                        modifier = Modifier.fillMaxWidth()
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
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Add Item")
                    }
                }
            },
            confirmButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Close")
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
        if (packingItemsState.all { it.isChecked }) {
            Text(
                text = "All items checked!",
                modifier = Modifier.align(Alignment.Center),
                textAlign = TextAlign.Center,
                color = Color.Gray
            )
        } else {
            Text(
                text = "Tap here to view your packing checklist.",
                modifier = Modifier.align(Alignment.Center),
                textAlign = TextAlign.Center,
                color = Color.Gray
            )
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
fun AddTransportationDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onSubmit: (TransportationOption) -> Unit,
    transportationOptions: List<TransportationOption>
) {
    if (!showDialog) return

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Transportation Option") },
        text = {
            Column {
                LazyColumn {
                    items(transportationOptions) { option ->
                        Text(
                            text = option.name,
                            modifier = Modifier
                                .clickable {
                                    onSubmit(option)
                                }
                                .padding(8.dp)
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Close")
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

@Composable
fun AddTripDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onSubmit: (Trip) -> Unit,
    tripViewModel: TripViewModel
) {
    if (!showDialog) return

    var destination by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var countryCode by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                "Add New Trip",
                fontFamily = PaytoneOne,
                fontSize = 32.sp,
                color = DenimBlue
            )
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
                            fontSize = 16.sp
                        )
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = LightGray,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    ),
                    textStyle = TextStyle(fontFamily = GlacialIndifference)
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
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = LightGray,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    ),
                    textStyle = TextStyle(fontFamily = GlacialIndifference)
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
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = LightGray,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    ),
                    textStyle = TextStyle(fontFamily = GlacialIndifference)
                )
                TextField(
                    value = countryCode,
                    onValueChange = { countryCode = it },
                    label = {
                        Text(
                            "Country Code",
                            fontFamily = GlacialIndifference, // Apply custom font
                            fontSize = 16.sp
                        )
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = LightGray,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    ),
                    textStyle = TextStyle(fontFamily = GlacialIndifference) // Apply custom font
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
                    budgetDetails = BudgetDetails(
                        totalBudget = 0.0,
                        items = emptyList()
                    ),
                    packingChecklist = emptyList()
                )
                onSubmit(trip)
                onDismiss()
            },
                colors = ButtonDefaults.buttonColors(containerColor = DarkGreen) ) {
                Text(
                    "ADD TRIP",
                    fontFamily = PaytoneOne, // Apply custom font
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        },
        dismissButton = {
            Button(onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = DarkRed) ) {
                Text(
                    "CANCEL",
                    fontFamily = PaytoneOne,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White

                )
            }
        }
    )
}


@Preview
@Composable
fun FlightsScreenPreview() {
    val tripViewModel = TripViewModel()
    FlightsScreen(tripViewModel)
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
        item { Box(
            modifier = Modifier
                .size(200.dp)
                .padding(8.dp)
                .clip(RoundedCornerShape(25.dp))
                .background(SunsetOrange)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Transportation Options", modifier = Modifier.align(Alignment.CenterHorizontally))
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Option 1", modifier = Modifier.align(Alignment.CenterHorizontally))
                Text(text = "Option 2", modifier = Modifier.align(Alignment.CenterHorizontally))
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

@Preview
@Composable
fun AddTripDialogPreview() {
    val tripViewModel = TripViewModel()
    AddTripDialog(
        showDialog = true,
        onDismiss = { },
        onSubmit = { },
        tripViewModel = tripViewModel
    )
}

@Preview(showBackground = true)
@Composable
fun EditTripDialogPreview() {
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
    EditTripDialog(
        trip = trip,
        onDismiss = { },
        onSubmit = { }
    )
}