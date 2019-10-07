package com.kglazuna.app.ui

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.kglazuna.app.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ImageSelectAndVoteTest {

    @get:Rule
    val intentsTestRule: IntentsTestRule<CatsListActivity> =
        IntentsTestRule(CatsListActivity::class.java)

    lateinit var context: Context

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun selectImageShowsRatingViewAndVoteClickShowsSnackbar() = runBlocking {
        delay(5000)

        onView(withId(R.id.catRecyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )

        onView(withId(R.id.catRatingLayout)).check(matches(isDisplayed()))
        intended(IntentMatchers.hasComponent(CatRatingActivity::class.java.name))

        onView(withId(R.id.upVote)).perform(click())

        delay(2000)

        onView(
            Matchers.allOf(
                withId(com.google.android.material.R.id.snackbar_text), ViewMatchers.withText(
                    context.getString(
                        R.string.vote_success
                    )
                )
            )
        )
            .check(matches(isDisplayed()))
        Unit
    }
}
