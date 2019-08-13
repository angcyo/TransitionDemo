package com.angcyo.transitiondemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.content_main2.*

class Activity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        setSupportActionBar(toolbar)

        AHelper().apply {
            activity = this@Activity2
            transitionView(image_view, "image_view")
            transitionView(text_view, "text_view")
            defaultTransition()
            doIt()
        }
    }
}
