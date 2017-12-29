package elsaghier.example.com.ntlbaking.IngredientWidgetPackage;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import elsaghier.example.com.ntlbaking.R;


public class IngredientRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext = null;
    private List<String> ingredients = new ArrayList<>();
    private String  recipeIngredient;
    public static final String PREFS_NAME = "RECIPE_ITEM";
    public static final String RECIPE_NAME = "RECIPE_NAME";
    public static final String RECIPE_INGREDIENTS = "RECIPE_INGREDIENTS";

    private void readFromSharedPref() {
        SharedPreferences setting = mContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        recipeIngredient = setting.getString(RECIPE_INGREDIENTS, "");
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
            row.setTextViewText(R.id.Ingredient_item, rowData);

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
