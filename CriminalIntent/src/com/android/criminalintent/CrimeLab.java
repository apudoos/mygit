package com.android.criminalintent;

import java.util.ArrayList;
import java.util.UUID;

import android.content.Context;

public class CrimeLab {
	private ArrayList<Crime> mCrimes;
	
	private static CrimeLab sCrimeLab;
	private Context mAppContext;
	
	private CrimeLab(Context appContext) {
		mAppContext = appContext;
		mCrimes = new ArrayList<Crime>();
		for (int i=0; i<100; i++) {
			Crime c = new Crime();
			c.setmTitle("Crime #" + i);
			c.setmSolved(i % 2 == 0);
			mCrimes.add(c);
		}
		
		
	}
	
	//Since we are creating only one instance 
	//of the class CrimeLab.
	public static CrimeLab get(Context c) {
		if (sCrimeLab == null) {
			sCrimeLab = new CrimeLab(c.getApplicationContext());
		}		
		return sCrimeLab;
	}
	
	public ArrayList<Crime> getCrimes() {
		return mCrimes;
	}
	
	public Crime getCrime(UUID id) {
		for(Crime c: mCrimes) {
			if(c.getmId().equals(id)) {
				return c;
			}
		}
		return null;
		
	}

}
