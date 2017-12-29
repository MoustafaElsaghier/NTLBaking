package elsaghier.example.com.ntlbaking.IngredientWidgetPackage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.widget.RemoteViewsService;

@SuppressLint("NewApi")
public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new IngredientRemoteViewFactory(this);
    }
}
