package com.ericjohnson.footballapps.utils

import android.support.test.espresso.IdlingResource
import android.support.test.espresso.idling.CountingIdlingResource

/**
 * Created by johnson on 12/10/18.
 */
class EspressoIdlingResource {

    companion object {

        private const val RESOURCE: String = "idlingResource"

        private val countingIdlingResource: CountingIdlingResource = CountingIdlingResource(RESOURCE)

        fun increment() = countingIdlingResource.increment()

        fun decrement() = countingIdlingResource.decrement()

        fun getIdlingResource(): IdlingResource = countingIdlingResource
    }
}