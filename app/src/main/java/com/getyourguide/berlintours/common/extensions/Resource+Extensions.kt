package com.getyourguide.berlintours.common.extensions

import android.content.res.Resources
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.N
import android.text.Html
import android.text.Spanned
import androidx.core.content.ContextCompat
import net.kibotu.ContextHelper

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */

/**
 * Converts dp to pixel.
 */
val Int.px: Int get() = (this * Resources.getSystem().displayMetrics.density).toInt()

/**
 * Converts pixel to dp.
 */
val Int.dp: Int get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Int.resBoolean: Boolean
    get() = ContextHelper.getApplication()!!.resources!!.getBoolean(this)

val Int.resInt: Int
    get() = ContextHelper.getApplication()!!.resources!!.getInteger(this)

val Int.resLong: Long
    get() = ContextHelper.getApplication()!!.resources!!.getInteger(this).toLong()

val Int.resDimension: Float
    get() = ContextHelper.getApplication()!!.resources!!.getDimension(this)

val Int.resString: String
    get() = ContextHelper.getApplication()!!.resources!!.getString(this)

val Int.resColor: Int
    get() = ContextCompat.getColor(ContextHelper.getApplication()!!, this)

val Int.html: Spanned
    get() = resString.html

val String.html: Spanned
    get() = if (SDK_INT >= N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        @Suppress("DEPRECATION")
        Html.fromHtml(this)
    }