package elsaghier.example.com.ntlbaking.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import elsaghier.example.com.ntlbaking.Models.ResponseModel;
import elsaghier.example.com.ntlbaking.R;

/**
 * Created by ELSaghier on 12/3/2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.MyViewHolder> {
    private List<ResponseModel> mRecipes;
    private Context context;


    public RecipeAdapter(Context context, List<ResponseModel> Recipes) {
        this.mRecipes = Recipes;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final ResponseModel model = mRecipes.get(position);
        holder.setRecipeImage(model.getImage());
        holder.setRecipeName(model.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                RecipeDetailsFragment.model = model;
//                Intent intent = new Intent(context, RecipeDetails.class);
//                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.recipe_image)
        ImageView recipeItem;
        @BindView(R.id.recipe_name)
        TextView recipeName;

        MyViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, itemView);

        }

        void setRecipeName(String recipe_Name) {
            recipeName.setText(recipe_Name);
        }

        void setRecipeImage(String link) {

            if (!link.equals(""))
                Glide.with(context).load(link).into(recipeItem);
        }

    }
}
