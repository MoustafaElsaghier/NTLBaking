package elsaghier.example.com.ntlbaking.IngredientWidgetPackage;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
        if (list == null)
            return 0;
        return list.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {

        if (list != null) {
            RemoteViews row = new RemoteViews(mContext.getPackageName(),
                    R.layout.widget_row);

            ingredients = list.get(i).getIngredients();

            String rowData = ingredients.get(i).getIngredient() +
                    "     " +
                    ingredients.get(i).getQuantity() + "  " +
                    ingredients.get(i).getMeasure();
            row.setTextViewText(R.id.appwidget_text,"Ingredient");
            row.setTextViewText(R.id.Ingredient_item, rowData);

            final Intent fillInIntent = new Intent();

            fillInIntent.setAction(IngredientWidget.ACTION_TOAST);

            final Bundle bundle = new Bundle();
            bundle.putString(IngredientWidget.EXTRA_STRING,
                    rowData);

            fillInIntent.putExtras(bundle);
            row.setOnClickFillInIntent(android.R.id.text1, fillInIntent);
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
