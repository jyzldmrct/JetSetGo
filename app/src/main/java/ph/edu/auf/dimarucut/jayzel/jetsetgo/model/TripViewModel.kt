package ph.edu.auf.dimarucut.jayzel.jetsetgo.model

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ph.edu.auf.dimarucut.jayzel.jetsetgo.models.PackingItem
import ph.edu.auf.dimarucut.jayzel.jetsetgo.models.Trip
import ph.edu.auf.dimarucut.jayzel.jetsetgo.models.TransportationOption

class TripViewModel : ViewModel() {
    private val _trips = MutableStateFlow<List<Trip>>(emptyList())
    val trips: StateFlow<List<Trip>> = _trips

    fun getTransportationOptions(tripId: String): List<TransportationOption> {
        val trip = _trips.value.find { it.id == tripId }
        return trip?.transportationOptions ?: listOf(
            TransportationOption("Air Travel", "Commercial flights, charter flights, private jets"),
            TransportationOption("Ground Transportation", "Car rentals, taxis, buses, trains, motorbikes"),
            TransportationOption("Water Travel", "Ferries, cruises, yachts, speedboats"),
            TransportationOption("Public Transport", "City buses, subways, trams"),
            TransportationOption("Specialty", "Cable cars, tuk-tuks, horse-drawn carriages")
        )
    }


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
}

