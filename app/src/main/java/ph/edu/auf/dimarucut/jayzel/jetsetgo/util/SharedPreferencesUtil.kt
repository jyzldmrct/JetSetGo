package ph.edu.auf.dimarucut.jayzel.jetsetgo.util

import android.content.Context

object SharedPreferencesUtil {
    private const val PREFERENCES_FILE_KEY = "ph.edu.auf.dimarucut.jayzel.jetsetgo.PREFERENCE_FILE_KEY"

    fun saveString(context: Context, key: String, value: String) {
        val sharedPref = context.getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString(key, value)
            apply() // Use apply() for asynchronous saving
        }
    }

    fun getString(context: Context, key: String, defaultValue: String? = null): String? {
        val sharedPref = context.getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)
        return sharedPref.getString(key, defaultValue)
    }
}