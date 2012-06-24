package com.moneymanager.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;


import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.Action;
import com.markupartist.android.widget.ActionBar.IntentAction;
import com.moneymanager.entity.Transaction;
import com.moneymanager.entity.con.DBAdapter;
import com.moneymanager.model.ExpandableListAdapter_Traction;

public class TransactionList extends Activity {

	private DBAdapter dbAdapter;
	private ExpandableListAdapter_Traction adapter;
	private ExpandableListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.transaction_listview);
		
        final ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
        //actionBar.setHomeAction(new IntentAction(this, createIntent(this), R.drawable.ic_title_home_demo));
        actionBar.setTitle("Transactions");
        final Action homeAction = new IntentAction(this, new Intent(this, MoneyManagerActivity.class), R.drawable.ic_title_home_default);
        actionBar.setHomeAction(homeAction);
        actionBar.setDisplayHomeAsUpEnabled(true);
        
        final Action shareAction = new IntentAction(this, new Intent(this, ReportView.class), R.drawable.ic_action_report);
        actionBar.addAction(shareAction);
        final Action otherAction = new IntentAction(this, new Intent(this, AccountsList.class), R.drawable.ic_action_account);
        actionBar.addAction(otherAction);
        

		listView = (ExpandableListView) findViewById(R.id.transaction_listView);
		
		listView.setOnChildClickListener(new OnChildClickListener() {

			public boolean onChildClick(ExpandableListView parent,
					View convertView, int groupPosition, int childPosition,
					long arg4) {

				Transaction transaction = (Transaction) adapter.getChild(groupPosition,
						childPosition);

				Intent intentNext = new Intent(
						TransactionList.this,
						TransactionAddEdit.class);

				intentNext.putExtra("EDITTR", transaction);

				startActivity(intentNext);

				return false;
			}
		});
        
        
        

		dbAdapter = new DBAdapter(getApplicationContext());
		updateList();
		
		
		

	}

	private void updateList() {
		dbAdapter.open();
		List<Transaction> transactions = dbAdapter.getAllTransactions();
		dbAdapter.close();

		Log.i("TRTR", "open");


		// Initialize the adapter with blank groups and children
		// We will be adding children on a thread, and then update the ListView
		adapter = new ExpandableListAdapter_Traction(this, new ArrayList<String>(),
				new ArrayList<ArrayList<Transaction>>());

		// Set this blank adapter to the list view
		listView.setAdapter(adapter);

		for (Transaction transaction : transactions) {
			adapter.addItem(transaction);
		}
		
		listView.setOnChildClickListener(new OnChildClickListener() {

			
			public boolean onChildClick(ExpandableListView parent,
					View convertView, int groupPosition, int childPosition,
					long arg4) {

				Transaction transaction = (Transaction) adapter.getChild(groupPosition,
						childPosition);

				Intent intentNext = new Intent(
						TransactionList.this,
						TransactionAddEdit.class);

				intentNext.putExtra("EDITTR", transaction);

				startActivity(intentNext);

				return false;
			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.accountlist_menu, menu);
		MenuItem mi = menu.findItem(R.id.account_opm_addnew);
		mi.setTitle("Add Transaction");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection

		switch (item.getItemId()) {
		case R.id.account_opm_addnew: {

			Intent intent = new Intent(TransactionList.this,
					TransactionAddEdit.class);
			TransactionList.this.startActivity(intent);

			return true;
		}
		case R.id.account_opm_update: {

			updateList();

			return true;
		}
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
