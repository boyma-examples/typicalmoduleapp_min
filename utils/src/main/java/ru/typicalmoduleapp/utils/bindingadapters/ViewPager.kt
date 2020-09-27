package ru.typicalmoduleapp.utils.bindingadapters

import androidx.databinding.BindingAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import kotlin.math.roundToInt

@BindingAdapter("adapter")
fun bindViewPagerAdapter(view: ViewPager, adapter: PagerAdapter?) {
    if (view.adapter !== adapter)
        view.adapter = adapter
}

@BindingAdapter("pageMargin")
fun bindViewPagerPageMargin(view: ViewPager, margin: Float?) {
    view.pageMargin = margin?.roundToInt() ?: 0
}
