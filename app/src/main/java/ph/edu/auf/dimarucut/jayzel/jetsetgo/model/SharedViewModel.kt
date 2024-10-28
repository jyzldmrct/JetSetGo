package ph.edu.auf.dimarucut.jayzel.jetsetgo.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ph.edu.auf.dimarucut.jayzel.jetsetgo.models.Activity
import ph.edu.auf.dimarucut.jayzel.jetsetgo.models.PackingItem
import ph.edu.auf.dimarucut.jayzel.jetsetgo.models.Trip

class SharedViewModel : ViewModel() {

    fun addActivityToTrip(tripId: String, activity: Activity) {
        val updatedTrips = _trips.value?.map { trip ->
            if (trip.id == tripId) {
                trip.copy(activities = trip.activities + activity)
            } else {
                trip
            }
        }
        _trips.value = updatedTrips
    }

    private val _trips = MutableLiveData<List<Trip>>(emptyList())
    val trips: LiveData<List<Trip>> get() = _trips

    private val _destination = MutableLiveData<String>()
    val destination: LiveData<String> get() = _destination

    private val _countryCode = MutableLiveData<String>()
    val countryCode: LiveData<String> get() = _countryCode

    fun addTrip(trip: Trip) {
        _trips.value = _trips.value?.plus(trip)
    }

    fun updateTrip(updatedTrip: Trip) {
        _trips.value = _trips.value?.map { trip ->
            if (trip.id == updatedTrip.id) updatedTrip else trip
        }
    }

    fun setDestination(destination: String) {
        _destination.value = destination
    }

    fun setCountryCode(countryCode: String) {
        _countryCode.value = countryCode
    }

    // In `SharedViewModel.kt`
fun updatePackingChecklist(tripId: String, updatedChecklist: List<PackingItem>) {
    _trips.value = _trips.value?.map { trip ->
        if (trip.id == tripId) {
            trip.copy(packingChecklist = updatedChecklist)
        } else {
            trip
        }
    }
}
}