package ph.edu.auf.dimarucut.jayzel.jetsetgo.model

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.StateFlow
import ph.edu.auf.dimarucut.jayzel.jetsetgo.models.Trip

class TripViewModel : ViewModel() {
    // Define the MutableStateFlow for trips
    private val _trips = MutableStateFlow<List<Trip>>(emptyList())
    val trips: StateFlow<List<Trip>> = _trips.asStateFlow()

    // Function to add a trip
    fun addTrip(trip: Trip) {
        _trips.value = _trips.value + trip // Update the trip list
    }
}
