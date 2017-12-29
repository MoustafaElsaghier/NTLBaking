package elsaghier.example.com.ntlbaking.IngredientWidgetPackage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by ELSaghier on 12/12/2017.
 */

@SuppressLint("NewApi")
public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        IngredientRemoteViewFactory remoteViewFactory = new IngredientRemoteViewFactory(this);
        return remoteViewFactory;
    }
}
