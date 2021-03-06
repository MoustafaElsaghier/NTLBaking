package elsaghier.example.com.ntlbaking.Activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import elsaghier.example.com.ntlbaking.Adapters.RecipeAdapter;
import elsaghier.example.com.ntlbaking.ApiWork.ApiClient;
import elsaghier.example.com.ntlbaking.InterFaces.ResponseInterFace;
import elsaghier.example.com.ntlbaking.Models.ResponseModel;
import elsaghier.example.com.ntlbaking.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    ResponseInterFace responseInterFace;
    RecipeAdapter recipeAdapter;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    public static List<ResponseModel> list;
    @BindView(R.id.recipe_list)
    RecyclerView mRecipeRecycler;
    retrofit2.Call<List<ResponseModel>> call;
    boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();

    }

    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    private void init() {
        ButterKnife.bind(this);
        isTablet = getResources().getBoolean(R.bool.isTab);

        if (isTablet)
            recyclerViewLayoutManager = new GridLayoutManager(this, 3);
        else
            recyclerViewLayoutManager = new LinearLayoutManager(this);
        mRecipeRecycler.setLayoutManager(recyclerViewLayoutManager);
        responseInterFace = ApiClient.getClient().create(ResponseInterFace.class);
        if (isConnected()) {
            call = responseInterFace.getRecipes();
            call.enqueue(new Callback<List<ResponseModel>>() {
                @Override
                public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                    list = response.body();
                    recipeAdapter = new RecipeAdapter(MainActivity.this, list);
                    mRecipeRecycler.setAdapter(recipeAdapter);
                }

                @Override
                public void onFailure(Call<List<ResponseModel>> call, Throwable t) {
                    System.out.println();
                    Log.e("NetworkFailed", t.getCause().toString());
                }
            });
        } else
            Snackbar.make(getCurrentFocus(), "Error in Network", Snackbar.LENGTH_LONG);
    }
}
