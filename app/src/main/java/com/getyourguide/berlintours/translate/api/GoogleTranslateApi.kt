package com.getyourguide.berlintours.translate.api

import com.getyourguide.berlintours.R
import com.getyourguide.berlintours.common.NetworkModule
import com.getyourguide.berlintours.common.extensions.resString
import io.reactivex.Flowable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 *
 * https://cloud.google.com/translate/docs/quickstart?hl=de
 */

interface GoogleTranslateApi {

    /**
    curl -s -X POST -H "Content-Type: application/json" \
    -H "Authorization: Bearer "$(gcloud auth print-access-token) \
    --data "{
    'q': 'The Great Pyramid of Giza (also known as the Pyramid of Khufu or the
    Pyramid of Cheops) is the oldest and largest of the three pyramids in
    the Giza pyramid complex.',
    'source': 'en',
    'target': 'es',
    'format': 'text'
    }" "https://translation.googleapis.com/language/translate/v2"
     */

    @POST("language/translate/v2")
    fun translate(@Body request: TranslationRequest, @Query("key") key: String = "AIzaSyBrtqAwvKJUKO7bmi3FlsbBpU61073ZkNc"): Flowable<TranslationResponse>

    @GET("language/translate/v2/languages")
    fun languages(@Query("key") key: String = "AIzaSyBrtqAwvKJUKO7bmi3FlsbBpU61073ZkNc"): Flowable<TranslationLanguagesResponse>

    companion object {

        fun create(): GoogleTranslateApi = NetworkModule().apply { baseUrl = R.string.google_translate_base_url.resString }.retrofit().create(GoogleTranslateApi::class.java)
    }
}