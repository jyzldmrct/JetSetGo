package ph.edu.auf.dimarucut.jayzel.jetsetgo.api

import CurrencyResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ConversionApiService {
    @GET("latest")
    suspend fun getExchangeRates(
        @Query("access_key") apiKey: String,
        @Query("base") baseCurrency: String,  // Specify base currency
        @Query("symbols") targetCurrency: String
    ): CurrencyResponse
}
