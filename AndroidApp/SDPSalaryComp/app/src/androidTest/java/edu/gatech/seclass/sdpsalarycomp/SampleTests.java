package edu.gatech.seclass.sdpsalarycomp;

        import org.junit.Rule;
        import org.junit.Test;
        import org.junit.runner.RunWith;

        import android.support.test.espresso.Espresso;
        import android.support.test.espresso.action.ViewActions;
        import android.support.test.rule.ActivityTestRule;
        import android.support.test.runner.AndroidJUnit4;

        import static android.support.test.espresso.Espresso.onData;
        import static android.support.test.espresso.Espresso.onView;
        import static android.support.test.espresso.action.ViewActions.clearText;
        import static android.support.test.espresso.action.ViewActions.click;
        import static android.support.test.espresso.action.ViewActions.typeText;
        import static android.support.test.espresso.assertion.ViewAssertions.matches;
        import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
        import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
        import static android.support.test.espresso.matcher.ViewMatchers.withId;
        import static android.support.test.espresso.matcher.ViewMatchers.withText;
        import static org.hamcrest.core.AllOf.allOf;
        import static org.hamcrest.core.Is.is;
        import static org.hamcrest.core.IsInstanceOf.instanceOf;
        import static org.hamcrest.core.StringContains.containsString;


@RunWith(AndroidJUnit4.class)
public class SampleTests {

    @Rule
    public ActivityTestRule<SDPSalaryCompActivity> tActivityRule = new ActivityTestRule<>(
            SDPSalaryCompActivity.class);

    //basic test to verify Espresso tests can see the required fields
    @Test
    public void test_Required_Names() {
        Espresso.onView(withId(R.id.baseSalary)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.currentCity)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.newCity)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.targetSalary)).check(matches(isDisplayed()));
    }

    @Test
    public void Screenshot1SalaryCheck() {

        onView(withId(R.id.baseSalary)).perform(clearText(), typeText("100501"), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.currentCity)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Atlanta, GA"))).perform(click());

        onView(withId(R.id.newCity)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Austin, TX"))).perform(click());

        Espresso.closeSoftKeyboard();

        onView(withId(R.id.targetSalary)).check(matches(withText("95476")));

    }

    @Test
    public void Screenshot2SalaryCheck() {

        onView(withId(R.id.baseSalary)).perform(clearText(), typeText("45000"), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.currentCity)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("New York City, NY"))).perform(click());

        onView(withId(R.id.newCity)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("San Francisco, CA"))).perform(click());

        Espresso.closeSoftKeyboard();

        onView(withId(R.id.targetSalary)).check(matches(withText("46746")));

    }

    @Test
    public void Screenshot3SalaryCheck() {

        onView(withId(R.id.baseSalary)).perform(clearText(), typeText("111111"), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.currentCity)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Atlanta, GA"))).perform(click());

        onView(withId(R.id.newCity)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("New York City, NY"))).perform(click());

        Espresso.closeSoftKeyboard();

        onView(withId(R.id.targetSalary)).check(matches(withText("161111")));

    }

    @Test
    public void ScreenshotErrorCheck() {

        onView(withId(R.id.baseSalary)).perform(clearText(), typeText(""), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.currentCity)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Seattle, WA"))).perform(click());

        onView(withId(R.id.newCity)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Washington D.C."))).perform(click());

        Espresso.closeSoftKeyboard();

        onView(withId(R.id.baseSalary)).check(matches(hasErrorText("Invalid salary")));

    }

    @Test
    public void Screenshot5SalaryCheck() {

        onView(withId(R.id.baseSalary)).perform(clearText(), typeText("105275"), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.currentCity)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Honolulu, HI"))).perform(click());

        onView(withId(R.id.newCity)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Springfield, MO"))).perform(click());

        Espresso.closeSoftKeyboard();

        onView(withId(R.id.targetSalary)).check(matches(withText("59708")));

    }

}