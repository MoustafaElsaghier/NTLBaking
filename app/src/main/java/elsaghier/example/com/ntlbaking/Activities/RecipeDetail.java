package elsaghier.example.com.ntlbaking.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import elsaghier.example.com.ntlbaking.InterFaces.RecipeInterface;
import elsaghier.example.com.ntlbaking.R;

public class RecipeDetail extends AppCompatActivity implements RecipeInterface {
    boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        isTablet = getResources().getBoolean(R.bool.isTab);

    }

    @Override
    public void setStepId(int id) {

    }
}
