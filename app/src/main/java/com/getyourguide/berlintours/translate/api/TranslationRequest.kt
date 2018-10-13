package com.getyourguide.berlintours.translate.api

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */

data class TranslationRequest(
        var format: String? = null,
        var target: String? = null,
        var source: String? = null,
        var q: String? = null
)