package ph.edu.auf.dimarucut.jayzel.jetsetgo.models

import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.UUID

data class Flight(val id: String, val name: String, val destination: String)

data class Trip(
    val id: String,
    val destination: String,
    val startDate: String,
    val endDate: String,
    val countryCode: String,
    val transportationOptions: List<TransportationOption>,
    val budgetDetails: BudgetDetails,
    val packingChecklist: List<PackingItem>,
    val selectedTransportationOption: TransportationOption? = null,
    val activities: List<Activity> = emptyList()
) {
    val duration: String
        get() {
            val start = LocalDate.parse(startDate)
            val end = LocalDate.parse(endDate)
            val days = ChronoUnit.DAYS.between(start, end) + 1
            val nights = days - 1

            return when {
                days == 0L -> "Same day trip"
                days == 1L -> "1 day"
                days == 2L -> "2 days and 1 night"
                else -> "$days days and $nights nights"
            }
        }
}

data class TransportationOption(
    val name: String
)

data class TransportationCategory(
    val name: String,
    val options: List<TransportationOption>
)

data class BudgetItem(
    val name: String,
    val cost: Double
)

data class BudgetDetails(
    val totalBudget: Double,
    val items: List<BudgetItem>
)

data class PackingItem(
    val name: String,
    val isChecked: Boolean = false
)

data class HotelResponse(
    val data: List<HotelData>
)

data class HotelData(
    val hotel: Hotel
)

data class Hotel(
    val name: String,
    val address: Address
)

data class Address(
    val street: String,
    val city: String,
    val country: String
)

data class Accommodation(
    val name: String,
    val address: String,
    val contactInfo: String,
    val checkInDate: String,
    val checkOutDate: String,
    val reservationNumber: String
)

data class Activity(
    val id: String,
    val category: String,
    val name: String,
    val date: String,
    val time: String,
    val description: String,
    val destination: String,
    val countryCode: String
)

data class WeatherResponse(
    val main: Main,
    val weather: List<Weather>
)

data class Main(
    val temp: Double
)

data class Weather(
    val description: String
)