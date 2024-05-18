package com.gianghv.android.util.app

import android.util.Patterns
import androidx.fragment.app.FragmentManager
import com.gianghv.android.views.popup.ViewImagePopup
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object AppUtils {

    fun isValidatedName(name: String?): Boolean {
        return !name.isNullOrEmpty()
    }

    fun isValidatedEmail(email: String?): Boolean {
        return !email.isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidatedPassword(password: String?): Boolean {
        return !password.isNullOrEmpty() && password.length >= 6
    }

    fun isValidatedConfirmPass(password: String?, confirmPass: String?): Boolean {
        return !confirmPass.isNullOrEmpty() && confirmPass == password
    }

    fun showImageFullScreen(manager: FragmentManager, imageUrl: String) {
        ViewImagePopup.newInstance(imageUrl).show(manager, ViewImagePopup::class.simpleName)
    }

    fun convertDMYHMToDate(date: String): Date? {
        try {
            val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            return formatter.parse(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun convertDateToZ(date: Date): String {
        try {
            val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            return formatter.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }
}
