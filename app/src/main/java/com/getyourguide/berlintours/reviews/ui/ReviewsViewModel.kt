package com.getyourguide.berlintours.reviews.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.getyourguide.berlintours.R
import com.getyourguide.berlintours.common.extensions.resInt
import com.getyourguide.berlintours.reviews.api.GetYourGuideApi
import com.getyourguide.berlintours.reviews.api.RequestParams
import com.getyourguide.berlintours.reviews.models.Review
import com.getyourguide.berlintours.reviews.repository.NetworkState
import com.getyourguide.berlintours.reviews.repository.ReviewDataSourceFactory
import com.getyourguide.berlintours.reviews.repository.inMemory.ReviewDataSource
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */

class ReviewsViewModel : ViewModel() {

    private val networkService: GetYourGuideApi = GetYourGuideApi.create()

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    var reviews: LiveData<PagedList<Review>>

    private val reviewDataSourceFactory: ReviewDataSourceFactory

    val requestParams = RequestParams(
            "berlin-l17",
            "tempelhof-2-hour-airport-history-tour-berlin-airlift-more-t23776",
            R.integer.page_size.resInt
    )

    init {
        reviewDataSourceFactory = ReviewDataSourceFactory(requestParams, compositeDisposable, networkService)
        val config = PagedList.Config.Builder()
                .setPageSize(requestParams.count)
                .setInitialLoadSizeHint(requestParams.count * 2)
                .setEnablePlaceholders(false)
                .build()
        reviews = LivePagedListBuilder<Int, Review>(reviewDataSourceFactory, config).build()
    }

    fun getStatus(): LiveData<NetworkState> = Transformations.switchMap<ReviewDataSource, NetworkState>(reviewDataSourceFactory.reviewDataSourceLiveData, ReviewDataSource::state)

    fun getTotalReviewCount(): LiveData<Int> = Transformations.switchMap<ReviewDataSource, Int>(reviewDataSourceFactory.reviewDataSourceLiveData, ReviewDataSource::totalReviewsComments)

    fun retry() = reviewDataSourceFactory.reviewDataSourceLiveData.value?.retry()

    fun refresh() {
        reviewDataSourceFactory.reviewDataSourceLiveData.value?.invalidate()
    }

    fun listIsEmpty(): Boolean = reviews.value?.isEmpty() ?: true

    override fun onCleared() {
        super.onCleared()

        if (!compositeDisposable.isDisposed)
            compositeDisposable.dispose()
    }

}