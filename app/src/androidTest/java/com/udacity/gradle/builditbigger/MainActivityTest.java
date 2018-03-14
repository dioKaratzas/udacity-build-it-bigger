package com.udacity.gradle.builditbigger;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertNotNull;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void jokeAsyncTaskTest() {
        onView(withId(R.id.buttonTellJoke))
                .check(matches(withText(R.string.button_text)));

        // Testing that JokeAsyncTask  result is not null
        // You can test this from buildItBigger -> verification -> runIntegrationTests Gradle Task
        String result = null;
        JokeAsyncTask jokeAsyncTask = new JokeAsyncTask(null);
        jokeAsyncTask.execute();
        try {
            result = jokeAsyncTask.get();
            Log.d("testing", "Retrieved a non-empty string successfully: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertNotNull(result);
    }

}
