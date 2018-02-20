package com.figsinc.app.login;

import android.support.test.rule.ActivityTestRule;
import android.widget.EditText;
import android.widget.Spinner;

import com.figsinc.app.R;

import org.junit.Rule;
import org.junit.Test;

import butterknife.BindView;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

/**
 * Created by 461883 on 10/14/17.
 */

public class SignUpActivityEspressoTest {

    @Rule
    public ActivityTestRule<SignUpActivity> signUpActivityActivityTestRule = new ActivityTestRule<>(SignUpActivity.class);

    @Test
    public void caseNoUserName() {

        onView(withId(R.id.editTextFirstName)).perform(typeText(""));
        onView(withId(R.id.editTextLastName)).perform(typeText("Krish"));
        onView(withId(R.id.editTextEmailAddress)).perform(typeText("Rajesh.krish@figs.inc"));
        onView(withId(R.id.editTextPassword)).perform(typeText("Rajesh"));
        onView(withId(R.id.spinner_SelectCountry)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("India"))).perform(click());
        onView(withId(R.id.spinner_SelectCountry)).check(matches(withSpinnerText(containsString("India"))));
        onView(withId(R.id.buttonCreate)).perform(click());
    }

    @Test
    public void caseNoLastName() {

        onView(withId(R.id.editTextFirstName)).perform(typeText("Rajesh"));
        onView(withId(R.id.editTextLastName)).perform(typeText(""));
        onView(withId(R.id.editTextEmailAddress)).perform(typeText("Rajesh.krish@figs.inc"));
        onView(withId(R.id.editTextPassword)).perform(typeText("Rajesh"));
        onView(withId(R.id.spinner_SelectCountry)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("India"))).perform(click());
        onView(withId(R.id.spinner_SelectCountry)).check(matches(withSpinnerText(containsString("India"))));
        onView(withId(R.id.buttonCreate)).perform(click());
    }

    @Test
    public void caseNoUserPassword() {

        onView(withId(R.id.editTextFirstName)).perform(typeText("Rajesh"));
        onView(withId(R.id.editTextLastName)).perform(typeText("Krish"));
        onView(withId(R.id.editTextEmailAddress)).perform(typeText("Rajesh.krish@figs.inc"));
        onView(withId(R.id.editTextPassword)).perform(typeText(""));
        onView(withId(R.id.spinner_SelectCountry)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("India"))).perform(click());
        onView(withId(R.id.spinner_SelectCountry)).check(matches(withSpinnerText(containsString("India"))));
        onView(withId(R.id.buttonCreate)).perform(click());
    }

    @Test
    public void caseNoEmailId() {

        onView(withId(R.id.editTextFirstName)).perform(typeText("Rajesh"));
        onView(withId(R.id.editTextLastName)).perform(typeText("Krish"));
        onView(withId(R.id.editTextEmailAddress)).perform(typeText(""));
        onView(withId(R.id.editTextPassword)).perform(typeText("Raejsh"));
        onView(withId(R.id.spinner_SelectCountry)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("India"))).perform(click());
        onView(withId(R.id.spinner_SelectCountry)).check(matches(withSpinnerText(containsString("India"))));
        onView(withId(R.id.buttonCreate)).perform(click());
    }


    @Test
    public void caseWrongEmailFormat() {

        onView(withId(R.id.editTextFirstName)).perform(typeText("Rajesh"));
        onView(withId(R.id.editTextLastName)).perform(typeText("Krish"));
        onView(withId(R.id.editTextEmailAddress)).perform(typeText("rajesh@figs"));
        onView(withId(R.id.editTextPassword)).perform(typeText("Raejsh"));
        onView(withId(R.id.spinner_SelectCountry)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("India"))).perform(click());
        onView(withId(R.id.spinner_SelectCountry)).check(matches(withSpinnerText(containsString("India"))));
        onView(withId(R.id.buttonCreate)).perform(click());
    }


    @Test
    public void caseWrongEmailFormatTwo() {

        onView(withId(R.id.editTextFirstName)).perform(typeText("Rajesh"));
        onView(withId(R.id.editTextLastName)).perform(typeText("Krish"));
        onView(withId(R.id.editTextEmailAddress)).perform(typeText("rajeshfigs.inc"));
        onView(withId(R.id.editTextPassword)).perform(typeText("Raejsh"));
        onView(withId(R.id.spinner_SelectCountry)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("India"))).perform(click());
        onView(withId(R.id.spinner_SelectCountry)).check(matches(withSpinnerText(containsString("India"))));
        onView(withId(R.id.buttonCreate)).perform(click());
    }


    @Test
    public void caseWrongEmailFormatThree() {

        onView(withId(R.id.editTextFirstName)).perform(typeText("Rajesh"));
        onView(withId(R.id.editTextLastName)).perform(typeText("Krish"));
        onView(withId(R.id.editTextEmailAddress)).perform(typeText("rajesh@figs..inc"));
        onView(withId(R.id.editTextPassword)).perform(typeText("Raejsh"));
        onView(withId(R.id.spinner_SelectCountry)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("India"))).perform(click());
        onView(withId(R.id.spinner_SelectCountry)).check(matches(withSpinnerText(containsString("India"))));
        onView(withId(R.id.buttonCreate)).perform(click());
    }

    @Test
    public void caseWrongEmailFormatFour() {

        onView(withId(R.id.editTextFirstName)).perform(typeText("Rajesh"));
        onView(withId(R.id.editTextLastName)).perform(typeText("Krish"));
        onView(withId(R.id.editTextEmailAddress)).perform(typeText("rajesh@figs"));
        onView(withId(R.id.editTextPassword)).perform(typeText("Raejsh"));
        onView(withId(R.id.spinner_SelectCountry)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("India"))).perform(click());
        onView(withId(R.id.spinner_SelectCountry)).check(matches(withSpinnerText(containsString("India"))));
        onView(withId(R.id.buttonCreate)).perform(click());
    }


    @Test
    public void caseNoCountry() {

        onView(withId(R.id.editTextFirstName)).perform(typeText("Rajesh"));
        onView(withId(R.id.editTextLastName)).perform(typeText("Krish"));
        onView(withId(R.id.editTextEmailAddress)).perform(typeText("rajesh@figs"));
        onView(withId(R.id.editTextPassword)).perform(typeText("Raejsh"));
        //onView(withId(R.id.spinner_SelectCountry)).perform(click());
        //onData(allOf(is(instanceOf(String.class)), is("India"))).perform(click());
        //onView(withId(R.id.spinner_SelectCountry)).check(matches(withSpinnerText(containsString("India"))));
        onView(withId(R.id.buttonCreate)).perform(click());
    }

    @Test
    public void caseSuccess() {

        onView(withId(R.id.editTextFirstName)).perform(typeText("Rajesh"));
        onView(withId(R.id.editTextLastName)).perform(typeText("Krish"));
        onView(withId(R.id.editTextEmailAddress)).perform(typeText("rajesh@figs.inc"));
        onView(withId(R.id.editTextPassword)).perform(typeText("Raejsh"));
        onView(withId(R.id.spinner_SelectCountry)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("India"))).perform(click());
        onView(withId(R.id.spinner_SelectCountry)).check(matches(withSpinnerText(containsString("India"))));
        onView(withId(R.id.buttonCreate)).perform(click());
    }
}
