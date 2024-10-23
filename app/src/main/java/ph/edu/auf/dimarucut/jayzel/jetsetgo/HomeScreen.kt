package ph.edu.auf.dimarucut.jayzel.jetsetgo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ph.edu.auf.dimarucut.jayzel.jetsetgo.models.Trip
import ph.edu.auf.dimarucut.jayzel.jetsetgo.ui.theme.*

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 56.dp)
        ) {
            item {
                Text(
                    text = "Hello Traveler!",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 0.dp),
                    fontFamily = PaytoneOne,
                    color = SunsetOrange
                )
            }

            item {
                Text(
                    text = "Let's plan your next trip.",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(16.dp, 0.dp, 16.dp, 0.dp),
                    fontFamily = GlacialIndifference,
                    fontSize = 18.sp,
                )
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Your Upcoming Trips",
                        style = MaterialTheme.typography.headlineLarge,
                        fontFamily = PaytoneOne,
                        fontSize = 24.sp,
                        color = DenimBlue,
                        textAlign = TextAlign.Center
                    )
                }
            }

        }
    }
}

@Composable
fun UpcomingTripsSection(trips: List<Trip>) {
        TODO("Not yet implemented")
}
