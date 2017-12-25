package elsaghier.example.com.ntlbaking.IngredientWidgetPackage;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import elsaghier.example.com.ntlbaking.Activities.MainActivity;
import elsaghier.example.com.ntlbaking.R;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientWidget extends AppWidgetProvider {

    public static String EXTRA_WORD = "elsaghier.example.com.bakingappntl.BakingWidgetClasses";

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredient_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            Intent svcIntent = new Intent(context, WidgetService.class);

            svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews widget = new RemoteViews(context.getPackageName(),
                    R.layout.ingredient_widget);

            widget.setRemoteAdapter(appWidgetId, R.id.appwidget_list,
                    svcIntent);

            Intent clickIntent = new Intent(context, MainActivity.class);
            PendingIntent clickPI = PendingIntent
                    .getActivity(context, 0,
                            clickIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT);

            widget.setPendingIntentTemplate(R.id.appwidget_list, clickPI);

            appWidgetManager.updateAppWidget(appWidgetId, widget);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

}

