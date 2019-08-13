package com.angcyo.transitiondemo

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main2.*

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab.setOnClickListener { view ->
            FHelper().apply {
                fragmentManager = getFragmentManager()
                transitionView(image_view, "image_view")
                transitionView(text_view, "text_view")
                start {
                    replace(R.id.root_layout, MainFragment2())
                    addToBackStack("MainFragment2")
                }
            }
        }

        image_view.setOnClickListener {
            AHelper().apply {
                activity = getActivity()
                transitionView(image_view, "image_view")
                transitionView(text_view, "text_view")
                start {
                    Intent(context, Activity2::class.java)
                }
            }
        }
    }
}
