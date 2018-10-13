package com.getyourguide.berlintours.reviews.api

import androidx.annotation.FloatRange
import androidx.annotation.IntRange

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */

data class RequestParams(var location: String = "",
                         var place: String = "",
                         @IntRange(from = 0) var count: Int = 0,
                         @IntRange(from = 0) var page: Int = 0,
                         @FloatRange(from = 0.0, to = 5.0) var rating: Float = 0f,
                         var sortBy: String? = null,
                         var direction: String? = null) {

    fun toggleDirection() = when (direction) {
        "desc" -> sortAscending()
        else -> sortDescending()
    }

    fun sortAscending() {
        direction = "asc"
    }

    fun sortDescending() {
        direction = "desc"
    }

    fun sortByRating() {
        sortBy = "rating"
    }

    fun sortByDateOfReview() {
        sortBy = "date_of_review"
    }
}