package com.getyourguide.berlintours.reviews.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.getyourguide.berlintours.reviews.api.GetYourGuideApi
import com.getyourguide.berlintours.reviews.api.RequestParams
import com.getyourguide.berlintours.reviews.models.Review
import com.getyourguide.berlintours.reviews.repository.inMemory.ReviewDataSource
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */

class ReviewDataSourceFactory(
        private val requestParams: RequestParams,
        private val compositeDisposable: CompositeDisposable,
        private val networkService: GetYourGuideApi)
    : DataSource.Factory<Int, Review>() {

    val reviewDataSourceLiveData = MutableLiveData<ReviewDataSource>()

    override fun create(): DataSource<Int, Review?> {
        val newsDataSource = ReviewDataSource(requestParams, networkService, compositeDisposable)
        reviewDataSourceLiveData.postValue(newsDataSource)
        return newsDataSource
    }
}