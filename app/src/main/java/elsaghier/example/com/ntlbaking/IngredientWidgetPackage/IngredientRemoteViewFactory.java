package elsaghier.example.com.ntlbaking.IngredientWidgetPackage;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

import elsaghier.example.com.ntlbaking.Models.IngredientsModel;
import elsaghier.example.com.ntlbaking.R;

import static elsaghier.example.com.ntlbaking.Activities.MainActivity.list;

/**
 * Created by ELSaghier on 12/12/2017.
 */

public class IngredientRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext = null;
    private int appWidgetId;
    private List<IngredientsModel> ingredients = new ArrayList<>();

    IngredientRemoteViewFactory(Context mContext, Intent intent) {
        this.mContext = mContext;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        if (mContext != null && list != null)
            ingredients = list.get(0).getIngredients();
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        if (ingredients == null)
            return 0;
        return ingredients.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {

        if (list != null) {
            RemoteViews row = new RemoteViews(mContext.getPackageName(),
                    R.layout.widget_row);

            ingredients = list.get(i).getIngredients();
            String rowData = ingredients.get(i).getIngredient() + "\n" +
                    ingredients.get(i).getQuantity() + ingredients.get(i).getQuantity();

            row.setTextViewText(R.id.Ingredient_item, rowData);

//            Intent intent = new Intent();
//            intent.putExtra(IngredientWidget.EXTRA_WORD, ingredients.get(i));
//
//            row.setOnClickFillInIntent(R.id.Ingredient_item, intent);
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
