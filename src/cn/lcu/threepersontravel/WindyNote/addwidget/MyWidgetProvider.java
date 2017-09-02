package cn.lcu.threepersontravel.WindyNote.addwidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/7/6.
 */
public class MyWidgetProvider extends AppWidgetProvider {

    private final Intent START_SERVICE_INTENT = new Intent("android.appwidget.action.EXAMPLE_APP_WI_ALL = "cn.lcu.lfz.widget.update_all";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.d("lfz","onUpdate");
    }

    @Override
    public void onEnabled(Context context) {
        Log.d("lfz","onEnabled");
        context.startService(START_SERVICE_INTENT);
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        context.stopService(START_SERVICE_INTENT);
        super.onDisabled(context);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if(ACTION_UPDATE_ALL.equals(action)){
            Toast.makeText(context,"适当的响一下！",Toast.LENGTH_SHORT).show();
        }

        super.onReceive(context, intent);

    }
}
