package com.first.listbox;

import java.util.ArrayList;
import java.util.List;

import com.first.listbox.ListData.Status;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ListAdaptor extends BaseAdapter{
	
	private List<ListData> mItems = getDataForListView();
	
	private Context mContext = null;
	
	public ListAdaptor (Context context) {
		mContext = context;
	}

	@Override
	public int getCount() {
		
		return mItems.size();

	}

	@Override
	public Object getItem(int pos) {
		// TODO Auto-generated method stub
		return mItems.get(pos);
	}

	@Override
	public long getItemId(int pos) {
		// TODO Auto-generated method stub
		return pos;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		
		ListData listData = mItems.get(arg0);
		
		RelativeLayout itemLayout = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.id.RelativeLayout1, null);
		
		final TextView titleView = (TextView) itemLayout.findViewById(R.id.titleView);
		titleView.setText(listData.getTitle());
		
		// TODO - Set up Status CheckBox
	
		final CheckBox statusView = (CheckBox) itemLayout.findViewById(R.id.statusCheckBox);
		
		if (listData.getStatus() == Status.DONE) {
			statusView.setChecked(true);
			statusView.setText("Done");
		} else {
			statusView.setChecked(false);
			statusView.setText("Not Done");
		}
		
		
		
		statusView.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				
				// TODO - Set up and implement an OnCheckedChangeListener, which 
				// is called when the user toggles the status checkbox
				if (statusView.isChecked()) {
					statusView.setText("Not Done");
				} else {
					statusView.setText("Done");
				}


			
			}
		});

		
		
		return itemLayout;
	}
	
    public List<ListData> getDataForListView()
    {
        List<ListData> compListData = new ArrayList<ListData>();

        for(int i=0;i<10;i++)
        {
        	ListData listData;
        	
        	if (i/2 == 0) {
        		listData = new ListData("Title" + i, Status.NOTDONE);
        	} else {
        		listData = new ListData("Title" + i, Status.DONE);
        	}
        	compListData.add(listData);
        }

        return compListData;

    }

	
}
