package fr.racomach.zigwheelo.common

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager

fun Context.missingPermissions(): List<String> {
    val missingPermissions = mutableListOf<String>()
    if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        missingPermissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
    if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        missingPermissions.add(Manifest.permission.ACCESS_FINE_LOCATION)

    return missingPermissions.toList()
}

fun Context.hasLocationPermissions() =
    checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED