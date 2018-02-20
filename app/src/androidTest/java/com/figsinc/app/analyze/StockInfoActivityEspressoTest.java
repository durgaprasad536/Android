package com.figsinc.app.analyze;

import android.support.test.rule.ActivityTestRule;

import com.figsinc.app.R;
import com.figsinc.app.analyze.trendingstocks.StockInfoActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.core.StringStartsWith.startsWith;

/**
 * Created by 461883 on 10/15/17.
 */

public class StockInfoActivityEspressoTest {

     @Rule
   public ActivityTestRule<StockInfoActivity> stockInfoActivityActivityTestRule = new ActivityTestRule<StockInfoActivity>(StockInfoActivity.class);

    @Test
    public void clickCalculator() {
        onView(withId(R.id.button_calculator)).perform(click());
    }

    @Test
    public void clickSave() {
        onView(withId(R.id.button_save)).perform(click());
    }

    @Test
    public void clickDllar() {
        onView(withId(R.id.button_dollar)).perform(click());
        onView(withId(R.id.liearWeb1)).perform(click());
        pressBack();
    }

    @Test
    public void clickLearn() {
        onView(withId(R.id.imageViewLearn)).perform(click());
        pressBack();
    }

    @Test
    public void clickCollect() {
        onView(withId(R.id.imageViewCollect)).perform(click());
        pressBack();
    }

    @Test
    public void clickFigs() {
        onView(withId(R.id.imageViewFigs)).perform(click());
        pressBack();
    }

    @Test
    public void clickFeed() {
        onView(withId(R.id.linearFeed)).perform(click());
        pressBack();
    }

    @Test
    public void clickAnalyze() {
        onView(withId(R.id.imageViewAnalyse)).perform(click());
        pressBack();
    }

    @Test
    public void clickEntities_showDetails() {
        onView(withId(R.id.entities_showDetails)).perform(click());
    }

    @Test
    public void scrollPage() {
        onView(withId(R.id.coordinator)).perform(swipeDown()).perform(swipeDown()).perform(swipeDown());
    }

    @Test
    public void ValuationClick(){
        onData(hasToString(startsWith("Valuation")))
                .inAdapterView(withId(R.id.filter1_recyclerView))
                .perform(click());
        pressBack();
    }

    @Test
    public void ProfitabilityClick(){
        onData(hasToString(startsWith("Profitability")))
                .inAdapterView(withId(R.id.filter1_recyclerView))
                .perform(click());
        pressBack();
    }

    @Test
    public void EfficiencyClick(){
        onData(hasToString(startsWith("Efficiency")))
                .inAdapterView(withId(R.id.filter1_recyclerView))
                .perform(click());
        pressBack();
    }

    @Test
    public void capitalStructureClick(){
        onData(hasToString(startsWith("Capital Structure")))
                .inAdapterView(withId(R.id.filter1_recyclerView))
                .perform(click());
        pressBack();
    }

}
