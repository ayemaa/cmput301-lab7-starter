package com.example.androiduitesting;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.Until;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ShowActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> scenario =
            new ActivityScenarioRule<>(MainActivity.class);

    private UiDevice device() {
        return UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    private String pkg() {
        return InstrumentationRegistry.getInstrumentation().getTargetContext().getPackageName();
    }

    private void addCityUI(String city) {
        UiDevice d = device();

        d.findObject(By.res(pkg(), "button_add")).click();
        d.findObject(By.res(pkg(), "editText_name")).setText(city);
        d.findObject(By.res(pkg(), "button_confirm")).click();

        d.waitForIdle();
    }

    // 1) Check whether the activity correctly switched
    @Test
    public void testActivitySwitched() {
        UiDevice d = device();
        addCityUI("Edmonton");

        // click the list item by its text (UIAutomator click, NOT Espresso)
        d.findObject(By.text("Edmonton")).click();

        // wait for ShowActivity UI to appear
        d.wait(Until.hasObject(By.res(pkg(), "text_city_name")), 2000);

        onView(withId(R.id.text_city_name)).check(matches(isDisplayed()));
    }

    // 2) Test whether the city name is consistent
    @Test
    public void testCityNameConsistent() {
        UiDevice d = device();
        addCityUI("Edmonton");

        d.findObject(By.text("Edmonton")).click();
        d.wait(Until.hasObject(By.res(pkg(), "text_city_name")), 2000);

        onView(withId(R.id.text_city_name)).check(matches(withText("Edmonton")));
    }

    // 3) Test the "back" button
    @Test
    public void testBackButton() {
        UiDevice d = device();
        addCityUI("Edmonton");

        d.findObject(By.text("Edmonton")).click();
        d.wait(Until.hasObject(By.res(pkg(), "button_back")), 2000);

        // back button (UIAutomator click is fine too)
        d.findObject(By.res(pkg(), "button_back")).click();

        d.wait(Until.hasObject(By.res(pkg(), "button_add")), 2000);
        onView(withId(R.id.button_add)).check(matches(isDisplayed()));
    }
}