package com.figsinc.app.analyze;

import android.content.Intent;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import com.figsinc.app.R;
import com.figsinc.app.analyze.sentiments.SentimentsFragment;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by 461883 on 10/14/17.
 */

public class SentimentsFragmentEspressoTest {

    @Rule
    public FragmentTestRule<?, SentimentsFragment> fragmentTestRule =
            FragmentTestRule.create(SentimentsFragment.class);

    @Test
    public void clickButton() throws Exception {
       // onView(withText(R.string.)).perform(click());

       // onView(withText(R.string.button_clicked)).check(matches(isDisplayed()));
    }
}
