package io.houseofcode.pushboxsdktestapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import io.houseofcode.pushboxsdk.PushBoxSDK;

public class MessageActivity extends AppCompatActivity
{

	String message = "";
	String title;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Bundle args = getIntent().getExtras();
		if (args != null)
		{
			title = args.getString("title");
			message = args.getString("message", "");
		}
		setTitle(title);
		setContentView(R.layout.activity_message);
		TextView messageView = (TextView)findViewById(R.id.message);
		messageView.setText(message);
	}
}
