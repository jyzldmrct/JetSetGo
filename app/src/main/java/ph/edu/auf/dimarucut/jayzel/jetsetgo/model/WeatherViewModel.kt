import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ph.edu.auf.dimarucut.jayzel.jetsetgo.api.WeatherRetrofitInstance
import ph.edu.auf.dimarucut.jayzel.jetsetgo.models.WeatherResponse

class WeatherViewModel : ViewModel() {
    private val _weather = MutableStateFlow<WeatherResponse?>(null)
    val weather: StateFlow<WeatherResponse?> = _weather

    fun fetchWeather(city: String, apiKey: String) {
        viewModelScope.launch {
            try {
                val response = WeatherRetrofitInstance.api.getWeather(city, apiKey)
                _weather.value = response
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}