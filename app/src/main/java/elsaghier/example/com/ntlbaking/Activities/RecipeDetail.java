package elsaghier.example.com.ntlbaking.Activities;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import elsaghier.example.com.ntlbaking.Fragments.RecipeDetailFragment;
import elsaghier.example.com.ntlbaking.R;

public class RecipeDetail extends AppCompatActivity /*implements RecipeInterface*/ {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState == null) {
            RecipeDetailFragment detailFragment = new RecipeDetailFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.pane_1, detailFragment).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
