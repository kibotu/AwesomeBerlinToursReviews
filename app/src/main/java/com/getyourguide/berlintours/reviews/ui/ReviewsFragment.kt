package com.getyourguide.berlintours.reviews.ui

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import com.getyourguide.berlintours.MainActivity.Companion.comingSoon
import com.getyourguide.berlintours.R
import com.getyourguide.berlintours.common.BaseFragment
import com.getyourguide.berlintours.common.extensions.contentRoot
import com.getyourguide.berlintours.common.extensions.resString
import com.getyourguide.berlintours.common.interfaces.OnItemClickListener
import com.getyourguide.berlintours.reviews.models.Review
import com.getyourguide.berlintours.reviews.repository.NetworkState
import com.getyourguide.berlintours.translate.api.Language
import com.getyourguide.berlintours.translate.ui.TranslationViewModel
import com.mingle.entity.MenuEntity
import com.mingle.sweetpick.DimEffect
import com.mingle.sweetpick.RecyclerViewDelegate
import com.mingle.sweetpick.SweetSheet
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import jp.wasabeef.recyclerview.animators.ScaleInAnimator
import kotlinx.android.synthetic.main.fragment_reviews.*
import net.kibotu.logger.Logger
import net.kibotu.logger.Logger.log


/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */

class ReviewsFragment : BaseFragment() {

    override val layout: Int = R.layout.fragment_reviews

    private val reviewsVm by lazy { ViewModelProviders.of(this).get(ReviewsViewModel::class.java) }

    private val translationVm by lazy { ViewModelProviders.of(this).get(TranslationViewModel::class.java) }

    private lateinit var adapter: ReviewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        translationVm.loadSupportedTranslationLanguages()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        initCollapsingToolBar()

        subscribeUi()

        setUiListener()

        initSupportedTranslationLanguages()
    }

    private fun setUiListener() {
        refresh.setOnRefreshListener { reviewsVm.refresh() }

        sort_by_rating.setOnClickListener {
            sortByRating()
        }
        sort_by_rating_icon.setOnClickListener {
            sortByRating()
        }

        sort_by_date.setOnClickListener {
            sortByDate()
        }
        sort_by_date_icon.setOnClickListener {
            sortByDate()
        }

        actions.setOnClickListener { comingSoon() }
    }

    private fun sortByRating() {
        reviewsVm.requestParams.sortByRating()
        reviewsVm.requestParams.toggleDirection()
        reviewsVm.refresh()
        sort_by_rating_icon.setImageResource(getIconBySortDirection())
    }

    private fun sortByDate() {
        reviewsVm.requestParams.sortByDateOfReview()
        reviewsVm.requestParams.toggleDirection()
        reviewsVm.refresh()
        sort_by_date_icon.setImageResource(getIconBySortDirection())
    }

    private fun getIconBySortDirection(): Int = when (reviewsVm.requestParams.direction) {
        "desc" -> R.drawable.ic_keyboard_arrow_down
        else -> R.drawable.ic_keyboard_arrow_up
    }

    private fun subscribeUi() {

        reviewsVm.getTotalReviewCount().observe(this, Observer {
            collapsing_toolbar.title = "$it ${R.string.title_reviews.resString}"
        })

        reviewsVm.reviews.observe(this, Observer {
            Logger.v(this::class.java.simpleName, "$it")
            adapter.submitList(it)
        })

        reviewsVm.getStatus().observe(this, Observer { state ->

            refresh.isRefreshing = reviewsVm.listIsEmpty() && state == NetworkState.RUNNING

            if (reviewsVm.listIsEmpty() && state == NetworkState.FAILED)
                Logger.snackbar("Something went wrong!")
        })
    }

    private fun initCollapsingToolBar() {
        header_title.text = R.string.berlin_tours_title.resString
        collapsing_toolbar.title = R.string.title_reviews.resString
        collapsing_toolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar)
        collapsing_toolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar)
    }

    private fun initRecyclerView() {
        adapter = ReviewsAdapter()
        val alphaAdapter = ScaleInAnimationAdapter(AlphaInAnimationAdapter(adapter, 0.25f), 0.75f)
        alphaAdapter.setInterpolator(OvershootInterpolator())
        alphaAdapter.setFirstOnly(false)
        list.itemAnimator = ScaleInAnimator(OvershootInterpolator(1f))
        list.adapter = alphaAdapter
        list.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    // region create bottom sheet

    private var sweetSheet: SweetSheet? = null

    private fun showBottomSheet(items: List<MenuEntity>?, onClick: (MenuEntity) -> Unit) {
        sweetSheet = SweetSheet(activity?.contentRoot as ViewGroup).apply {
            setDelegate(RecyclerViewDelegate(true))
            setBackgroundEffect(DimEffect(1f))
            setMenuList(items)
            setOnMenuItemClickListener { _, menuEntity ->
                Logger.v(menuEntity.title.toString())
                onClick(menuEntity)
                true
            }
            show()
        }
    }

    // endregion

    // region bottom sheet menu entry: translation output text

    private fun initSupportedTranslationLanguages() {

        translationVm.supportedTranslationLanguages.observe(this, Observer { languages ->

            adapter.onTranslationClickListener = object : OnItemClickListener<Review> {
                override fun onItemClicked(model: Review, itemView: View, position: Int) {
                    showBottomSheet(languages?.map { MenuEntity().apply { title = it.name ?: it.language } }) { menuEntity ->
                        translate(model, menuEntity.title.toString(), position)
                    }
                }
            }
        })
    }

    // endregion

    private fun translate(item: Review, target: String, position: Int) {

        Logger.v("translate to ${item.languageCode}")

        val targetLanguageCode = Language.availableLanguages[target]
        translationVm.translate(item.message
                ?: return, item.languageCode, targetLanguageCode) { translation ->

            log(translation)
            item.message = translation
            item.languageCode = targetLanguageCode

            adapter.notifyItemChanged(position)
        }

        translationVm.translate(item.title
                ?: return, item.languageCode, targetLanguageCode) { translation ->

            log(translation)
            item.title = translation
            item.languageCode = targetLanguageCode

            adapter.notifyItemChanged(position)
        }

    }
}

