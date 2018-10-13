package com.getyourguide.berlintours.reviews.api

import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import com.getyourguide.berlintours.R
import com.getyourguide.berlintours.common.NetworkModule
import com.getyourguide.berlintours.common.extensions.resString
import com.getyourguide.berlintours.reviews.models.ReviewResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */

interface GetYourGuideApi {

    /**
     * curl -X GET \
     *      'https://www.getyourguide.com/berlin-l17/tempelhof-2-hour-airport-history-tour-berlin-airlift-more-t23776/reviews.json?count=5&page=0&rating=0&sortBy=date_of_review&direction=DESC' \
     *      -H 'User-Agent: GetYourGuide'
     */
    @Headers("User-Agent: GetYourGuide")
    @GET("{location}/{place}/reviews.json")
    fun reviews(@Path("location") location: String,
                @Path("place") place: String,
                @Query("count") @IntRange(from = 0) count: Int = 0,
                @Query("page") @IntRange(from = 0, to = 5) page: Int = 0,
                @Query("rating") @FloatRange(from = 0.0, to = 5.0) rating: Float? = null,
                @Query("sortBy") sortBy: String? = null,
                @Query("direction") direction: String? = null
    ): Observable<ReviewResponse>

    companion object {

        fun create(): GetYourGuideApi = NetworkModule().apply { baseUrl = R.string.gyg_base_url.resString }.retrofit().create(GetYourGuideApi::class.java)
    }
}