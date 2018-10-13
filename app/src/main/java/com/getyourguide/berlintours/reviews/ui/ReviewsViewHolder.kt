package com.getyourguide.berlintours.reviews.ui

import android.graphics.PorterDuff
import android.text.TextUtils.isEmpty
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.getyourguide.berlintours.R
import com.getyourguide.berlintours.common.extensions.gone
import com.getyourguide.berlintours.common.extensions.html
import com.getyourguide.berlintours.common.extensions.resColor
import com.getyourguide.berlintours.reviews.models.Review
import kotlinx.android.synthetic.main.item_review.view.*

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */

class ReviewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindTo(item: Review) = with(itemView) {

        title.text = item.title
        title.gone(isEmpty(item.title))

        message.text = item.message?.html
        message.gone(isEmpty(item.message))

        author.text = item.author
        author.gone(isEmpty(item.author))

        date.text = item.date
        date.gone(isEmpty(item.date))

        star_1.setRatingIcon(item.rating?.toDouble()?.toInt(), 1)
        star_2.setRatingIcon(item.rating?.toDouble()?.toInt(), 2)
        star_3.setRatingIcon(item.rating?.toDouble()?.toInt(), 3)
        star_4.setRatingIcon(item.rating?.toDouble()?.toInt(), 4)
        star_5.setRatingIcon(item.rating?.toDouble()?.toInt(), 5)

        translate_icon.setColorFilter(R.color.GYG_blue.resColor, PorterDuff.Mode.SRC_IN)

        setBackgroundColor(if (adapterPosition % 2 == 0) R.color.window_background.resColor else R.color.dark_red_transparent.resColor)
    }

    private fun ImageView.setRatingIcon(rating: Int?, minRating: Int) = setImageResource(if (rating ?: 0 >= minRating) R.drawable.ic_star else R.drawable.ic_star_empty)

    fun clear() {
    }

    companion object {
        const val layout: Int = R.layout.item_review
    }
}