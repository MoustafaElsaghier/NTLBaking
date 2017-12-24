package elsaghier.example.com.ntlbaking.IngredientWidgetPackage;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by ELSaghier on 12/12/2017.
 */

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
//        int appWidgetId = intent.getIntExtra(
//                AppWidgetManager.EXTRA_APPWIDGET_ID,
//                AppWidgetManager.INVALID_APPWIDGET_ID);
        return (new IngredientRemoteViewFactory(this.getApplicationContext(),intent));
    }
}
