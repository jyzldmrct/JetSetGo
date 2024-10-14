import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import ph.edu.auf.dimarucut.jayzel.jetsetgo.models.Trip

class TripViewModel : ViewModel() {
    private val _trips = mutableStateListOf<Trip>()
    val trips: SnapshotStateList<Trip> = _trips

    fun addTrip(trip: Trip) {
        _trips.add(trip)
    }
}