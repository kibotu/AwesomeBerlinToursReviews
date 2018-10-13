package com.getyourguide.berlintours.reviews.models

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */

data class Review(
        val review_id: Int? = 0,
        val rating: String? = "",
        var title: String? = "",
        var message: String? = "",
        val author: String? = "",
        val foreignLanguage: Boolean? = false,
        val date: String? = "",
        val date_unformatted: DateUnformatted? = DateUnformatted(),
        var languageCode: String? = "",
        val traveler_type: String? = "",
        val reviewerName: String? = "",
        val reviewerCountry: String? = ""
)