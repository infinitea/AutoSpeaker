package com.tea.autospeaker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class PhoneStateReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent arg1) {
		// TODO Auto-generated method stub

		Log.d("PhoneStateReceiver", "onReceive");
		Intent intent = new Intent();
		intent.setClassName("com.tea.autospeaker", "com.tea.autospeaker.AutoSpeakService");
		context.startService(intent);
	}

}
