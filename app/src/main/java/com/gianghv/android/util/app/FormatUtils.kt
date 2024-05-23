package com.gianghv.android.util.app

import java.text.DecimalFormat

object FormatUtils {

    private const val PATTERN_ESTIMATED_PRICE = "###,###,###"

    fun convertEstimatedPriceVND(estimatedPrice: Double): String {
        return convertEstimatedPrice(estimatedPrice) + "K VNĐ"
    }

    fun convertEstimatedPriceVND(estimatedPrice: Float): String {
        return convertEstimatedPrice(estimatedPrice.toDouble()) + "K VNĐ"
    }

    fun convertEstimatedPriceVND(estimatedPrice: Int): String {
        return convertEstimatedPrice(estimatedPrice.toDouble()) + " VNĐ"
    }

    fun convertEstimatedPriceVND(estimatedPrice: Long): String {
        return convertEstimatedPrice(estimatedPrice.toDouble()) + "K VNĐ"
    }

    private fun convertEstimatedPrice(estimatedPrice: Double): String {
        val format = DecimalFormat(PATTERN_ESTIMATED_PRICE)
        return format.format(estimatedPrice)
    }
}
