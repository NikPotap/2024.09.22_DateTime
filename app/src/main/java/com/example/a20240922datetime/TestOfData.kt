package com.example.a20240922datetime

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import java.time.LocalDate

@SuppressLint("NewApi")
fun testOfData(
    cont: Context,
    name: String,
    lastName: String,
    day: String,
    monthSize: Int,
    date: String,
    year: String,
    pictIsSet: String
): Boolean {
    if (name == "" || lastName == "") {
        Toast.makeText(cont, cont.resources.getString(R.string.error_null), Toast.LENGTH_SHORT)
            .show()
        return false
    }
    if ((day.toIntOrNull() == null) || (year.toIntOrNull() == null)) {
        Toast.makeText(
            cont,
            cont.resources.getString(R.string.error_blank_date_or_year),
            Toast.LENGTH_SHORT
        )
            .show()
        return false
    }
    val chars = ('а'..'я').toList()
    if (name.any() { ch -> ch !in chars } || lastName.any() { ch -> ch !in chars }) {
        Toast.makeText(
            cont,
            cont.resources.getString(R.string.error_bad_name_or_lastname),
            Toast.LENGTH_SHORT
        )
            .show()
        return false
    }
    if (day.toInt() !in 1..monthSize) {
        Toast.makeText(cont, cont.resources.getString(R.string.error_bad_day), Toast.LENGTH_SHORT)
            .show()
        return false
    }
    val tempDate = LocalDate.parse(date)
    if (LocalDate.now().isBefore(tempDate) || tempDate.isBefore(LocalDate.parse("1910-01-01"))) {
        Toast.makeText(cont, cont.resources.getString(R.string.error_bad_year), Toast.LENGTH_SHORT)
            .show()
        return false
    }
    if (pictIsSet == "false") {
        Toast.makeText(cont, cont.resources.getString(R.string.error_not_pict), Toast.LENGTH_SHORT)
            .show()
        return false
    }
    return true
}