package elsaghier.example.com.ntlbaking;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import elsaghier.example.com.ntlbaking.Activities.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by ELSaghier on 12/12/2017.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void checkRecyclerAvailability() {
        onView(withId(R.id.recipe_list)).check(matches(isDisplayed()));
    }

    @Test
    public void recyclerClick() throws InterruptedException {
        onView(withId(R.id.recipe_list))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText("Cheesecake")), click()));
        Thread.sleep(2000);
        onView(withId(R.id.fragmentRecipeId))
                .perform(ViewActions.swipeDown());
        onView(withId(R.id.textView)).check(matches(withText("Ingredients"))).check(matches(isDisplayed()));
    }
}