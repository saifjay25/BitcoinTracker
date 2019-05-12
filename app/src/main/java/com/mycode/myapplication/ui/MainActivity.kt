package com.mycode.myapplication.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.mycode.myapplication.R
import com.mycode.myapplication.entity.BitcoinPrice
import com.mycode.myapplication.viewModels.ViewModelProviderFactory
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DateFormat
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class MainActivity : DaggerAppCompatActivity(){

    var providerFactory : ViewModelProviderFactory? = null
        @Inject set

    lateinit var viewModel : MainViewModel
    val value : ArrayList<Entry> = ArrayList()
    var bitcoin : String = ""
    lateinit var formatter : NumberFormat
    var recentPrice : Double = 0.0
    var currentDate = ""
    var hashMap = HashMap<Float,String>()
    var hashrate = HashMap<Float, String>()
    var hashstamp = HashMap<Float,String>()
    var hashfees = HashMap<Float, String>()
    var hashdifficulty = HashMap<Float, String>()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        formatter = NumberFormat.getCurrencyInstance()
        val calendar = Calendar.getInstance()
        currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.time)


        linechart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener
        {
            @SuppressLint("SetTextI18n")
            override fun onValueSelected(e: Entry?, h: Highlight?)
            {
                val y = e?.y
                price2.text = hashMap.get(y).toString()+ ": " +y.toString()
                expandable_text.text = "Current Price: " + bitcoin+"\n"+
                        "Hash Rate: "+ hashrate.get(y)+"\n"+
                        "timestamp: " + hashstamp.get(y)+"\n"+
                        "Total Fees BTC: " + hashfees.get(y)+"\n"+
                        "Difficulty: " + hashdifficulty.get(y)
            }

            override fun onNothingSelected() {
            }
        })

        //when instantiating viewmodel you have to pass viewmodel providers factory in order to do dependecy injection
        viewModel = ViewModelProviders.of(this,providerFactory).get(MainViewModel::class.java)
        viewModel.getEverything().observe(this, object : Observer<MutableList<BitcoinPrice>>
        {
            override fun onChanged(t: MutableList<BitcoinPrice>?)
            {
                if (t != null)
                {
                    if(t.isEmpty())
                    {
                        val bitcoin = BitcoinPrice()
                        bitcoin.setPrice(0.0)
                        bitcoin.setTime("")
                        expandable_text.text = "Current Price: $0"
                        t.add(bitcoin)
                        recentPrice = 0.0
                    }
                    else
                    {
                        recentPrice = t[t.lastIndex].getPrice()
                    }
                    value.clear()
                    for(i in t.indices)
                    {
                        value.add(Entry(i.toFloat(), t[i].getPrice().toFloat()))
                        hashMap.put(t[i].getPrice().toFloat(),t[i].getTime())
                        hashdifficulty.put(t[i].getPrice().toFloat(), t[i].getDifficulty())
                        hashfees.put(t[i].getPrice().toFloat(), t[i].getFees())
                        hashrate.put(t[i].getPrice().toFloat(), t[i].getHashRate())
                        hashstamp.put(t[i].getPrice().toFloat(), t[i].getTimestamp())
                    }
                    val set = LineDataSet(value,"set")
                    set.fillAlpha = 110
                    set.valueTextSize = 10f
                    val dataset : ArrayList<ILineDataSet> = ArrayList()
                    dataset.add(set)
                    val data = LineData(dataset)
                    linechart.data = data
                    linechart.invalidate()
                }
            }
        })
        viewModel.APICallFromFlowable()
        subscribeObservers()
    }

    private fun subscribeObservers()
    {
        viewModel.observePrice().observe(this, object : Observer<BitcoinPrice>
        {
            override fun onChanged(bitcoinValue: BitcoinPrice?)
            {
                if (bitcoinValue != null && recentPrice != bitcoinValue.getPrice())
                {
                    bitcoinValue.setTime(currentDate)
                    bitcoin = formatter.format(bitcoinValue.getPrice())
                    viewModel.add(bitcoinValue)
                }
                else
                {
                    if (bitcoinValue != null)
                    {
                        bitcoin = formatter.format(bitcoinValue.getPrice())
                    }
                }
                if (bitcoinValue != null) {
                    expandable_text.text = "Current Price: " + bitcoin+"\n"+
                                            "Hash Rate: "+ bitcoinValue.getHashRate()+"\n"+
                                            "timestamp: " + bitcoinValue.getTimestamp()+"\n"+
                                            "Total Fees BTC: " + bitcoinValue.getFees()+"\n"+
                                            "Difficulty: " + bitcoinValue.getDifficulty()
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean
    {
        val inflater = menuInflater
        inflater.inflate(R.menu.ex_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean
    {
        if (item != null)
        {
            if(item.itemId == R.id.refresh)
            {
                finish()
                startActivity(intent)
            }
            if(item.itemId == R.id.delete)
            {
                viewModel.deleteEverything()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
