package com.getyourguide.berlintours.common.interfaces

import androidx.annotation.LayoutRes

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */

interface LayoutProvider {

    @get:LayoutRes
    val layout: Int
}