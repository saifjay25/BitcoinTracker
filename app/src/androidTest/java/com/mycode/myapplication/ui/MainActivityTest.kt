package com.mycode.myapplication.ui

import android.widget.TextView
import androidx.test.rule.ActivityTestRule
import com.github.mikephil.charting.charts.LineChart
import com.mycode.myapplication.R
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    private var activity : MainActivity? = null

    @Before
    fun setUp() {

        activity = activityRule.activity

    }

    @Test
    fun testNull(){

        val view : TextView = activity!!.findViewById(R.id.price)
        assertNotNull(view)

        val view2 : LineChart = activity!!.findViewById(R.id.linechart)
        assertNotNull(view2)
    }

    @After
    fun tearDown() {

        activity = null

    }
}