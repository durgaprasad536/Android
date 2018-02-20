package com.figsinc.app.analyze;

import android.content.Intent;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;

import com.figsinc.app.R;
import com.figsinc.app.analyze.model.TrendingStocks;
import com.figsinc.app.analyze.sentiments.SentimentsFragment;
import com.figsinc.app.analyze.trendingstocks.TrendingStocksRecyclerViewAdapter;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.core.StringStartsWith.startsWith;

/**
 * Created by 461883 on 10/14/17.
 */


@FixMethodOrder (MethodSorters.NAME_ASCENDING)
public class AnalyzeActivityEspressoTest {

    public SentimentsFragment fragment;
    public AnalyzeActivity activity;

    @Rule
    public ActivityTestRule<AnalyzeActivity> analyzeActivityActivityTestRule = new ActivityTestRule<>(AnalyzeActivity.class, false, true);

     @Test
    public void swipeViewPagerLeftAndRight() {
        onView(withId(R.id.viewpager)).perform(swipeLeft()).perform(swipeLeft()).perform(swipeLeft());
        onView(withId(R.id.viewpager)).perform(swipeRight()).perform(swipeRight()).perform(swipeRight());
    }

    @Test
    public void swipeViewPagerUpandDown() {
        onView(withId(R.id.viewpager)).perform(swipeDown()).perform(swipeDown()).perform(swipeDown());
    }

    @Test
    public void clickLearn() {
        onView(withId(R.id.imageViewLearn)).perform(click());
    }

    @Test
    public void clickCollect() {
        onView(withId(R.id.imageViewCollect)).perform(click());
    }

    @Test
    public void clickFigs() {
        onView(withId(R.id.imageViewFigs)).perform(click());
    }

    @Test
    public void clickFeed() {
        onView(withId(R.id.linearFeed)).perform(click());
    }

    @Test
    public void clickAnalyze() {
        onView(withId(R.id.imageViewAnalyse)).perform(click());
    }


    @Test
    public void setimentsListItemClick() {

        onView(withId(R.id.viewpager)).perform(swipeLeft());

             ViewInteraction recyclerView = onView(
                    allOf(withId(R.id.listTrendingStocks),
                            withParent(withId(R.id.viewpager)),
                            isDisplayed()));
            recyclerView.perform(actionOnItemAtPosition(0, click()));


       /* onData(anything()).inAdapterView(allOf(
                isAssignableFrom(AdapterView.class),
                isDescendantOfA(withId(R.id.viewpager)),
                isDisplayed()))
                .atPosition(1).perform(click());
*/
       //matches(hasDescendant(withText("Position #2")))




       /* onData(allOf(is(AnyTextView.class), withText("Item1"))).perform(click());
        onData(Matchers.allOf(Matchers.is(Matchers.instanceOf(ListView.class)), Matchers.hasToString(Matchers.startsWith("Item1")))).perform(ViewActions.click());
        onData(anything()).inAdapterView(withContentDescription("menu_item_icon")).atPosition(0).perform(click());
     */

      /* onData(hasToString(startsWith("Shire")))
                .inAdapterView(withId(R.id.listTrendingStocks))
                .perform(click());*/

//        onData(Matchers.allOf(Matchers.is(Matchers.instanceOf(TrendingStocksRecyclerViewAdapter.class)), Matchers.hasToString(Matchers.startsWith("Shire")))).perform(ViewActions.click());

/*
            ViewInteraction recyclerView2 = onView(
                    allOf(withId(R.id.listTrendingStocks),
                            withParent(withId(R.id.viewpager)),
                            isDisplayed()));
            recyclerView2.perform(actionOnItemAtPosition(3, click()));

        pressBack();*/

       // pressBack();

    }


    @Test
    public void trendingStocksListItemClick() {

        onView(withId(R.id.viewpager)).perform(swipeLeft());

        for (int i = 0; i < 1; i++) {

            ViewInteraction recyclerView = onView(
                    allOf(withId(R.id.listTrendingStocks),
                            withParent(withId(R.id.viewpager)),
                            isDisplayed()));
            recyclerView.perform(actionOnItemAtPosition(i, click()));
        }
        pressBack();
    }

    @Test
    public void trendingFundsListItemClick() {

        onView(withId(R.id.viewpager)).perform(swipeLeft()).perform(swipeLeft());

        for (int i = 0; i < 1; i++) {

            ViewInteraction recyclerView = onView(
                    allOf(withId(R.id.listTrendingStocks),
                            withParent(withId(R.id.viewpager)),
                            isDisplayed()));
            recyclerView.perform(actionOnItemAtPosition(i, click()));
        }
        pressBack();
    }


    @Test
    public void trendingAnalystsListItemClick() {

        onView(withId(R.id.viewpager)).perform(swipeLeft()).perform(swipeLeft()).perform(swipeLeft());

        for (int i = 0; i < 1; i++) {

            ViewInteraction recyclerView = onView(
                    allOf(withId(R.id.listSentiments),
                            withParent(withId(R.id.viewpager)),
                            isDisplayed()));
            recyclerView.perform(actionOnItemAtPosition(i, click()));
        }
        pressBack();
    }

    @Test
    public void setimentsListScroll() {

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.listSentiments),
                        withParent(withId(R.id.viewpager)),
                        isDisplayed()));
        recyclerView.perform(scrollToPosition(5));


    }

}
