package com.angcyo.transitiondemo

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.content_main2.*

class MainFragment2 : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FHelper().apply {
            fragment = this@MainFragment2
            defaultTransition()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.activity_main2, container, false)

        FHelper().apply {
            transitionView(inflate.findViewById(R.id.image_view), "image_view")
            transitionView(inflate.findViewById(R.id.text_view), "text_view")
            doIt()
        }

        return inflate
    }
}
