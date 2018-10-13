package com.getyourguide.berlintours.reviews.repository.inMemory

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.getyourguide.berlintours.reviews.api.GetYourGuideApi
import com.getyourguide.berlintours.reviews.api.RequestParams
import com.getyourguide.berlintours.reviews.models.Review
import com.getyourguide.berlintours.reviews.models.ReviewResponse
import com.getyourguide.berlintours.reviews.repository.NetworkState
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */

class ReviewDataSource(
        private val requestParams: RequestParams,
        private val networkService: GetYourGuideApi,
        private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, Review?>() {

    var state: MutableLiveData<NetworkState> = MutableLiveData()

    var totalReviewsComments: MutableLiveData<Int> = MutableLiveData()

    private var retryCompletable: Completable? = null

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Review?>) {
        updateState(NetworkState.RUNNING)

        compositeDisposable.add(
                createReviewRequest()
                        .subscribe(
                                { response ->
                                    updateState(NetworkState.SUCCESS)
                                    updateTotalReviewsComments(response?.total_reviews_comments)
                                    callback.onResult(response.data?.toMutableList()
                                            ?: mutableListOf(),
                                            null,
                                            2
                                    )
                                },
                                {
                                    updateState(NetworkState.FAILED)
                                    setRetry(Action { loadInitial(params, callback) })
                                }
                        )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Review?>) {
        updateState(NetworkState.RUNNING)

        compositeDisposable.add(
                createReviewRequest()
                        .subscribe(
                                { response ->
                                    updateState(NetworkState.SUCCESS)
                                    updateTotalReviewsComments(response?.total_reviews_comments)
                                    callback.onResult(response.data?.toMutableList()
                                            ?: mutableListOf(),
                                            params.key + 1
                                    )
                                },
                                {
                                    updateState(NetworkState.FAILED)
                                    setRetry(Action { loadAfter(params, callback) })
                                }
                        )
        )
    }

    private fun createReviewRequest(): Observable<ReviewResponse> {
        return networkService.reviews(
                requestParams.location,
                requestParams.place,
                requestParams.count,
                requestParams.page,
                requestParams.rating,
                requestParams.sortBy,
                requestParams.direction)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Review?>) {
    }

    fun retry() = retryCompletable?.let {
        compositeDisposable.add(it
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())
    }

    private fun setRetry(action: Action?) {
        retryCompletable = if (action == null) null else Completable.fromAction(action)
    }

    private fun updateState(state: NetworkState) = this.state.postValue(state)

    private fun updateTotalReviewsComments(totalReviewsComments: Int?) = this.totalReviewsComments.postValue(totalReviewsComments)
}