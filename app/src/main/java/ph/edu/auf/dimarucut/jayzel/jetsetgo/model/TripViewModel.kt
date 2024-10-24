package ph.edu.auf.dimarucut.jayzel.jetsetgo.model

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ph.edu.auf.dimarucut.jayzel.jetsetgo.models.PackingItem
import ph.edu.auf.dimarucut.jayzel.jetsetgo.models.Trip
import ph.edu.auf.dimarucut.jayzel.jetsetgo.models.TransportationOption

class TripViewModel : ViewModel() {
    private val _trips = MutableStateFlow<List<Trip>>(emptyList())
    val trips: StateFlow<List<Trip>> = _trips


    fun addTrip(trip: Trip) {
        _trips.value = _trips.value + trip
    }

    fun addTransportationOption(tripId: String, transportationOption: TransportationOption) {
        // Find the trip by ID and add the transportation option
        _trips.value = _trips.value.map { trip ->
            if (trip.id == tripId) {
                trip.copy(transportationOptions = trip.transportationOptions + transportationOption)
            } else {
                trip
            }
        }
    }

    fun updatePackingChecklist(tripId: String, updatedChecklist: List<PackingItem>) {
        _trips.value = _trips.value.map { trip ->
            if (trip.id == tripId) {
                trip.copy(packingChecklist = updatedChecklist)
            } else {
                trip
            }
        }
    }


    fun updateTrip(updatedTrip: Trip) {
        _trips.value = _trips.value.map { trip ->
            if (trip.id == updatedTrip.id) {
                updatedTrip
            } else {
                trip
            }
        }
    }
}


