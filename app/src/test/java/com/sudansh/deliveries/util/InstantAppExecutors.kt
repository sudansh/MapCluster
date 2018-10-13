package com.sudansh.deliveries.util

import com.sudansh.deliveries.data.AppExecutors
import java.util.concurrent.Executor

class InstantAppExecutors : AppExecutors(instant, instant) {
    companion object {
        private val instant = Executor { it.run() }
    }
}