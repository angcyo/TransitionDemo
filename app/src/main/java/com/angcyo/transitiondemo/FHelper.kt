package com.angcyo.transitiondemo

import android.os.Build
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.util.Pair
import android.support.v4.view.ViewCompat
import android.text.TextUtils
import android.transition.*
import android.view.View
import java.util.*

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/08/13
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class FHelper {

    var fragmentManager: FragmentManager? = null
    var fragment: Fragment? = null

    private var sharedElementList: MutableList<Pair<View, String>>? = null

    private fun configElement(fragmentTransaction: FragmentTransaction) {
        if (sharedElementList != null) {
            for (pair in sharedElementList!!) {
                if (pair.first != null && pair.second != null) {
                    ViewCompat.setTransitionName(pair.first!!, pair.second)
                    fragmentTransaction.addSharedElement(pair.first!!, pair.second!!)
                }
            }
        }
    }

    /**
     * Fragment转场动画, 不能用add只能用replace
     */
    fun transitionView(sharedElement: View, sharedElementName: String?) {
        if (!TextUtils.isEmpty(sharedElementName)) {
            if (sharedElementList == null) {
                sharedElementList = ArrayList()
            }
            sharedElementList!!.add(Pair(sharedElement, sharedElementName))
        }
    }

    private fun isSupport(): Boolean {
        return fragment != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
    }

    fun defaultTransition() {
        defaultWindowTransition()
        defaultShareElementTransition()
    }

    fun defaultWindowTransition() {
        if (isSupport()) {
            windowTransition(Fade(), Fade())
        }
    }

    fun windowTransition(
        enterTransition: Transition,
        exitTransition: Transition
    ) {
        windowEnterTransition(enterTransition)
        windowExitTransition(exitTransition)
    }

    fun windowEnterTransition(enterTransition: Transition) {
        if (isSupport()) {
            fragment?.enterTransition = enterTransition
        }
    }

    fun windowExitTransition(exitTransition: Transition) {
        if (isSupport()) {
            fragment?.exitTransition = exitTransition
        }
    }

    fun defaultShareElementTransition() {
        if (isSupport()) {
            val transitionSet = TransitionSet()
            transitionSet.addTransition(ChangeBounds())
            transitionSet.addTransition(ChangeTransform())
            transitionSet.addTransition(ChangeClipBounds())
            transitionSet.addTransition(ChangeImageTransform())
            //transitionSet.addTransition(ChangeScroll()) //图片过渡效果, 请勿设置此项
            return shareElementTransition(transitionSet, transitionSet)
        }
    }

    fun shareElementTransition(
        enterTransition: Transition,
        exitTransition: Transition
    ) {
        shareElementEnterTransition(enterTransition)
        shareElementExitTransition(exitTransition)
    }

    fun shareElementEnterTransition(enterTransition: Transition) {
        if (isSupport()) {
            fragment?.sharedElementEnterTransition = enterTransition
        }
    }

    fun shareElementExitTransition(exitTransition: Transition) {
        if (isSupport()) {
            fragment?.sharedElementReturnTransition = exitTransition
        }
    }

    fun start(config: FragmentTransaction.() -> Unit) {
        if (fragmentManager != null) {
            val transaction = fragmentManager!!.beginTransaction()
            configElement(transaction)
            transaction.config()
            transaction.commit()
        }
    }

    fun doIt() {
        sharedElementList?.let {
            for (pair in it) {
                if (pair.first != null) {
                    ViewCompat.setTransitionName(pair.first!!, pair.second)
                }
            }
        }
    }
}