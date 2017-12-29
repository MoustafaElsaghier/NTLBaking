package elsaghier.example.com.ntlbaking.IngredientWidgetPackage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import elsaghier.example.com.ntlbaking.Fragments.RecipeDetailFragment;
import elsaghier.example.com.ntlbaking.R;


public class IngredientRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext = null;
    private List<String> ingredients = new ArrayList<>();
    private String recipeName, recipeIngredient;

    private void readFromSharedPref() {

        SharedPreferences setting = mContext.getSharedPreferences(RecipeDetailFragment.PREFS_NAME, Context.MODE_PRIVATE);
        if (setting.contains(RecipeDetailFragment.PREFS_NAME)) {
            recipeName = setting.getString(RecipeDetailFragment.RECIPE_NAME, "");
            recipeIngredient = setting.getString(RecipeDetailFragment.RECIPE_INGREDIENTS, "");
        }
    }

    private List<String> getIngredientsList(String s) {
        List<String> list = new ArrayList<>();

        String[] data = s.split("#");
        list.addAll(Arrays.asList(data));
        return list;
    }

    IngredientRemoteViewFactory(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onCreate() {
        if (mContext != null) {
            readFromSharedPref();
            ingredients = getIngredientsList(recipeIngredient);
        }
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        if (ingredients.size() == 0)
            return 0;
        return ingredients.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {

        if (ingredients != null) {
            RemoteViews row = new RemoteViews(mContext.getPackageName(),
                    R.layout.widget_row);

            String rowData = ingredients.get(i);
            row.setTextViewText(R.id.appwidget_text, recipeName);
            row.setTextViewText(R.id.Ingredient_item, rowData);

            final Intent fillInIntent = new Intent();

            fillInIntent.setAction(IngredientWidget.ACTION_TOAST);

            final Bundle bundle = new Bundle();
            bundle.putString(IngredientWidget.EXTRA_STRING,
                    rowData);

            fillInIntent.putExtras(bundle);
            row.setOnClickFillInIntent(R.layout.ingredient_widget, fillInIntent);
            return row;
        }
        return null;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
