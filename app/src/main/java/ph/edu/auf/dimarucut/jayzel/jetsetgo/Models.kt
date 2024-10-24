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



