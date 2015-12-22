package io.houseofcode.pushboxsdktestapp;

import android.app.Application;

import io.houseofcode.pushboxsdk.PushBoxSDK;

/**
 * Created by gsl on 22/12/15.
 */
public class PushBoxSDKTestApp extends Application
{
	@Override
	public void onCreate()
	{
		super.onCreate();
		PushBoxSDK.setContextAndSenderIdAndKeys(this, BuildConfig.GCM_SENDER_ID, BuildConfig.API_KEY, BuildConfig.API_SECRET);

		PushBoxSDK.getInstance().logEvent("Started App");
	}
}
