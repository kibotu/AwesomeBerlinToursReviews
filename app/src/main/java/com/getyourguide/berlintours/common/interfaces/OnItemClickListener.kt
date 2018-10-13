package com.getyourguide.berlintours.common.interfaces

import android.view.View

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */

interface OnItemClickListener<T> {

    fun onItemClicked(model: T, itemView: View, position: Int)
}