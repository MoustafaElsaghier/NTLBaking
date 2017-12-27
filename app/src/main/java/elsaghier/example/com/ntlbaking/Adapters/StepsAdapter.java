package elsaghier.example.com.ntlbaking.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import elsaghier.example.com.ntlbaking.Activities.StepDetails;
import elsaghier.example.com.ntlbaking.Fragments.RecipeDetailFragment;
import elsaghier.example.com.ntlbaking.Fragments.StepDetailsFragment;
import elsaghier.example.com.ntlbaking.Models.StepModel;
import elsaghier.example.com.ntlbaking.R;

/**
 * Created by ELSaghier on 12/3/2017.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.MyViewHolder> {

    private List<StepModel> mSteps;
    private Context context;
    //    private RecipeInterface recipeInterface;
    private boolean isTablet;


    public StepsAdapter(Context context, List<StepModel> mSteps, Boolean isTablet) {
        this.mSteps = mSteps;
        this.context = context;
        this.isTablet = isTablet;
    }

    @Override

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final StepModel model = mSteps.get(position);
        holder.setRecipeImage(model.getThumbnailURL());
        holder.setRecipeName(model.getShortDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                recipeInterface.setStepId(holder.getAdapterPosition());
                if (isTablet) {
                    // tablet
                    StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
                    Bundle b = new Bundle();
                    b.putInt("pos", holder.getAdapterPosition());
                    stepDetailsFragment.setArguments(b);
                    ((AppCompatActivity) context).getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.pane_2, stepDetailsFragment)
                            .commit();
                } else {
                    //single
                    Intent intent = new Intent(context, StepDetails.class);
                    intent.putExtra("pos", holder.getAdapterPosition());
                    context.startActivity(intent);
                }
                RecipeDetailFragment.moveTo = position;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSteps.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.step_image)
        ImageView mStepImge;
        @BindView(R.id.step_shortDescription)
        TextView stepName;

        MyViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, itemView);
        }

        void setRecipeName(String recipe_Name) {
            stepName.setText(recipe_Name);
        }

        void setRecipeImage(String link) {

            if (!TextUtils.isEmpty(link))
                Glide.with(context).load(link).into(mStepImge);
        }

    }
}
