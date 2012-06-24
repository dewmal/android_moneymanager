package com.moneymanager.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginView extends Activity {

	private Button btLogin;
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_view);
		
		btLogin=(Button) findViewById(R.id.loginview_bt_login);
		btLogin.setOnClickListener(new  View.OnClickListener() {
			
			public void onClick(View arg0) {
				Intent intent=new Intent(LoginView.this, MoneyManagerActivity.class);
				LoginView.this.startActivity(intent);
				
			}
		});
	}

}
