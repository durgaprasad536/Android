package com.figsinc.app.login;

import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;

import com.figsinc.app.R;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertFalse;

/**
 * Created by 461883 on 10/14/17.
 */

public class LoginActivityEspressoTest  {

    @Rule
 public ActivityTestRule<LoginActivity>loginActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void performLoginClick(){
        onView(withId(R.id.editTextEmail)).perform(typeText("rez@figs.inc"), closeSoftKeyboard());
        onView(withId(R.id.editTextPassword)).perform(typeText("rez"),closeSoftKeyboard());
        onView(withId(R.id.buttonLogIn)).perform(click());
    }

    @Test
    public void performWrongUsername(){
        onView(withId(R.id.editTextEmail)).perform(typeText("rez456@figs.inc"), closeSoftKeyboard());
        onView(withId(R.id.editTextPassword)).perform(typeText("rez"),closeSoftKeyboard());
        onView(withId(R.id.buttonLogIn)).perform(click());
    }

    @Test
    public void performWrongPassword(){
        onView(withId(R.id.editTextEmail)).perform(typeText("rez@figs.inc"), closeSoftKeyboard());
        onView(withId(R.id.editTextPassword)).perform(typeText("rez456"),closeSoftKeyboard());
        onView(withId(R.id.buttonLogIn)).perform(click());
    }

    @Test
    public void performWrongEmailFormat(){
        onView(withId(R.id.editTextEmail)).perform(typeText("rez@figs"), closeSoftKeyboard());
        onView(withId(R.id.editTextPassword)).perform(typeText("rez456"),closeSoftKeyboard());
        onView(withId(R.id.buttonLogIn)).perform(click());
    }

    @Test
    public void performWrongEmailFormatTwo(){
        onView(withId(R.id.editTextEmail)).perform(typeText("rezfigs.inc"), closeSoftKeyboard());
        onView(withId(R.id.editTextPassword)).perform(typeText("rez456"),closeSoftKeyboard());
        onView(withId(R.id.buttonLogIn)).perform(click());
    }

    @Test
    public void emailValidator_InvalidEmailDoubleDot_ReturnsFalse() {
        onView(withId(R.id.editTextEmail)).perform(typeText("rez@figs..inc"), closeSoftKeyboard());
        onView(withId(R.id.editTextPassword)).perform(typeText("rez"),closeSoftKeyboard());
        onView(withId(R.id.buttonLogIn)).perform(click());
    }

    @Test
    public void performForgetPasswrodClick(){
        onView(withId(R.id.textView_ForgetPassword)).perform(click());
    }


}
