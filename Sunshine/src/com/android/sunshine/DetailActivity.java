package com.android.sunshine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new DetailFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detail, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class DetailFragment extends Fragment {
		
		private static final String LOG_TAG = DetailFragment.class.getName();
		private static final String FORECAST_SHARE_HASHTAG = " #SunshineApp";
		private String forecastStr;
		//private ShareActionProvider mShareActionProvider;
		

		public DetailFragment() {
			setHasOptionsMenu(true);
		}

		@Override
		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
			
			inflater.inflate(R.menu.forecastfragment, menu);
			
			MenuItem menuItem = menu.findItem(R.id.action_refresh);
			
			ShareActionProvider mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
			
			// TODO Test this one
			//mShareActionProvider = (ShareActionProvider) menuItem.getActionProvider();
			
			if (mShareActionProvider != null) {
				mShareActionProvider.setShareIntent(createShareForecastIntent());
			} else {
				Log.d(LOG_TAG, "Share action is null");
			}
			
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			Intent intent=getActivity().getIntent();
			View rootView = inflater.inflate(R.layout.fragment_detail,
					container, false);
			if ((intent != null) && intent.hasExtra(Intent.EXTRA_TEXT)) {
				forecastStr = intent.getStringExtra(Intent.EXTRA_TEXT);
				
				
				TextView textview = (TextView) rootView.findViewById(R.id.detail_text);
				textview.setText(forecastStr);
				
				//Another way to do this				
				//((TextView) rootView.findViewById(R.id.detail_text)).setText(forecastStr);
				
				
			}
			return rootView;
		}
		
		private Intent createShareForecastIntent() {
			Intent shareIntent = new Intent(Intent.ACTION_SEND);
			shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
			shareIntent.setType("text/plain");
			shareIntent.putExtra(Intent.EXTRA_TEXT, forecastStr + FORECAST_SHARE_HASHTAG);
			
			return shareIntent;
		}
		
		// TODO test this one
		// Call to update the share intent
/*		private void setShareIntent(Intent shareIntent) {
		    if (mShareActionProvider != null) {
		        mShareActionProvider.setShareIntent(shareIntent);
		    }
		}*/
		
		
		
	}

}
