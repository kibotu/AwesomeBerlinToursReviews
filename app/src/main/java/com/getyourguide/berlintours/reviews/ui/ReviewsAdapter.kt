package com.getyourguide.berlintours.reviews.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.getyourguide.berlintours.common.interfaces.OnItemClickListener
import com.getyourguide.berlintours.reviews.models.Review
import kotlinx.android.synthetic.main.item_review.view.*

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */

class ReviewsAdapter : PagedListAdapter<Review, ReviewsViewHolder>(DIFF_CALLBACK) {

    var onItemClickListener: OnItemClickListener<Review>? = null

    var onTranslationClickListener: OnItemClickListener<Review>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewsViewHolder = ReviewsViewHolder(LayoutInflater.from(parent.context).inflate(ReviewsViewHolder.layout, parent, false))

    override fun onBindViewHolder(holder: ReviewsViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bindTo(item)
            holder.itemView.setOnClickListener {
                onItemClickListener?.onItemClicked(item, holder.itemView, position)
            }
            holder.itemView.translate.setOnClickListener {
                onTranslationClickListener?.onItemClicked(item, holder.itemView, position)
            }
            holder.itemView.translate_icon.setOnClickListener {
                onTranslationClickListener?.onItemClicked(item, holder.itemView, position)
            }
        } else {
            holder.clear()
            holder.itemView.setOnClickListener { }
        }
    }

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Review>() {

            override fun areItemsTheSame(old: Review, new: Review): Boolean = old.review_id == new.review_id

            override fun areContentsTheSame(old: Review, new: Review): Boolean = old == new
        }
    }
}