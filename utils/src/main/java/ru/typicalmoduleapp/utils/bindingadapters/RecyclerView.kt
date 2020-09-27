package ru.typicalmoduleapp.utils.bindingadapters

import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("adapter")
fun bindRecyclerViewAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>?) {
    if (view.adapter !== adapter)
        view.adapter = adapter
}

@BindingAdapter("gridSpanSizeLookup")
fun bindGridSpanSizeLookup(view: RecyclerView, spanSizeLookup: GridLayoutManager.SpanSizeLookup?) {
    (view.layoutManager as? GridLayoutManager)?.spanSizeLookup = spanSizeLookup
}

@BindingAdapter("itemDecoration")
fun bindRecyclerViewItemDecoration(
    view: RecyclerView,
    itemDecoration: RecyclerView.ItemDecoration
) {
    view.removeItemDecoration(itemDecoration)
    view.addItemDecoration(itemDecoration)
}

@BindingAdapter("itemSpacing")
fun bindRecyclerViewItemSpacing(view: RecyclerView, spacingDp: Int) {
    val spacing = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        spacingDp.toFloat(),
        view.resources.displayMetrics
    ).toInt()
    view.addItemDecoration(object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val layoutManager = parent.layoutManager ?: return
            val adapter = parent.adapter ?: return
            val isLast = layoutManager.getPosition(view) == adapter.itemCount - 1
            if (!isLast) {
                when ((layoutManager as? LinearLayoutManager)?.orientation) {
                    LinearLayoutManager.HORIZONTAL -> outRect.right = spacing
                    LinearLayoutManager.VERTICAL -> outRect.bottom = spacing
                }
            }
        }
    })
}
