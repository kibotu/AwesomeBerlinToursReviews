package com.getyourguide.berlintours.common.extensions

import android.app.Activity
import android.view.View

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */

fun View?.show(isShowing: Boolean = true) {
    this?.visibility = if (isShowing) View.VISIBLE else View.INVISIBLE
}

fun View?.hide(isHiding: Boolean = true) {
    this?.visibility = if (isHiding) View.INVISIBLE else View.VISIBLE
}

fun View?.gone(isGone: Boolean = true) {
    this?.visibility = if (isGone) View.GONE else View.VISIBLE
}

val Activity.contentRoot: View
    get() = window.decorView.findViewById(android.R.id.content)