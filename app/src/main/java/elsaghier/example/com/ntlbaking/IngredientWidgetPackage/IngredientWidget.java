package elsaghier.example.com.ntlbaking.IngredientWidgetPackage;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.Objects;

import elsaghier.example.com.ntlbaking.R;

import static elsaghier.example.com.ntlbaking.IngredientWidgetPackage.IngredientRemoteViewFactory.PREFS_NAME;
import static elsaghier.example.com.ntlbaking.IngredientWidgetPackage.IngredientRemoteViewFactory.RECIPE_NAME;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientWidget extends AppWidgetProvider {
    public static final String ACTION_TOAST = "elsaghier.example.com.ntlbaking.widgets.ACTION_TOAST";
    public static final String EXTRA_STRING = "elsaghier.example.com.ntlbaking.widgets.EXTRA_STRING";

    Context c;
    private String recipeName = "";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Objects.equals(intent.getAction(), ACTION_TOAST)) {
            String item = intent.getExtras().getString(EXTRA_STRING);
            Toast.makeText(context, item, Toast.LENGTH_LONG).show();
        }
        c = context;
        super.onReceive(context, intent);
    }

    private void readFromSharedPref() {
        SharedPreferences setting = c.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        recipeName = setting.getString(RECIPE_NAME, "");
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int widgetId : appWidgetIds) {
            RemoteViews mView = initViews(context, appWidgetManager, widgetId);
            readFromSharedPref();
            mView.setTextViewText(R.id.appwidget_text, recipeName);


            // Adding collection modelList item handler
            final Intent onItemClick = new Intent(context, IngredientWidget.class);
            onItemClick.setAction(ACTION_TOAST);
            onItemClick.setData(Uri.parse(onItemClick
                    .toUri(Intent.URI_INTENT_SCHEME)));
            final PendingIntent onClickPendingIntent = PendingIntent
                    .getBroadcast(context, 0, onItemClick,
                            PendingIntent.FLAG_UPDATE_CURRENT);
            mView.setPendingIntentTemplate(R.id.appwidget_list,
                    onClickPendingIntent);

            appWidgetManager.updateAppWidget(widgetId, mView);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private RemoteViews initViews(Context context,
                                  AppWidgetManager widgetManager, int widgetId) {

        RemoteViews mView = new RemoteViews(context.getPackageName(),
                R.layout.ingredient_widget);

        Intent intent = new Intent(context, WidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);

        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        mView.setRemoteAdapter(widgetId, R.id.appwidget_list, intent);

        return mView;
    }

}

