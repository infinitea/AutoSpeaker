package com.tea.autospeaker;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

public class AutoSpeakWidgetProvider extends AppWidgetProvider {

	@Override
	public void onReceive(Context context, Intent intent) {
		if ("com.tea.autospeaker.UPDATE_SPEAKER_WIDGET".equals(intent.getAction()))
		{
			Log.d("AutoSpeakWidgetProvider", "onReceive com.tea.autospeaker.UPDATE_SPEAKER_WIDGET");
			AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
			ComponentName thisAppWidget = new ComponentName(context.getPackageName(), AutoSpeakWidgetProvider.class.getName());
			int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);

			onUpdate(context, appWidgetManager, appWidgetIds);
		}
		super.onReceive(context, intent);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		Log.d("AutoSpeakWidgetProvider", "onUpdate");
		// TODO Auto-generated method stub
		SharedPreferences sp = context.getSharedPreferences(MainActivity.PREF_FILE_NAME, Context.MODE_PRIVATE);
		boolean isOn = sp.getBoolean(MainActivity.PREF_ENABLE_AUTO_SPEAK_NAME, false);
		for (int i = 0; i < appWidgetIds.length; i++)
		{
			int appWidgetId = appWidgetIds[i];
			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_main);
			
			if (isOn)
				views.setImageViewResource(R.id.image, R.drawable.ic_launcher);
			else
				views.setImageViewResource(R.id.image, R.drawable.ic_launcher_off);
			
			Intent intent = new Intent(context, ChangeSettingService.class);
            PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);
            views.setOnClickPendingIntent(R.id.image, pendingIntent);
	        // Tell the AppWidgetManager to perform an update on the current app widget
	        appWidgetManager.updateAppWidget(appWidgetId, views);
		}
        
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}

}
