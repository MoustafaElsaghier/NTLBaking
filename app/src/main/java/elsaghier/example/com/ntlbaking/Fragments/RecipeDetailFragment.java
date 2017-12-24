package elsaghier.example.com.ntlbaking.Fragments;

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

    RecyclerView.LayoutManager recyclerViewLayoutManager;
    StepsAdapter stepsAdapter;

    public RecipeDetailFragment() {
    }

    public static ResponseModel model;

    public void setRecipeInterFace(RecipeInterface recipeInterFace) {
        this.recipeInterFace = recipeInterFace;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
//        model = (ResponseModel) getArguments().get("model");
        ButterKnife.bind(this, v);
        if (!model.getName().equals("")) {
            List<IngredientsModel> mIngredientsList = model.getIngredients();
            recyclerViewLayoutManager = new LinearLayoutManager(getContext());
            mStepRecycler.setLayoutManager(recyclerViewLayoutManager);
            list = model.getSteps();
            stepsAdapter = new StepsAdapter(getContext(), list, recipeInterFace);
            StringBuilder IngredientsData = new StringBuilder();
            mStepRecycler.setAdapter(stepsAdapter);
//            ((RecipeDetails) getActivity()).setActionBarTittle(model.getName());
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

}
