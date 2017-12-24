package elsaghier.example.com.ntlbaking.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import elsaghier.example.com.ntlbaking.Fragments.StepDetailsFragment;
import elsaghier.example.com.ntlbaking.R;

public class StepDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle b = getIntent().getExtras();

        System.out.println("XXX" +b);
        if (savedInstanceState == null) {
            StepDetailsFragment detailsFragment = new StepDetailsFragment();
            detailsFragment.setArguments(b);
            getSupportFragmentManager().beginTransaction().replace(R.id.containerItem, detailsFragment).commit();
        }

    }

}
