package elsaghier.example.com.ntlbaking.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import elsaghier.example.com.ntlbaking.Adapters.StepsAdapter;
import elsaghier.example.com.ntlbaking.IngredientWidgetPackage.IngredientRemoteViewFactory;
import elsaghier.example.com.ntlbaking.IngredientWidgetPackage.WidgetService;
import elsaghier.example.com.ntlbaking.Models.IngredientsModel;
import elsaghier.example.com.ntlbaking.Models.ResponseModel;
import elsaghier.example.com.ntlbaking.Models.StepModel;
import elsaghier.example.com.ntlbaking.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * A placeholder fragment containing a simple view.
 */
public class RecipeDetailFragment extends Fragment {
    @BindView(R.id.Ingredients)
    TextView mIngredients;
    @BindView(R.id.step_recyc)
    RecyclerView mStepRecycler;
    public static List<StepModel> modelList;
    boolean isTablet;
    public static int moveTo;
    List<IngredientsModel> mIngredientsList;

    RecyclerView.LayoutManager recyclerViewLayoutManager;
    StepsAdapter stepsAdapter;

    public RecipeDetailFragment() {
    }

    public static ResponseModel model;

    @Override
    public void onResume() {
        super.onResume();
        mStepRecycler.smoothScrollToPosition(moveTo);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        ButterKnife.bind(this, v);
        if (model != null)
            if (!TextUtils.isEmpty(model.getName())) {

                isTablet = getResources().getBoolean(R.bool.isTab);
                mIngredientsList = model.getIngredients();
                recyclerViewLayoutManager = new LinearLayoutManager(getContext());
                mStepRecycler.setLayoutManager(recyclerViewLayoutManager);
                modelList = model.getSteps();
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(model.getName());
                stepsAdapter = new StepsAdapter(getContext(), modelList, isTablet);
                StringBuilder IngredientsData = new StringBuilder();
                mStepRecycler.setAdapter(stepsAdapter);
                saveToSharedPref(model.getName(), mIngredientsList);
                WidgetService m = new WidgetService();
                m.onGetViewFactory(getActivity().getIntent());
                IngredientsData.append("\n");
                for (IngredientsModel item : mIngredientsList) {
                    IngredientsData.append(item.getIngredient()).append(" : \t ")
                            .append(item.getQuantity()).append(" ")
                            .append(item.getMeasure()).
                            append("\n\n");
                }
                mIngredients.setText(IngredientsData.toString());
            }

        return v;
    }

    private void saveToSharedPref(String recipeName, List<IngredientsModel> ingrediants) {

        SharedPreferences settings = getContext().getSharedPreferences(IngredientRemoteViewFactory.PREFS_NAME, MODE_PRIVATE);

        // Writing data to SharedPreferences
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(IngredientRemoteViewFactory.RECIPE_NAME, recipeName);

        String sIngredient = convertToString(ingrediants);
        editor.putString(IngredientRemoteViewFactory.RECIPE_INGREDIENTS, sIngredient);

        editor.putString("key", "some value");
        editor.apply();
    }

    private String convertToString(List<IngredientsModel> list) {
        StringBuilder res = new StringBuilder();
        for (IngredientsModel item : mIngredientsList) {
            res.append(item.getIngredient()).append(" :   ")
                    .append(item.getQuantity()).append(" ")
                    .append(item.getMeasure()).
                    append("#");
        }
        return res.toString();
    }

}
