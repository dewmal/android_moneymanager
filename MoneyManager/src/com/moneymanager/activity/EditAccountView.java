package com.moneymanager.activity;

import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.Action;
import com.markupartist.android.widget.ActionBar.IntentAction;
import com.moneymanager.entity.Account;
import com.moneymanager.entity.con.DBAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditAccountView extends Activity {
	private Button btAdd;
	private EditText etAccName;
	private EditText etAddAmount;
	private DBAdapter dbHelper;
	
	
	//
	private Account account;
	private EditText etCurValue;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		account=(Account) getIntent().getExtras().getSerializable("EDITAC");
		
		Log.i("ACC", account.toString());
		
		dbHelper=new DBAdapter(getApplicationContext());
		
		setContentView(R.layout.account_view_edit);
		
		final ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
		// actionBar.setHomeAction(new IntentAction(this, createIntent(this),
		// R.drawable.ic_title_home_demo));
		actionBar.setTitle("Edit Account");
		final Action homeAction = new IntentAction(this, getHomeIntent(), R.drawable.ic_title_home_default);
		actionBar.setHomeAction(homeAction);
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		
		btAdd=(Button) findViewById(R.id.account_edit_bt_add);
		btAdd.setText("Update");
		etAccName=(EditText) findViewById(R.id.account_edit_et_accname);
		etCurValue=(EditText)findViewById(R.id.account_edit_et_currbalance);
		etCurValue.setEnabled(false);
		etAddAmount=(EditText) findViewById(R.id.account_edi_et_addamount);
		
		
		etAccName.setText(account.getName());
		etCurValue.setText(Double.toString(account.getCurr_value())+"");
		
		
		
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
			account.setName(etAccName.getEditableText().toString());
			
			double parseDouble = Double.parseDouble(etAddAmount.getEditableText().toString().trim());
			Log.i("AMOUNT", parseDouble+"");
			double curr_value = account.getCurr_value()+parseDouble;
			account.setCurr_value(curr_value);
			dbHelper.updateAccount(account);
			dbHelper.close();
			
			Intent homeIntent=getHomeIntent();
			EditAccountView.this.startActivity(homeIntent);
			
			EditAccountView.this.finish();
	
		}
	};
}