package com.android.criminalintent;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CrimeListFragment extends ListFragment {
	
	private ArrayList<Crime> mCrimes;
	private static final String TAG = "CrimeListFragment";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getActivity().setTitle(R.string.crimes_title);
		mCrimes = CrimeLab.get(getActivity()).getCrimes();
		
		CrimeAdapter adapter = new CrimeAdapter(mCrimes);
		
		/*ArrayAdapter <Crime> adapter = new ArrayAdapter<Crime>(getActivity(),
										android.R.layout.simple_list_item_1, mCrimes);*/
		
		setListAdapter(adapter);
		
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		//cast the result to crime
		//Crime c = (Crime)(getListAdapter()).getItem(position);
		//Since you are casting to CrimeAdapter, you get the benefits of type-checking. 
		//CrimeAdapter can only
		//hold Crime objects, so you no longer need to cast to Crime.
		Crime c = ((CrimeAdapter)getListAdapter()).getItem(position);
		Log.d(TAG, c.getmTitle() + " was clicked");
		Toast.makeText(v.getContext(), c.getmTitle() + " was clicked", Toast.LENGTH_SHORT).show();
	}
	
	private class CrimeAdapter extends ArrayAdapter<Crime> {
		
		public CrimeAdapter(ArrayList<Crime> crimes) {
			super(getActivity(), 0, crimes);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_crime, null);
			}
			
			
			Crime c = getItem(position);
			
			TextView titleTextView = (TextView)convertView.findViewById(R.id.crime_list_item_titleTextView);
			titleTextView.setText(c.getmTitle());
			TextView dateTextView = (TextView)convertView.findViewById(R.id.crime_list_item_dateTextView);
			dateTextView.setText(c.getmDate().toString());
			CheckBox solvedCheckBox = (CheckBox)convertView.findViewById(R.id.crime_list_item_solvedCheckBox);
			solvedCheckBox.setChecked(c.ismSolved());
			
			
			return convertView;
		}


		
		
	}

	
	

}
