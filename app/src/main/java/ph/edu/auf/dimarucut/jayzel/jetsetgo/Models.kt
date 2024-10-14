package ph.edu.auf.dimarucut.jayzel.jetsetgo.models

data class  Home (val id: String, val name: String, val destination: String)
data class Flight(val id: String, val name: String, val destination: String)
data class Hotel(val id: String, val name: String, val location: String)
data class Activity(val id: String, val name: String, val description: String)
data class Trip(
    val id: String,
    val destination: String,
    val date: String
)
