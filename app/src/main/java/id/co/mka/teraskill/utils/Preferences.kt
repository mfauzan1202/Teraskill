package id.co.mka.teraskill.utils

import android.content.Context
import android.content.SharedPreferences

class Preferences (val context: Context) {
    companion object{
        const val USER_PREFF = "user_preff"
    }

    var sharedPreferences: SharedPreferences = context.getSharedPreferences(USER_PREFF, 0)

    fun setValues(key : String, value : String) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getValues(key: String) : String?{
        return sharedPreferences.getString(key, "")
    }

    fun clearValues() {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}