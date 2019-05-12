package com.mycode.myapplication.ui

import android.app.Application
import androidx.lifecycle.*
import com.mycode.myapplication.database.Repository
import com.mycode.myapplication.entity.BitcoinPrice
import com.mycode.myapplication.network.MainAPI
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(mainAPI: MainAPI, application: Application) : AndroidViewModel(application) {

    private var Tag = "auth"
    private var mainAPI : MainAPI
    private var mediator : MediatorLiveData<BitcoinPrice> = MediatorLiveData()
    private var repos : Repository = Repository(application)
    private var everything : LiveData<MutableList<BitcoinPrice>> = repos.getEverything()

    init
    {
        this.mainAPI = mainAPI
    }

    //convert flowable to live data object
    //source is going to be an api call from the flowable object
    fun APICallFromFlowable()
    {
        val source : LiveData<BitcoinPrice> = LiveDataReactiveStreams.fromPublisher(
            mainAPI.getPrice().subscribeOn(Schedulers.io())
        )

        mediator.addSource(source, object : Observer<BitcoinPrice>
        {
            //return the data using an Rx call then convert to live data and to mediator live data
            override fun onChanged(bitcoinPrice: BitcoinPrice?) {
                mediator.value = bitcoinPrice
                mediator.removeSource(source)
            }

        })
    }

    fun observePrice() : LiveData<BitcoinPrice>
    {
        return mediator
    }

    fun add (price : BitcoinPrice)
    {
        repos.add(price)
    }

    fun getEverything() : LiveData<MutableList<BitcoinPrice>>
    {
        return everything
    }

    fun deleteEverything()
    {
        repos.deleteEverything()
    }


}