package com.example.tipcalc;

import java.text.NumberFormat;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
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
		private TextView perPersonLabelView;
		private TextView perPersonAmtView;
		private Spinner spinner;
		private RadioButton noneRadio;
		private RadioButton tipRadio;
		private RadioButton totalRadio;
		
		private float tipPercent = .15f;
		private SharedPreferences savedValues;
		private String billAmountString;
		private Button applyButton;
		private boolean applyButtonClicked = false;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			
			getActivity();
			savedValues = getActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
			
			billAmountEditText = (EditText) rootView.findViewById(R.id.billAmountEditText);
			percentTextView = (TextView) rootView.findViewById(R.id.percentTextView);
			percentUpButton = (Button) rootView.findViewById(R.id.percentUpButton);
			percentDownButton = (Button) rootView.findViewById(R.id.percentDownButton);
			tipTextView = (TextView) rootView.findViewById(R.id.tipTextView);
			totalTextView = (TextView) rootView.findViewById(R.id.totalTextView);
			noneRadio = (RadioButton) rootView.findViewById(R.id.radio0);
			tipRadio = (RadioButton) rootView.findViewById(R.id.radio1);
			totalRadio = (RadioButton) rootView.findViewById(R.id.radio2);
			applyButton = (Button) rootView.findViewById(R.id.button1);
			perPersonLabelView = (TextView) rootView.findViewById(R.id.perPersonLabel);
			perPersonAmtView = (TextView) rootView.findViewById(R.id.perPersonText);
			
			spinner = (Spinner) rootView.findViewById(R.id.spinner1);
			ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.splittip, android.R.layout.simple_spinner_item);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner.setAdapter(adapter);
			
			billAmountEditText.setOnEditorActionListener(this);
			
			percentUpButton.setOnClickListener(this);
			percentDownButton.setOnClickListener(this);		
			applyButton.setOnClickListener(this);
			
			return rootView;
		}

		@Override
		public void onPause() {
			Editor editor = savedValues.edit();
			editor.putString("billAmountString", billAmountString);
			editor.putFloat("tipPercent", tipPercent);
			editor.commit();
			
			super.onPause();
		}

		@Override
		public void onResume() {
			
			billAmountString = savedValues.getString("billAmountString", "");
			tipPercent = savedValues.getFloat("tipPercent", 0.15f);
			super.onResume();
		}

		@Override
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			if (actionId == EditorInfo.IME_ACTION_DONE) {
				calculateAndDisplay();
			}
			
			
			return false;
		}

		private void calculateAndDisplay() {
			billAmountString = billAmountEditText.getText().toString();
			float billAmount;
			if (billAmountString.equals("")) {
				billAmount = 0;
			} else {
				billAmount = Float.parseFloat(billAmountString);
			}
			
			float tipAmount = billAmount * tipPercent;
			float totalAmount = billAmount + tipAmount;
			
			if (applyButtonClicked) {
				if (tipRadio.isChecked()) {
					tipAmount = StrictMath.round(billAmount * tipPercent);
					totalAmount = billAmount + tipAmount;
					tipPercent = tipAmount / billAmount;
				
				} else if (totalRadio.isChecked()) {
				
					float tipNotRounded = billAmount * tipPercent;
					totalAmount = StrictMath.round(billAmount + tipNotRounded);
					tipAmount = totalAmount - billAmount;
					tipPercent = tipAmount / billAmount;
				}
			}
			
			NumberFormat currency = NumberFormat.getCurrencyInstance();
			tipTextView.setText(currency.format(tipAmount));
			totalTextView.setText(currency.format(totalAmount));
			
			NumberFormat percent = NumberFormat.getPercentInstance();
			percentTextView.setText(percent.format(tipPercent));
			
			
			int position = spinner.getSelectedItemPosition();
			
			if (position == 0) {
				perPersonLabelView.setVisibility(View.GONE);
				perPersonAmtView.setVisibility(View.GONE);
			} else {
				
				Float perperson = totalAmount/(position + 1);
				perPersonLabelView.setVisibility(View.VISIBLE);
				perPersonAmtView.setVisibility(View.VISIBLE);
				perPersonAmtView.setText(currency.format(perperson));

				
			}
			
			
			
		}

		@Override
		public void onClick(View v) {
			
			switch (v.getId()) {
			case R.id.percentDownButton:
				applyButtonClicked = false;
				tipPercent = tipPercent - .01f;
				calculateAndDisplay();
				noneRadio.setChecked(true);
				
				break;
				
			case R.id.percentUpButton:
				applyButtonClicked = false;
				tipPercent = tipPercent + .01f;
				calculateAndDisplay();
				noneRadio.setChecked(true);
				break;
				
			case R.id.button1:
				applyButtonClicked = true;
				calculateAndDisplay();
				break;

			
			}
			
		}
	}

}
