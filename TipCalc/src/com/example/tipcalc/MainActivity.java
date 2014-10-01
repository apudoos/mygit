package com.example.tipcalc;

import java.text.NumberFormat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment implements OnEditorActionListener, OnClickListener  {
		
		private EditText billAmountEditText;
		private TextView percentTextView;
		private Button percentUpButton;
		private Button percentDownButton;
		private TextView tipTextView;
		private TextView totalTextView;
		
		private float tipPercent = .15f;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			
			billAmountEditText = (EditText) rootView.findViewById(R.id.billAmountEditText);
			percentTextView = (TextView) rootView.findViewById(R.id.percentTextView);
			percentUpButton = (Button) rootView.findViewById(R.id.percentUpButton);
			percentDownButton = (Button) rootView.findViewById(R.id.percentDownButton);
			tipTextView = (TextView) rootView.findViewById(R.id.tipTextView);
			totalTextView = (TextView) rootView.findViewById(R.id.totalTextView);
			
			
			billAmountEditText.setOnEditorActionListener(this);
			
			percentUpButton.setOnClickListener(this);
			percentDownButton.setOnClickListener(this);		
			
			return rootView;
		}

		@Override
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			calculateAndDisplay();
			return false;
		}

		private void calculateAndDisplay() {
			String billAmountString = billAmountEditText.getText().toString();
			float billAmount;
			if (billAmountString.equals("")) {
				billAmount = 0;
			} else {
				billAmount = Float.parseFloat(billAmountString);
			}
			
			float tipAmount = billAmount * tipPercent;
			float totalAmount = billAmount + tipAmount;
			
			NumberFormat currency = NumberFormat.getCurrencyInstance();
			tipTextView.setText(currency.format(tipAmount));
			totalTextView.setText(currency.format(totalAmount));
			
			NumberFormat percent = NumberFormat.getPercentInstance();
			percentTextView.setText(percent.format(tipPercent));
			
		}

		@Override
		public void onClick(View v) {
			
			switch (v.getId()) {
			case R.id.percentDownButton:
				tipPercent = tipPercent - .01f;
				calculateAndDisplay();
				break;
				
			case R.id.percentUpButton:
				tipPercent = tipPercent + .01f;
				calculateAndDisplay();
				break;
			
			}
			
		}
	}

}
