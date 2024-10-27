package ph.edu.auf.dimarucut.jayzel.jetsetgo.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ph.edu.auf.dimarucut.jayzel.jetsetgo.models.Trip

class SharedViewModel : ViewModel() {

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
}