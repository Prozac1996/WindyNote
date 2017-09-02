package cn.lcu.threepersontravel.WindyNote.addwidget;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Administrator on 2017/7/23.
 */
public class MyWidgetService extends Service {

    private static final String TAG = "MyWidgetService";

    private static final String ACTION_UPDATE_ALL = "cn.lcu.lfz.widget.update_all";

    // 周期性更新 widget 的周期
    private static final int UPDATE_TIME = 5000;
    // 周期性更新 widget 的线程
    private UpdateThread mUpdateThread;
    private Context mContext;
    // 更新周期的计数
    private int count=0;

    @Override
    public void onCreate() {

        mUpdateThread = new UpdateThread();
        mUpdateThread.start();

        mContext = this.getApplicationContext();
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mUpdateThread != null){
            mUpdateThread.interrupt();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class UpdateThread extends Thread{

        @Override
        public void run() {
            super.run();

            try{
                count = 0;
                while(true){
                    Log.d(TAG,"run ...count:"+count);
                    count++;

                    Intent updateIntent = new Intent(ACTION_UPDATE_ALL);
                    mContext.sendBroadcast(updateIntent);
                    Thread.sleep(UPDATE_TIME);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
