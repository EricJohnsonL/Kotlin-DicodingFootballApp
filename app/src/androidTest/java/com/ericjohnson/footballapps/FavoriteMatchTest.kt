package com.ericjohnson.footballapps

import android.support.test.espresso.Espresso
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.ericjohnson.footballapps.utils.EspressoIdLingResource
import com.ericjohnson.footballapps.view.mainactivity.MainActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by johnson on 10/15/18.
 */

@RunWith(AndroidJUnit4::class)
class FavoriteMatchTest {

    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdLingResource.getIdlingResource())
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdLingResource.getIdlingResource())
    }

    @Test
    fun testFavMatchesBehavior() {
        Espresso.onView(ViewMatchers.withId(R.id.bottom_nav))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.menu_fav_match)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.menu_prev_match)).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.srl_schedule)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.rv_schedule_list)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText("Real Madrid")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText("Real Madrid")).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.add_to_favorites)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.add_to_favorites)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText("Added to favorite"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.pressBack()

        Espresso.onView(ViewMatchers.withId(R.id.bottom_nav))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.menu_fav_match)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText("Real Madrid")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText("Real Madrid")).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.add_to_favorites)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.add_to_favorites)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText("Removed from favorite"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.pressBack()
    }
}