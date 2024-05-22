package com.gianghv.android.util.app

import com.gianghv.android.domain.RoomType
import java.util.Date
import kotlin.math.abs
import kotlin.math.round

object CostUtils {
    fun calculateCost(basePrice: Int, checkIn: Date, checkOut: Date, roomType: RoomType, people: Int): Int {
        try {
            val durationInMillis = abs(checkOut.time - checkIn.time)
            val durationInHours = durationInMillis / (1000 * 60 * 60)
            var totalPrice: Int = (basePrice / 24 * durationInHours * people).toInt()

            //Vip service fee
            if (roomType == RoomType.Vip) totalPrice = round(totalPrice * 1.05f).toInt()

            //VAT
            totalPrice = round(totalPrice * 1.1f).toInt()
            return totalPrice
        } catch (e: Exception) {
            e.printStackTrace()
            return 0
        }
    }

    fun calculateTaxFreeCost(basePrice: Int, checkIn: Date, checkOut: Date, roomType: RoomType, people: Int): Int {
        try {
            val durationInMillis = abs(checkOut.time - checkIn.time)
            val durationInHours = durationInMillis / (1000 * 60 * 60)
            val totalPrice = (basePrice / 24 * durationInHours * people).toInt()

            return totalPrice
        } catch (e: Exception) {
            e.printStackTrace()
            return 0
        }
    }
}
