package elsaghier.example.com.ntlbaking;

import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import elsaghier.example.com.ntlbaking.Activities.RecipeDetail;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by ELSaghier on 12/12/2017.
 */

@RunWith(AndroidJUnit4.class)
public class RecipeDetailTest {


    @Rule
    public ActivityTestRule<RecipeDetail> activityTestRule =
            new ActivityTestRule<>(RecipeDetail.class);

    @Test
    public void isGradientDisplayed() {
        onView(withId(R.id.fragmentRecipeId))
                .perform(ViewActions.swipeDown());
    }

    @Test
    public void checkStepItemImageAvailability() {
        onData(withId(R.id.step_recyc)).atPosition(0).onChildView(isDisplayed());
    }

}
