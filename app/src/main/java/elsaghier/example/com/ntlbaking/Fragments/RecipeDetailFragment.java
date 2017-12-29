package elsaghier.example.com.ntlbaking.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import elsaghier.example.com.ntlbaking.Adapters.StepsAdapter;
import elsaghier.example.com.ntlbaking.InterFaces.RecipeInterface;
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
    public static List<StepModel> list;
    RecipeInterface recipeInterFace;
    boolean isTablet;
    public static int moveTo;
    List<IngredientsModel> mIngredientsList;

    RecyclerView.LayoutManager recyclerViewLayoutManager;
    StepsAdapter stepsAdapter;
    public static final String PREFS_NAME = "RECIPE_ITEM";
    public static final String RECIPE_NAME = "RECIPE_NAME";
    public static final String RECIPE_INGREDIENTS = "RECIPE_INGREDIENTS";

    public static RecipeDetailFragment newInstance(RecipeInterface recipeInterFace) {
        RecipeDetailFragment f = new RecipeDetailFragment();
        f.setRecipeInterFace(recipeInterFace);
        return f;
    }

    public RecipeDetailFragment() {
    }

    public static ResponseModel model;

    public void setRecipeInterFace(RecipeInterface recipeInterFace) {
        this.recipeInterFace = recipeInterFace;
    }

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
            if (!model.getName().equals("")) {

                isTablet = getResources().getBoolean(R.bool.isTab);
                mIngredientsList = model.getIngredients();
                recyclerViewLayoutManager = new LinearLayoutManager(getContext());
                mStepRecycler.setLayoutManager(recyclerViewLayoutManager);
                list = model.getSteps();

                stepsAdapter = new StepsAdapter(getContext(), list, isTablet);
                StringBuilder IngredientsData = new StringBuilder();
                mStepRecycler.setAdapter(stepsAdapter);
                saveToSharedPref(model.getName(), mIngredientsList);
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

        SharedPreferences settings = getContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Writing data to SharedPreferences
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(RECIPE_NAME, recipeName);

        String sIngredient = convertToString(ingrediants);
        editor.putString(RECIPE_INGREDIENTS, sIngredient);

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
