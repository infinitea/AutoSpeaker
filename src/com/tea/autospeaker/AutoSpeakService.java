package com.tea.autospeaker;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class AutoSpeakService extends Service {

	private TelephonyManager mTelMgr = null;
	private AudioManager mAudioMgr = null;
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		  mTelMgr = null;
		  mAudioMgr = null;
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		SharedPreferences sp = getSharedPreferences(MainActivity.PREF_FILE_NAME, Context.MODE_PRIVATE);

		Log.d("AutoSpeakService", "onStartCommand");
		if (!sp.getBoolean(MainActivity.PREF_ENABLE_AUTO_SPEAK_NAME, false))
			stopSelf();
		else
		{
			Log.d("AutoSpeakService", "true");
			if (mTelMgr == null)
			{
				mTelMgr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
				mTelMgr.listen(mListener, PhoneStateListener.LISTEN_CALL_STATE);
				mAudioMgr = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
				Notification ntf = new Notification();
				startForeground(AutoSpeakService.class.getSimpleName().hashCode(), ntf);
			}
		}
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private PhoneStateListener mListener = new PhoneStateListener()
	{
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			  switch(state)
			  {
			  case TelephonyManager.CALL_STATE_IDLE:
				Log.d("AutoSpeakService", "CALL_STATE_IDLE");
				  if (mAudioMgr != null && mAudioMgr.isSpeakerphoneOn())
				  {
					  mAudioMgr.setSpeakerphoneOn(false);
					  mAudioMgr.setMode(AudioManager.MODE_NORMAL);
				  }
				mTelMgr.listen(mListener, PhoneStateListener.LISTEN_NONE);
				stopSelf();
				  break;
			  case TelephonyManager.CALL_STATE_OFFHOOK:
				  Log.d("AutoSpeakService", "CALL_STATE_OFFHOOK");
				  if (mAudioMgr != null)
				  {
					mAudioMgr.setMode(AudioManager.MODE_IN_CALL);
					if (!mAudioMgr.isSpeakerphoneOn())
						mAudioMgr.setSpeakerphoneOn(true);
					mAudioMgr.setMode(AudioManager.MODE_NORMAL);
				  }
				  break;
			  }
		}
	};
}
