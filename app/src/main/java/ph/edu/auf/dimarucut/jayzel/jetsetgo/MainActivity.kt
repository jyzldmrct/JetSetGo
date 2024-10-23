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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import ph.edu.auf.dimarucut.jayzel.jetsetgo.ui.theme.JetSetGoTheme
import ph.edu.auf.dimarucut.jayzel.jetsetgo.models.BudgetDetails
import ph.edu.auf.dimarucut.jayzel.jetsetgo.models.Trip


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
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf("HOME", "FLIGHTS", "HOTELS", "ACTIVITIES")
    val icons = listOf(
        R.drawable.home_icon,
        R.drawable.flight_icon,
        R.drawable.hotel_icon,
        R.drawable.activity_icon
    )

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
                        onClick = { selectedItem = index },
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
                1 -> FlightsScreen(tripViewModel)
                2 -> HotelsScreen()
                3 -> ActivitiesScreen()
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


@Composable
fun HotelsScreen(modifier: Modifier = Modifier) {
    val hotels = listOf(
        Hotel("1", "Hotel 1", "Location 1"),
        Hotel("2", "Hotel 2", "Location 2"),
        Hotel("3", "Hotel 3", "Location 3")
    )
    LazyColumn(modifier = modifier.fillMaxWidth()) {
        items(hotels) { hotel ->
            Text(text = "${hotel.name} in ${hotel.location}")
        }
    }
}

@Composable
fun ActivitiesScreen(modifier: Modifier = Modifier) {
    val activities = listOf(
        Activity("1", "Activity 1", "Description 1"),
        Activity("2", "Activity 2", "Description 2"),
        Activity("3", "Activity 3", "Description 3")
    )
    LazyColumn(modifier = modifier.fillMaxWidth()) {
        items(activities) { activity ->
            Text(text = "${activity.name}: ${activity.description}")
        }
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
fun PreviewFlightsScreen() {
    val tripViewModel = TripViewModel()
    JetSetGoTheme {
        FlightsScreen(tripViewModel)
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





