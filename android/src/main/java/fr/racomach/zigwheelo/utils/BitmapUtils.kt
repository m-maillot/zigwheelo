package fr.racomach.zigwheelo.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.VectorDrawable
import androidx.core.content.ContextCompat.getDrawable
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory


fun getBitmapDescriptor(context: Context, id: Int): BitmapDescriptor {
    val vectorDrawable = getDrawable(context, id) as VectorDrawable
    val h = vectorDrawable.intrinsicHeight
    val w = vectorDrawable.intrinsicWidth
    vectorDrawable.setBounds(0, 0, w, h)
    val bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bm)
    vectorDrawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bm)
}