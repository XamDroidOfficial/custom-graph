package com.example.customgraph

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.customgraph.databinding.ActivityHorizontalChartBinding

class HorizontalChartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHorizontalChartBinding
    private lateinit var adapter: HorizontalBarChartAdapter
    private lateinit var linearlayout: LinearLayout

    private var granularity = 0
    private var maxValue = 0
    private var maxValueOnXAxis = 0
    private var pointList = listOf(0, -8, -3,-2,-1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHorizontalChartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        getGranularity()
        addTextViewForXAxis()
        setGraphRecyclerViewAdapter(maxValueOnXAxis)
    }

    private fun initViews() {
        linearlayout = binding.xAxisTextViewContainerLinearLayout
    }

    private fun getGranularity() {
        maxValue = pointList.maxOrNull() ?: 0
        calculateGranularity(maxValue)
    }

    //This method will calculate the granularity(interval) between two points of X-Axis.
    private fun calculateGranularity(maxValue: Int) {
        when {
            maxValue > 10 -> {
                maxValueOnXAxis = maxValue + (5 - (maxValue % 5))
                granularity = maxValueOnXAxis / 5
            }
            maxValue == 10 -> {
                maxValueOnXAxis = 10
                granularity = 2
            }
            maxValue in 3..9 -> {
                maxValueOnXAxis = if (maxValue % 2 == 0) maxValue + 2 else maxValue + 1
                granularity = 2
            }
            maxValue > 1 -> {
                maxValueOnXAxis = 2
                granularity = 2
            }
            else -> {
                maxValueOnXAxis = 1
                granularity = 1
            }
        }
    }

    //This method will generate the required numbers of text-view for x-axis.
    private fun addTextViewForXAxis() {
        when {
            maxValue > 10 -> createTextView(5)
            maxValue > 8 -> createTextView(5)
            maxValue > 6 -> createTextView(4)
            maxValue > 4 -> createTextView(3)
            maxValue > 2 -> createTextView(2)
            else -> createTextView(1)
        }
    }

    private fun createTextView(numberOfTextView: Int) {
        generateXAxisOriginLabel()
        for (index in 1..numberOfTextView) {
            generateXAxisTextView((granularity * index).toString())
        }
    }

    private fun generateXAxisOriginLabel() {
        val textView = TextView(this)
        textView.text = getString(R.string.zero)
        linearlayout.addView(textView)
    }

    private fun generateXAxisTextView(text: String) {
        val textView = TextView(this)
        textView.text = text
        textView.textAlignment = View.TEXT_ALIGNMENT_VIEW_END
        textView.layoutParams = TableLayout.LayoutParams(
            TableLayout.LayoutParams.WRAP_CONTENT,
            TableLayout.LayoutParams.WRAP_CONTENT,
            1f
        )
        linearlayout.addView(textView)
    }

    private fun setGraphRecyclerViewAdapter(maxValueOnXAxis: Int) {
        adapter = HorizontalBarChartAdapter(pointList, maxValueOnXAxis)
        binding.barChartRecyclerview.layoutManager = LinearLayoutManager(this)
        binding.barChartRecyclerview.adapter = adapter
    }
}