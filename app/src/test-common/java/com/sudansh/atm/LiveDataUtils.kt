package com.sudansh.atm

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

object LiveDataUtils {
    @Throws(InterruptedException::class)
    @Suppress("UNCHECKED_CAST")
    fun <T> getValue(liveData: LiveData<T>?): T {
        val data = arrayOfNulls<Any>(1)
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(o: T?) {
                data[0] = o
                latch.countDown()
                liveData?.removeObserver(this)
            }
        }
        liveData?.observeForever(observer)
        latch.await(2, TimeUnit.SECONDS)

        return data[0] as T
    }
}

fun <T> LiveData<T>.liveValue(): T = LiveDataUtils.getValue(this)

