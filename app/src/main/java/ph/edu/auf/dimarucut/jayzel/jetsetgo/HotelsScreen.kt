package ph.edu.auf.dimarucut.jayzel.jetsetgo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import ph.edu.auf.dimarucut.jayzel.jetsetgo.model.SharedViewModel
import ph.edu.auf.dimarucut.jayzel.jetsetgo.models.Trip

@Composable
fun HotelsScreen(sharedViewModel: SharedViewModel) {
    val tripsState = sharedViewModel.trips.observeAsState(emptyList())
    val trips = tripsState.value

    LazyColumn {
        items(trips) { trip ->
            TripItem(trip = trip)
        }
    }
}

@Composable
fun TripItem(trip: Trip) {
    Column {
        Text(text = "Destination: ${trip.destination}")
        Text(text = "Country Code: ${trip.countryCode}")
        // Add more UI elements as needed
    }
}