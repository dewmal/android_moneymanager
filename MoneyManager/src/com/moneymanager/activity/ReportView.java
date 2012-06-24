package com.moneymanager.activity;

import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.Action;
import com.markupartist.android.widget.ActionBar.IntentAction;
import com.moneymanager.report.model.ACCCatagoryPieChart;
import com.moneymanager.report.model.TrCatagoryPieChart;
import com.moneymanager.report.model.TransactionByDate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ReportView extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.report_view);

		final ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
		// actionBar.setHomeAction(new IntentAction(this, createIntent(this),
		// R.drawable.ic_title_home_demo));
		actionBar.setTitle("Reports");
		final Action homeAction = new IntentAction(this, new Intent(this,
				MoneyManagerActivity.class), R.drawable.ic_title_home_default);
		actionBar.setHomeAction(homeAction);
		actionBar.setDisplayHomeAsUpEnabled(true);

		final Action shareAction = new IntentAction(this, new Intent(this,
				AccountsList.class), R.drawable.ic_action_account);
		actionBar.addAction(shareAction);
		final Action otherAction = new IntentAction(this, new Intent(this,
				TransactionList.class), R.drawable.ic_action_transaction);
		actionBar.addAction(otherAction);
		
		Button btTrByDate=(Button) findViewById(R.id.report_view_bt_bymonth);
		btTrByDate.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				TransactionByDate byDate=new TransactionByDate();
				Intent intent=byDate.execute(ReportView.this);
				ReportView.this.startActivity(intent);
				
			}
		});

		
		Button btTrByAcc=(Button) findViewById(R.id.report_view_bt_byacc);
		btTrByAcc.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				ACCCatagoryPieChart byDate=new ACCCatagoryPieChart();
				Intent intent=byDate.execute(ReportView.this);
				ReportView.this.startActivity(intent);
				
			}
		});
		
		Button btTrByType=(Button) findViewById(R.id.report_view_bt_bycat);
		btTrByType.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				TrCatagoryPieChart byDate=new TrCatagoryPieChart();
				Intent intent=byDate.execute(ReportView.this);
				ReportView.this.startActivity(intent);
				
			}
		});
	}
}
