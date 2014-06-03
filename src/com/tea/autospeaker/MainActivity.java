package com.tea.autospeaker;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class MainActivity extends Activity {

	public final static String PREF_FILE_NAME = "auto_speaker_setting";
	public final static String PREF_ENABLE_AUTO_SPEAK_NAME = "enable_auto_speaker";
	private CheckBox mCheckBox;
    private SharedPreferences mSharedPreference;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCheckBox = (CheckBox)findViewById(R.id.checkBox1);
        mSharedPreference = getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        
        mCheckBox.setChecked(mSharedPreference.getBoolean(PREF_ENABLE_AUTO_SPEAK_NAME, false));
        mCheckBox.setOnCheckedChangeListener(mCheckListener);
    }
    
    private OnCheckedChangeListener mCheckListener = new OnCheckedChangeListener()
    {
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			mSharedPreference.edit().putBoolean(PREF_ENABLE_AUTO_SPEAK_NAME, isChecked).commit();
			sendBroadcast(new Intent("com.tea.autospeaker.UPDATE_SPEAKER_WIDGET"));
		}
    };
}
