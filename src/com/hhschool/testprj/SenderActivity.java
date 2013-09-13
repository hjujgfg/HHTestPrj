package com.hhschool.testprj;

import java.util.Calendar;
import java.util.Date;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SenderActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scalable_sender);
		nameText = (EditText)findViewById(R.id.initials);
		genderSpinner = (Spinner)findViewById(R.id.spinner);
		positionText = (EditText)findViewById(R.id.position);
		salaryText = (EditText)findViewById(R.id.salary);
		phoneText = (EditText)findViewById(R.id.phone);
		phoneText.setFilters(new InputFilter[] {new InputFilter() {
			
			@Override
			public CharSequence filter(CharSequence source, int start, int end,
					Spanned dest, int dstart, int dend) {
				// TODO Auto-generated method stub
				String str = source.toString();
				SpannableStringBuilder sb = new SpannableStringBuilder();
				for (int i = 0; i < str.length(); i ++) {
					if ((str.charAt(i) >= '0' && str.charAt(i) <= '9')) {
						sb.append(str.charAt(i));
					}
					if (str.charAt(i) == '+' && i == 0) {
						sb.append(str.charAt(i));
					}
				}						
				return sb;
			}
		}});
		emailText = (EditText)findViewById(R.id.email);
		dateView = (TextView)findViewById(R.id.birth);
		clock = (ImageView)findViewById(R.id.imageView1);
		sendButton = (Button)findViewById(R.id.button1);
		clock.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDatePicker();
			}
		});
		clock.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (hasFocus){
					showDatePicker();
				}
			}
		});
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender,
				android.R.layout.simple_spinner_dropdown_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		genderSpinner.setAdapter(adapter);
		sendButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				boolean correct = true; 
				if (nameText.getText().toString().isEmpty()) {
					correct = false;
					nameText.requestFocus();					
				}
				if (positionText.getText().toString().isEmpty()) {
					correct = false;
					positionText.requestFocus();					
				}
				if (salaryText.getText().toString().isEmpty()) {
					correct = false;
					salaryText.requestFocus();
				}
				if (phoneText.getText().toString().isEmpty()) {
					correct = false;
					phoneText.requestFocus();
				}
				if (emailText.getText().toString().isEmpty()) {
					correct = false; 
					emailText.requestFocus();
				}
				if (dateView.getText().toString().isEmpty()) {
					correct = false;
					showDatePicker();				
				}
				//one of the fields is empty
				if (!correct) {
					return; 
				}
				//everything is filled
				updateModel();
				Intent i = new Intent(context, ReceiverActivity.class);				
				i.putExtra("request", currentReq);
				startActivityForResult(i, 1);				
			}
		});
		currentReq = new Request();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_sender, menu);
		return true;
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data == null) return;
		String result = data.getStringExtra("response");
		if (requestCode == 1){
			if (resultCode == RESULT_OK) {			
				new AlertDialog.Builder(context)
					.setTitle("Ответ работодателя")
					.setMessage(result)
					.show();							
			} 
		}
	}
	EditText nameText;
	Spinner genderSpinner;
	EditText positionText; 
	EditText salaryText; 
	EditText phoneText; 
	EditText emailText; 
	Button sendButton; 
	TextView dateView;
	ImageView clock;
	Request currentReq; 
	final Context context = this; 
	private void updateModel() {
		currentReq.setName(nameText.getText().toString());
		if (genderSpinner.getSelectedItemPosition() == 0) {
			currentReq.IsMale(true);
		} else {
			currentReq.IsMale(false);
		}
		currentReq.setPosition(positionText.getText().toString());
		currentReq.setSalary(Integer.parseInt(salaryText.getText().toString()));
		currentReq.setPhone(phoneText.getText().toString());
		currentReq.setEmail(emailText.getText().toString());
	}
	private void showDatePicker() {
		final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.date_picker_dialog);
		dialog.setTitle("Дата рождения");
		final DatePicker dp = (DatePicker)dialog.findViewById(R.id.datePicker1);		
		Calendar c = currentReq.getDate();
		dp.init(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), null);
		dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
			
			@Override
			public void onDismiss(DialogInterface dialog) {
				// TODO Auto-generated method stub						
				final Calendar cc = Calendar.getInstance();
				cc.set(dp.getYear(), dp.getMonth(), dp.getDayOfMonth());						
				
				Handler h = new Handler();
				h.post(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						currentReq.setDate(cc);
						dateView.setText(currentReq.getBirthString());
					}
				});											
			}
		});
		dialog.show();
	}
}
