package elsaghier.example.com.ntlbaking.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import elsaghier.example.com.ntlbaking.Fragments.RecipeDetailFragment;
import elsaghier.example.com.ntlbaking.Fragments.StepDetailsFragment;
import elsaghier.example.com.ntlbaking.InterFaces.RecipeInterface;
import elsaghier.example.com.ntlbaking.R;

public class RecipeDetail extends AppCompatActivity implements RecipeInterface {
    private boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        isTablet = getResources().getBoolean(R.bool.isTab);
        if (savedInstanceState == null) {
            RecipeDetailFragment detailFragment =  RecipeDetailFragment.newInstance(this);
            getSupportFragmentManager().beginTransaction().replace(R.id.pane_1, detailFragment).commit();
        }
    }

    @Override
    public void setStepId(int id) {
        if (isTablet) {
            // tablet
            StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
            Bundle b = new Bundle();
            b.putInt("pos", id);
            stepDetailsFragment.setArguments(b);
            getSupportFragmentManager().beginTransaction().replace(R.id.pane_2  , stepDetailsFragment).commit();
        } else {
            //single
            Intent intent = new Intent(this, StepDetails.class);
            intent.putExtra("pos", id);
            startActivity(intent);
        }
    }
}
