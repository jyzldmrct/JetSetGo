package ph.edu.auf.dimarucut.jayzel.jetsetgo

import ph.edu.auf.dimarucut.jayzel.jetsetgo.model.TripViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import java.util.UUID


class MainActivity : ComponentActivity() {
    private val tripViewModel: TripViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetSetGoTheme {
                MainScreen(tripViewModel)
            }
        }
    }
}

@Composable
fun MainScreen(tripViewModel: TripViewModel) {
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf("Home", "Flights", "Hotels", "Activities")
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
                    .padding(16.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .height(56.dp)
            ) {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    painter = painterResource(id = icons[index]),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                if (selectedItem == index) {
                                    Text(
                                        text = item,
                                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp)
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
        when (selectedItem) {
            0 -> HomeScreen(tripViewModel, Modifier.padding(innerPadding))
            1 -> FlightsScreen(tripViewModel,Modifier.padding(innerPadding))
            2 -> HotelsScreen(Modifier.padding(innerPadding))
            3 -> ActivitiesScreen(Modifier.padding(innerPadding))
        }
    }
}

@Composable
fun HomeScreen(tripViewModel: TripViewModel, modifier: Modifier = Modifier) {
    val trips by tripViewModel.trips.collectAsState() // Collecting state

    LazyColumn(modifier = modifier) {
        item {
            Text(
                text = "Hello Traveler!",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 0.dp),
                fontFamily = PaytoneOne,
                color = SunsetOrange
            )
        }
        item {
            Text(
                text = "Let's plan your next trip.",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(16.dp, 0.dp, 16.dp, 16.dp),
                fontFamily = GlacialIndifference,
                fontSize = 18.sp,
            )
        }
        item { UpcomingTripsSection(trips = trips) }
        item { QuickLinksSection() }
        item { TravelStatsSection() }
        item { WeatherSection() }
    }
}


@Composable
fun FlightsScreen(tripViewModel: TripViewModel, modifier: Modifier = Modifier) {
    val trips by tripViewModel.trips.collectAsState()
    Column(modifier = modifier.fillMaxSize()) {
        Text(
            text = "Flights",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )

        AddTripSection(tripViewModel) // Include the trip input section

        // Check if trips are empty and show message or list
        if (trips.isEmpty()) {
            Text(text = "No upcoming trips", style = MaterialTheme.typography.bodyMedium)
        } else {
            LazyColumn {
                items(trips) { trip ->
                    TripItem(trip = trip) // Ensure 'trip' is of type 'Trip'
                }
            }
        }
    }
}

@Composable
fun AddTripSection(tripViewModel: TripViewModel) {
    var destination by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = destination,
            onValueChange = { destination = it },
            label = { Text("Destination") }
        )
        TextField(
            value = date,
            onValueChange = { date = it },
            label = { Text("Date") }
        )
        Button(onClick = {
            // Ensure this is called only when both fields are filled
            if (destination.isNotBlank() && date.isNotBlank()) {
                tripViewModel.addTrip(Trip(id = UUID.randomUUID().toString(), destination = destination, date = date))
                destination = ""
                date = ""
            }
        }) {
            Text("Add Trip")
        }
    }
}


@Composable
fun TripItem(trip: Trip) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Destination: ${trip.destination}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Date: ${trip.date}", style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun UpcomingTripsSection(trips: List<Trip>) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Your Upcoming Trips", style = MaterialTheme.typography.headlineSmall)

        if (trips.isEmpty()) {
            Text(
                text = "No upcoming trips",
                style = MaterialTheme.typography.bodyMedium
            )
        } else {
            LazyColumn {
                items(trips) { trip -> // Ensure 'trip' is of type 'Trip'
                    TripItem(trip = trip)
                }
            }
        }
    }
}


@Composable
fun QuickLinksSection() {
    Text(text = "Quick Links")
    // Add your quick links content here
}

@Composable
fun TravelStatsSection() {
    Text(text = "Your Travel Stats")
    // Add your travel stats content here
}

@Composable
fun WeatherSection() {
    Text(text = "Weather")
    // Add your weather content here
}

@Composable
fun HotelsScreen(modifier: Modifier = Modifier) {
    val hotels = listOf(
        Hotel("1", "Hotel 1", "Location 1"),
        Hotel("2", "Hotel 2", "Location 2"),
        Hotel("3", "Hotel 3", "Location 3")
    )
    LazyColumn(modifier = modifier) {
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
    LazyColumn(modifier = modifier) {
        items(activities) { activity ->
            Text(text = "${activity.name}: ${activity.description}")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetSetGoTheme {
        MainScreen(TripViewModel())
    }
}