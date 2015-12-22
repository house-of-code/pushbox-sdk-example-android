package io.houseofcode.pushboxsdktestapp;

import android.content.Intent;
import android.util.Log;

import io.houseofcode.pushboxsdk.PushBoxMessage;
import io.houseofcode.pushboxsdk.PushBoxSDKGcmIntentService;

/**
 * Created by gsl on 22/12/15.
 */
public class MyGcmIntentService extends PushBoxSDKGcmIntentService
{
	@Override
	protected void onHandleIntent(Intent intent)
	{
		super.onHandleIntent(intent);
		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}

	@Override
	protected void handlePushReceived(PushBoxMessage message)
	{
		Log.d("jojo", "Daz msg:" + message.getTitle());
	}
}