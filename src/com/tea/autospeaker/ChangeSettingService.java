package com.tea.autospeaker;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class ChangeSettingService extends IntentService {

	public ChangeSettingService() {
		super("ChangeSettingService");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent arg0) {
		Log.d("ChangeSettingService", "onHandleIntent");
		SharedPreferences sp = getSharedPreferences(MainActivity.PREF_FILE_NAME, Context.MODE_PRIVATE);
		sp.edit().putBoolean(MainActivity.PREF_ENABLE_AUTO_SPEAK_NAME, !sp.getBoolean(MainActivity.PREF_ENABLE_AUTO_SPEAK_NAME, false)).apply();
		sendBroadcast(new Intent("com.tea.autospeaker.UPDATE_SPEAKER_WIDGET"));
	}
}
