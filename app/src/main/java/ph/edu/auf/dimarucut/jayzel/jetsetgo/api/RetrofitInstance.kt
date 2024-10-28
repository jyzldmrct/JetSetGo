package ph.edu.auf.dimarucut.jayzel.jetsetgo.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.exchangerate-api.com/v4/latest/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: ConversionApiService by lazy {
        retrofit.create(ConversionApiService::class.java)
    }
}