package com.getyourguide.berlintours.translate.api

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */

data class TranslationResponse(
        var data: Data? = null
)

data class Data(
        var translations: List<Translations>? = null
)

data class Translations(
        var translatedText: String? = null,
        var detectedSourceLanguage: String? = null
)