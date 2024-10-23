package ph.edu.auf.dimarucut.jayzel.jetsetgo.models

import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.UUID

data class  Main (val id: String, val name: String, val destination: String)
data class Flight(val id: String, val name: String, val destination: String)
data class Hotel(val id: String, val name: String, val location: String)
data class Activity(val id: String, val name: String, val description: String)

data class Trip(
    val id: String,
    val destination: String,
    val startDate: String,
    val endDate: String,
    val countryCode: String,
    val transportationOptions: List<TransportationOption>,
    val budgetDetails: BudgetDetails,
    val packingChecklist: List<PackingItem>,
    val selectedTransportationOption: TransportationOption? = null
) {
    val duration: String
        get() {
            val start = LocalDate.parse(startDate)
            val end = LocalDate.parse(endDate)
            val days = ChronoUnit.DAYS.between(start, end) // Don't add 1 here

            return when {
                days == 0L -> "1 day" // Same start and end date means it's a 1-day trip
                days == 1L -> "2 days and 1 night" // Start and end on consecutive days
                days == 2L -> "3 days and 2 nights"
                else -> "${days + 1} days and $days nights" // +1 for inclusive counting of days
            }
        }
}

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

data class TransportationOption(val name: String, val description: String)


