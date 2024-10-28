import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ph.edu.auf.dimarucut.jayzel.jetsetgo.api.RetrofitInstance
import ph.edu.auf.dimarucut.jayzel.jetsetgo.util.SharedPreferencesUtil

data class CurrencyResponse(val rates: Map<String, Double>)

class CurrencyViewModel : ViewModel() {
    private val _conversionResult = MutableStateFlow<Double?>(null)
    val conversionResult: StateFlow<Double?> = _conversionResult

    fun convertCurrency(amount: Double, from: String, to: String, apiKey: String, context: Context) {
        viewModelScope.launch {
            try {
                val savedRate = SharedPreferencesUtil.getString(context, "$from-$to")
                if (savedRate != null) {
                    _conversionResult.value = savedRate.toDouble() * amount
                } else {
                    val response = RetrofitInstance.api.getExchangeRates(apiKey, from, to)
                    if (response != null) {
                        val rate = response.rates[to]
                        if (rate != null) {
                            _conversionResult.value = rate * amount
                            SharedPreferencesUtil.saveString(context, "$from-$to", rate.toString())
                        } else {
                            _conversionResult.value = null
                        }
                    } else {
                        _conversionResult.value = null
                    }
                }
            } catch (e: Exception) {
                _conversionResult.value = null
            }
        }
    }
}