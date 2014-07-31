package com.android.criminalintent;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;

public class CrimeListFragment extends ListFragment {
	
	private ArrayList<Crime> mCrimes;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getActivity().setTitle(R.string.crimes_title);
		mCrimes = CrimeLab.get(getActivity()).getCrimes();
		
		ArrayAdapter <Crime> adapter = new ArrayAdapter<Crime>(getActivity(),
										android.R.layout.simple_list_item_1, mCrimes);
		
		setListAdapter(adapter);
		
	}

	
	

}
