package com.figsinc.app.login;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import com.figsinc.app.R;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withInputType;

/**
 * Created by 461883 on 10/14/17.
 */

public class ForgotPasswordActivityEspressoTest {

    @Rule
   public ActivityTestRule<ForgotPasswordActivity>forgotPasswordActivityTestRule = new ActivityTestRule<>(ForgotPasswordActivity.class);

    @Test
    public void performForgetPasswordClick(){

        onView(withId(R.id.editText)).perform(typeText("rez@figs.inc"),closeSoftKeyboard());
        onView(withId(R.id.buttonForgetPassword)).perform(click());
        Espresso.pressBack();
    }

    @Test
    public void pressNavigationUpButton(){
        onView(withContentDescription("Navigate up")).perform(click());
    }

}
