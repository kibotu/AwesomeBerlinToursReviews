package com.getyourguide.berlintours.reviews.models

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */

data class ReviewResponse(
        val status: Boolean? = false,
        val total_reviews_comments: Int? = 0,
        val data: List<Review?>? = listOf()
)