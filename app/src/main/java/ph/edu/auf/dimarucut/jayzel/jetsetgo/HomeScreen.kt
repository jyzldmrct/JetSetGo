package ph.edu.auf.dimarucut.jayzel.jetsetgo

import CurrencyViewModel
import WeatherViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ph.edu.auf.dimarucut.jayzel.jetsetgo.models.Trip
import ph.edu.auf.dimarucut.jayzel.jetsetgo.ui.theme.*
import ph.edu.auf.dimarucut.jayzel.jetsetgo.util.SharedPreferencesUtil
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    weatherViewModel: WeatherViewModel = viewModel(),
    currencyViewModel: CurrencyViewModel = viewModel()
) {
    var city by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var fromCurrency by remember { mutableStateOf("PHP") }
    var toCurrency by remember { mutableStateOf("USD") }
    val weatherState by weatherViewModel.weather.collectAsState()
    val conversionResult by currencyViewModel.conversionResult.collectAsState()
    var isConversionButtonPressed by remember { mutableStateOf(false) }
    var showWeatherDialog by remember { mutableStateOf(false) }
    var showConversionDialog by remember { mutableStateOf(false) }
    var isAmountZero by remember { mutableStateOf(false) }
    var showAmountZeroDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // Initialize city from SharedPreferences only once
    LaunchedEffect(Unit) {
        city = SharedPreferencesUtil.getString(context, "last_searched_city") ?: ""
    }

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
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
                    text = "Get real-time exchange rates and weather updates to plan your trip with ease.",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(16.dp, 0.dp, 16.dp, 0.dp),
                    fontFamily = GlacialIndifference,
                    fontSize = 18.sp,
                    lineHeight = 20.sp,
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = DirtyWhite),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        TextField(
                            value = city,
                            onValueChange = { city = it },
                            label = {
                                Text(
                                    "Enter City",
                                    fontFamily = GlacialIndifference,
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(top = 0.dp)
                                )
                            },
                            shape = RoundedCornerShape(8.dp),
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = Color.Transparent,
                                focusedIndicatorColor = SunsetOrange,
                                unfocusedIndicatorColor = LightDenimBlue,
                                focusedLabelColor = SunsetOrange,
                                unfocusedLabelColor = LightDenimBlue,
                                cursorColor = DenimBlue
                            ),
                            textStyle = TextStyle(
                                fontFamily = GlacialIndifference,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = DenimBlue
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Box(modifier = Modifier.fillMaxWidth()) {
                            Button(
                                onClick = {
                                    weatherViewModel.fetchWeather(
                                        city,
                                        "8b9290da0228ad4e99bc79358e2c70b8"
                                    )
                                    SharedPreferencesUtil.saveString(
                                        context,
                                        "last_searched_city",
                                        city
                                    )
                                    showWeatherDialog = true
                                },
                                modifier = Modifier.align(Alignment.CenterEnd),
                                colors = ButtonDefaults.buttonColors(containerColor = SkyBlue)
                            ) {
                                Text(
                                    "Get Weather",
                                    fontFamily = PaytoneOne,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = DirtyWhite),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {

                TextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Enter Amount",
                        fontFamily = GlacialIndifference,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 0.dp)
                    )
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = SunsetOrange,
                        unfocusedIndicatorColor = LightDenimBlue,
                        focusedLabelColor = SunsetOrange,
                        unfocusedLabelColor = LightDenimBlue,
                        cursorColor = DenimBlue
                    ),
                    textStyle = TextStyle(
                        fontFamily = GlacialIndifference,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = DenimBlue
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = fromCurrency,
                    onValueChange = { fromCurrency = it },
                    label = { Text("From Currency",
                        fontFamily = GlacialIndifference,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 0.dp)
                    )
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = SunsetOrange,
                        unfocusedIndicatorColor = LightDenimBlue,
                        focusedLabelColor = SunsetOrange,
                        unfocusedLabelColor = LightDenimBlue,
                        cursorColor = DenimBlue
                    ),
                    textStyle = TextStyle(
                        fontFamily = GlacialIndifference,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = DenimBlue
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = toCurrency,
                    onValueChange = { toCurrency = it },
                    label = { Text("To Currency",
                        fontFamily = GlacialIndifference,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 0.dp)
                    )
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = SunsetOrange,
                        unfocusedIndicatorColor = LightDenimBlue,
                        focusedLabelColor = SunsetOrange,
                        unfocusedLabelColor = LightDenimBlue,
                        cursorColor = DenimBlue
                    ),
                    textStyle = TextStyle(
                        fontFamily = GlacialIndifference,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = DenimBlue
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))

                Box(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = {
                            val amountDouble = amount.toDoubleOrNull() ?: 0.0
                            if (amountDouble == 0.0) {
                                isAmountZero = true
                            } else {
                                isAmountZero = false
                                currencyViewModel.convertCurrency(amountDouble, fromCurrency, toCurrency, "d5dab6f1c99f0677f20a8513e0673a40", context)
                                isConversionButtonPressed = true
                                showConversionDialog = true
                            }
                        },
                        modifier = Modifier.align(Alignment.CenterEnd),
                        colors = ButtonDefaults.buttonColors(containerColor = SkyBlue)
                    ) {
                        Text("Convert Currency",
                            fontFamily = PaytoneOne,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                }
                    Spacer(modifier = Modifier.height(8.dp))
            }
            }
        }
    }

    if (showWeatherDialog) {
        weatherState?.let { weather ->
            WeatherInformationDialog(
                weather = "Temperature: ${weather.main.temp}°C, ${weather.weather[0].description}",
                onDismiss = { showWeatherDialog = false }
            )
        }
    }

    if (showConversionDialog) {
        conversionResult?.let { result ->
            ConversionResultDialog(
                result = result.toString(),
                onDismiss = { showConversionDialog = false }
            )
        }
    }

    if (showAmountZeroDialog) {
        AmountZeroDialog(onDismiss = { showAmountZeroDialog = false })
    }
}


@Composable
fun AmountZeroDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Error", style = MaterialTheme.typography.headlineLarge, color = SunsetOrange)
        },
        text = {
            Text(text = "Input a number first", style = MaterialTheme.typography.bodyLarge, color = Color.Red)
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("OK")
            }
        }
    )
}

@Composable
fun ConversionResultDialog(result: String, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Conversion Result", style = MaterialTheme.typography.headlineLarge, color = SunsetOrange)
        },
        text = {
            Column {
                Text(text = "Converted Amount: $result", style = MaterialTheme.typography.bodyLarge, color = DenimBlue)
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("OK")
            }
        }
    )
}

@Composable
fun WeatherInformationDialog(weather: String, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Weather Information",
                fontFamily = PaytoneOne,
                fontSize = 28.sp,
                textAlign = TextAlign.Center,
                color = SunsetOrange)
        },
        text = {
            Column {
                Text(text = weather, style = MaterialTheme.typography.bodyLarge, color = DenimBlue)
                Spacer(modifier = Modifier.height(8.dp))
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("OK")
            }
        }
    )
}


