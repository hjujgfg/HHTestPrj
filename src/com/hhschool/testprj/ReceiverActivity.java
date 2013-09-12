package com.hhschool.testprj;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.method.MovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ReceiverActivity extends Activity {

	public ReceiverActivity() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.scalable_receiver);
		Intent t = getIntent();
		receivedReq = (Request)t.getSerializableExtra("request");
		nameView = (TextView)findViewById(R.id.rec_name);
		dateView = (TextView)findViewById(R.id.rec_birth);
		genderView = (ImageView)findViewById(R.id.imageView2);
		posView  = (TextView)findViewById(R.id.rec_pos);
		salaryView  = (TextView)findViewById(R.id.rec_sal);
		phoneView  = (TextView)findViewById(R.id.rec_phone);
		emailView  = (TextView)findViewById(R.id.rec_email);
		resp  = (EditText)findViewById(R.id.rec_res);
		response = (Button)findViewById(R.id.rec_send);
		resp.setMovementMethod(new ScrollingMovementMethod());
		try {
			nameView.setText(receivedReq.Name());
			dateView.setText(receivedReq.getBirthString());
			if (receivedReq.getGender() == true) {
				genderView.setImageResource(R.drawable.male);
			} else {
				genderView.setImageResource(R.drawable.female);
			}
			posView.setText(receivedReq.getPosition());
			salaryView.setText(receivedReq.getSalary()+"");
			phoneView.setText(receivedReq.getPhone());
			emailView.setText(receivedReq.getEmail());
		} catch (Exception e ) {
			new AlertDialog.Builder(this)
			.setTitle("Ошибка")
			.setMessage("Заявка не передана")
			.setPositiveButton("Создать ещё раз", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					setResult(RESULT_CANCELED);
					finish();
				}
			})
			.show();
		}
		response.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.putExtra("response", resp.getText().toString());
				setResult(RESULT_OK, i);
				finish();
			}
		});
	}
	final private Context context = this;
	Request receivedReq; 
	TextView nameView;
	TextView dateView;
	ImageView genderView;
	TextView posView;
	TextView salaryView;
	TextView phoneView;
	TextView emailView;	
	EditText resp;
	Button response; 
}
