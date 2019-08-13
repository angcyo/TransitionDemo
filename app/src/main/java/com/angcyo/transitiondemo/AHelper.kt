package com.angcyo.transitiondemo

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
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
class AHelper {
    var activity: Activity? = null

    private var sharedElementList: MutableList<Pair<View, String>>? = null

    private fun isSupport(): Boolean {
        return activity != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
    }

    /**
     * 转场动画支持.
     * 步骤1: 获取共享元素属性值
     * 步骤2: 传递属性
     * 步骤3: 播放动画
     */
    fun transitionView(sharedElement: View, sharedElementName: String?) {
        if (!TextUtils.isEmpty(sharedElementName)) {
            if (sharedElementList == null) {
                sharedElementList = ArrayList()
            }
            sharedElementList!!.add(Pair(sharedElement, sharedElementName))
        }
    }

    fun transitionView(sharedElement: View) {
        transitionView(sharedElement, ViewCompat.getTransitionName(sharedElement))
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
            activity?.window?.enterTransition = enterTransition
        }
    }

    fun windowExitTransition(exitTransition: Transition) {
        if (isSupport()) {
            activity?.window?.exitTransition = exitTransition
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
            activity?.window?.sharedElementEnterTransition = enterTransition
        }
    }

    fun shareElementExitTransition(exitTransition: Transition) {
        if (isSupport()) {
            activity?.window?.sharedElementExitTransition = exitTransition
        }
    }

    fun start(intent: () -> Intent) {
        var transitionOptions: Bundle? = null
        if (sharedElementList != null) {
            transitionOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity as Activity,
                *sharedElementList!!.toTypedArray()
            ).toBundle()
        }

        ActivityCompat.startActivity(
            activity as Activity,
            intent(),
            transitionOptions
        )
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