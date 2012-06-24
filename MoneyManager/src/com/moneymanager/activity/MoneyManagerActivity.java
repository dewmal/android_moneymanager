package com.moneymanager.activity;

import com.moneymanager.report.model.TransactionByDate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;

public class MoneyManagerActivity extends Activity {
	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Button btTransaction = (Button) findViewById(R.id.dahsboard_bt_transaction);
		btTransaction.setOnClickListener(trasactionView);
		Button btReport = (Button) findViewById(R.id.dahsboard_bt_reports);
		btReport.setOnClickListener(reportsview);
		Button btAccounts = (Button) findViewById(R.id.dahsboard_bt_accounts);
		btAccounts.setOnClickListener(accountview);

	}

	private View.OnClickListener trasactionView = new View.OnClickListener() {

		public void onClick(View arg0) {
			Intent intent = new Intent(MoneyManagerActivity.this,
					TransactionList.class);
			startActivity(intent);
		}
	};

	private View.OnClickListener reportsview = new View.OnClickListener() {

		public void onClick(View arg0) {
			
			
			Intent intent =new Intent(MoneyManagerActivity.this, ReportView.class);
			startActivity(intent);
			
			

		}
	};

	private View.OnClickListener accountview = new View.OnClickListener() {

		public void onClick(View arg0) {
			Intent intent = new Intent(MoneyManagerActivity.this,
					AccountsList.class);
			startActivity(intent);

		}
	};

}