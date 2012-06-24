package com.moneymanager.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.Action;
import com.markupartist.android.widget.ActionBar.IntentAction;
import com.moneymanager.entity.Account;
import com.moneymanager.entity.con.DBAdapter;

public class NewAccountAddView extends Activity {
	private Button btAdd;
	private EditText etAccName;
	private EditText etAddAmount;
	private DBAdapter dbHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		dbHelper=new DBAdapter(getApplicationContext());
		
		setContentView(R.layout.account_view_add);
		
		final ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
		// actionBar.setHomeAction(new IntentAction(this, createIntent(this),
		// R.drawable.ic_title_home_demo));
		actionBar.setTitle("Add New Account");
		final Action homeAction = new IntentAction(this, getHomeIntent(), R.drawable.ic_title_home_default);
		actionBar.setHomeAction(homeAction);
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		
		btAdd=(Button) findViewById(R.id.account_view_bt_add);
		etAccName=(EditText) findViewById(R.id.account_add_et_accname);
		etAddAmount=(EditText) findViewById(R.id.account_add_et_initbalance);
		
		btAdd.setOnClickListener(btAddAccountAction);
		
	}

	private Intent getHomeIntent() {
		return new Intent(this,
				AccountsList.class);
	}
	
	private View.OnClickListener btAddAccountAction=new View.OnClickListener() {
		
		public void onClick(View v) {
			
			DBAdapter dbHelper=new DBAdapter(v.getContext());
			dbHelper.open();
			Account account=new Account();
			
			account.setName(etAccName.getEditableText().toString());
			account.setInit_value(Integer.parseInt(etAddAmount.getEditableText().toString()));				
			account.setCurr_value(account.getInit_value());
			dbHelper.inserAccount(account);
			dbHelper.close();
			
			Intent intent=getHomeIntent();
			NewAccountAddView.this.startActivity(intent);
			NewAccountAddView.this.finish();
	
		}
	};
}
