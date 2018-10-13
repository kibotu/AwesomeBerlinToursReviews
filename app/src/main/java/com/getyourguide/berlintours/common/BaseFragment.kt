package com.getyourguide.berlintours.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.getyourguide.berlintours.common.interfaces.LayoutProvider

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */

abstract class BaseFragment : Fragment(), LayoutProvider {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(layout, container, false)
}