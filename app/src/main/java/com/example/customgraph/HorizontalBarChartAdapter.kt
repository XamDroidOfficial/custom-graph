package com.example.customgraph

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.customgraph.databinding.ViewholderItemHorizontalBarChartBinding
import kotlin.math.roundToInt

class HorizontalBarChartAdapter(
    private var pointList: List<Int>,
    private var maxValueOnXAxis: Int
) :
    RecyclerView.Adapter<HorizontalBarChartAdapter.HorizontalBarChartViewHolder>() {

    private var parentWidth: Int = 0
    private var viewWidthInPx: Int = 0
    private var marginInDp: Float = 24f
    private var marginInPx: Float = 0f

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HorizontalBarChartViewHolder {
        val binding = ViewholderItemHorizontalBarChartBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        marginInPx = convertMarginInPxFromDp(parent)
        parentWidth = getParentWidthInPx(parent)

        return HorizontalBarChartViewHolder(binding)
    }

    private fun convertMarginInPxFromDp(parent: ViewGroup) = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        marginInDp,
        parent.context.resources.displayMetrics
    )

    private fun getParentWidthInPx(parent: ViewGroup) =
        (parent.context.resources.displayMetrics.widthPixels - (marginInPx * 2)).roundToInt()

    // Below formula is similar as we use for calculating percentage.
    private fun calculateViewWidth(value: Int): Int {
        return value * parentWidth / maxValueOnXAxis
    }

    override fun onBindViewHolder(holder: HorizontalBarChartViewHolder, position: Int) {
        viewWidthInPx = calculateViewWidth(pointList[position])
        holder.binding.view.layoutParams.width = viewWidthInPx
        holder.binding.textValue.text = pointList[position].toString()
    }

    override fun getItemCount(): Int {
        return pointList.size
    }

    inner class HorizontalBarChartViewHolder(val binding: ViewholderItemHorizontalBarChartBinding) :
        RecyclerView.ViewHolder(binding.root)
}