package ph.edu.auf.dimarucut.jayzel.jetsetgo

import ph.edu.auf.dimarucut.jayzel.jetsetgo.model.TripViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ph.edu.auf.dimarucut.jayzel.jetsetgo.models.*
import ph.edu.auf.dimarucut.jayzel.jetsetgo.ui.theme.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ph.edu.auf.dimarucut.jayzel.jetsetgo.model.SharedViewModel
import ph.edu.auf.dimarucut.jayzel.jetsetgo.ui.theme.JetSetGoTheme
import ph.edu.auf.dimarucut.jayzel.jetsetgo.models.BudgetDetails
import ph.edu.auf.dimarucut.jayzel.jetsetgo.models.Trip
import ph.edu.auf.dimarucut.jayzel.jetsetgo.util.SharedPreferencesUtil

class MainActivity : ComponentActivity() {

    private val tripViewModel: TripViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(tripViewModel = tripViewModel)
        }
    }
}

@Composable
fun MainScreen(tripViewModel: TripViewModel) {
    val sharedViewModel: SharedViewModel = viewModel()
    var selectedItem by remember { mutableStateOf(0) }
    var showAddTripDialog by remember { mutableStateOf(false) }
    var destination by remember { mutableStateOf("") }
    var countryCode by remember { mutableStateOf("") }
    val items = listOf("HOME", "FLIGHTS", "HOTELS", "ACTIVITIES")
    val icons = listOf(
        R.drawable.home_icon,
        R.drawable.flight_icon,
        R.drawable.hotel_icon,
        R.drawable.activity_icon
    )

     val onAddTrip: (String, String) -> Unit = { dest, code ->
        destination = dest
        countryCode = code
    }

    val context = LocalContext.current

    selectedItem = SharedPreferencesUtil.getString(context, "last_selected_item")?.toInt() ?: 0


    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = DenimBlue,
                tonalElevation = 5.dp,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .height(56.dp)
            ) {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.padding(8.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = icons[index]),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                if (selectedItem == index) {
                                    Text(
                                        text = item,
                                        fontSize = 14.sp,
                                        fontFamily = PaytoneOne,
                                        color = Color.White
                                    )
                                }
                            }
                        },
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                            // Save the selected item to SharedPreferences
                            SharedPreferencesUtil.saveString(context, "last_selected_item", index.toString())
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.White,
                            unselectedIconColor = Color.LightGray,
                            selectedTextColor = Color.White,
                            unselectedTextColor = Color.LightGray,
                            indicatorColor = Color.Transparent
                        ),
                        alwaysShowLabel = false,
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedItem) {
                0 -> HomeScreen()
                1 -> FlightsScreen(tripViewModel, sharedViewModel)
                2 -> HotelsScreen(sharedViewModel)
                3 -> ActivitiesScreen(sharedViewModel)
            }
        }
    }
}

@Composable
fun TripItem(
    trip: Trip,
    onTripSelected: (Trip) -> Unit,
    modifier: Modifier = Modifier
){
    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onTripSelected(trip) }, // Trigger onTripSelected when clicked
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = trip.destination, style = MaterialTheme.typography.titleMedium)
                Text(text = "Start Date: ${trip.startDate}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "End Date: ${trip.endDate}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Duration: ${trip.duration}", style = MaterialTheme.typography.bodyMedium)
            }
            IconButton(onClick = { showDialog = true }) {
                Icon(imageVector = Icons.Default.Info, contentDescription = "Info")
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Trip Details") },
            text = {
                Column {
                    Text(text = "Destination: ${trip.destination}")
                    Text(text = "Start Date: ${trip.startDate}")
                    Text(text = "End Date: ${trip.endDate}")
                    Text(text = "Duration: ${trip.duration}")
                    Text(text = "Country Code: ${trip.countryCode}")
                }
            },
            confirmButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Close")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val tripViewModel = TripViewModel()
    JetSetGoTheme {
        MainScreen(tripViewModel)
    }
}


@Preview(showBackground = true)
@Composable
fun TripItemPreview() {
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




