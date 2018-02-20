package com.figsinc.app.onboarding;

import android.support.test.rule.ActivityTestRule;

import com.figsinc.app.R;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


/**
 * Created by 461883 on 10/14/17.
 */

public class OnboardingActivityEspressoTest {

    @Rule
    public ActivityTestRule<OnboardingActivity> onboardingActivityTestRule = new ActivityTestRule<>(OnboardingActivity.class);

    @Test
    public void viewPagerSwipeRight() {
        onView(withId(R.id.pager)).perform(swipeLeft()).perform(swipeLeft()).perform(swipeLeft()).perform(swipeLeft());
    }

    @Test
    public void linear_bottomLayoutClick(){
        onView(withId(R.id.linear_bottomLayout)).perform(click());
    }

    @Test
    public void signupButtonClick(){
        onView(withId(R.id.buttonLogIn)).perform(click());
    }

}
