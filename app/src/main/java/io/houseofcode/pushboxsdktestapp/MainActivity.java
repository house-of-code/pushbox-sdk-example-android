package io.houseofcode.pushboxsdktestapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import io.houseofcode.pushboxsdk.PushBoxMessage;
import io.houseofcode.pushboxsdk.PushBoxSDK;

public class MainActivity extends AppCompatActivity
{

	private List<PushBoxMessage> messages;
	private ListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		listView = (ListView) findViewById(R.id.listview);
		listView.setVisibility(View.GONE);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				PushBoxMessage message = messages.get(position);

				if (message != null)
				{
					Intent i = new Intent(MainActivity.this, MessageActivity.class);
					i.putExtra("title", message.getTitle());
					if (message.getMessage() != null)
					{
						i.putExtra("message", message.getMessage());
					}
					startActivity(i);
					PushBoxSDK.getInstance().setMessageRead(message);
				}
			}
		});
		FloatingActionButton reload = (FloatingActionButton) findViewById(R.id.reload);
		reload.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Snackbar.make(v, "Reloads inbox", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();
				loadData();
			}
		});
		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Snackbar.make(view, "Logs event: 'TEST EVENT'", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();
				PushBoxSDK.getInstance().logEvent("TEST EVENT");
			}
		});

	}

	@Override
	protected void onResume()
	{
		super.onResume();
		loadData();
	}

	private void loadData()
	{
		listView.setVisibility(View.GONE);
		PushBoxSDK.getInstance().getInbox(new PushBoxSDK.PushboxInboxCallback()
		{
			@Override
			public void onSuccess(List<PushBoxMessage> msgs)
			{
				MyAdapter adapter = new MyAdapter();
				messages = msgs;
				listView.setAdapter(adapter);
				listView.setVisibility(View.VISIBLE);
			}

			@Override
			public void onFailure(String error)
			{
				listView.setVisibility(View.GONE);
			}
		});
	}
	private class MyAdapter extends BaseAdapter
	{
		class ViewHolder {
			TextView mName;

			public ViewHolder(View view) {
				mName = (TextView) view.findViewById(R.id.text);
				view.setTag(this);
			}
		}


		public MyAdapter()
		{
			super();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			PushBoxMessage message = messages.get(position);
			String title = message.getTitle();
			if (title == null)
			{
				title = "";
			}
			if (convertView == null)
			{
				convertView = View.inflate(parent.getContext(), R.layout.item, null);
				new ViewHolder(convertView);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();

			holder.mName.setText(title);

			return convertView;
		}

		@Override
		public int getCount()
		{
			if (messages == null)
			{
				return 0;
			}
			return messages.size();
		}


		@Override
		public Object getItem(int position)
		{
			if (messages == null)
			{
				return null;
			}
			return messages.get(position);
		}

		@Override
		public long getItemId(int position)
		{
			if (messages == null)
			{
				return 0;
			}
			return messages.get(position).getId();
		}
	}
}
