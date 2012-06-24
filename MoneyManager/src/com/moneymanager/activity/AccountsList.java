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
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.Action;
import com.markupartist.android.widget.ActionBar.IntentAction;
import com.moneymanager.entity.Account;
import com.moneymanager.entity.Transaction;
import com.moneymanager.entity.con.DBAdapter;
import com.moneymanager.model.ExpandableListAdapter_Account;

public class AccountsList extends Activity {

	private DBAdapter dbAdapter;
	private ExpandableListAdapter_Account listAdapter_Account;
	private ExpandableListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_listview);

		final ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
		// actionBar.setHomeAction(new IntentAction(this, createIntent(this),
		// R.drawable.ic_title_home_demo));
		actionBar.setTitle("Accounts");
		final Action homeAction = new IntentAction(this, new Intent(this,
				MoneyManagerActivity.class), R.drawable.ic_title_home_default);
		actionBar.setHomeAction(homeAction);
		actionBar.setDisplayHomeAsUpEnabled(true);

		final Action shareAction = new IntentAction(this, new Intent(this,
				ReportView.class), R.drawable.ic_action_report);
		actionBar.addAction(shareAction);
		final Action otherAction = new IntentAction(this, new Intent(this,
				TransactionList.class), R.drawable.ic_action_transaction);
		actionBar.addAction(otherAction);

		listView = (ExpandableListView) findViewById(R.id.account_listView);
		
		listView.setOnChildClickListener(new OnChildClickListener() {

			public boolean onChildClick(ExpandableListView parent,
					View convertView, int groupPosition, int childPosition,
					long arg4) {

				Account account = (Account) listAdapter_Account.getChild(groupPosition,
						childPosition);

				Intent intentNext = new Intent(
						AccountsList.this,
						EditAccountView.class);

				intentNext.putExtra("EDITAC", account);

				startActivity(intentNext);

				return false;
			}
		});

		dbAdapter = new DBAdapter(this);

		updateList();

		// setListAdapter(new ArrayAdapter<T>(context, textViewResourceId));
	}

	private void updateList() {
		dbAdapter.open();
		List<Account> accounts = dbAdapter.getAllAccounts();
		dbAdapter.close();

		for (Account account : accounts) {
			Log.i("Accounts", account.toString());
		}

		Log.i("TRTR", "open");

		// Initialize the adapter with blank groups and children
		// We will be adding children on a thread, and then update the ListView
		listAdapter_Account = new ExpandableListAdapter_Account(this,
				new ArrayList<String>(), new ArrayList<ArrayList<Account>>());

		// Set this blank adapter to the list view
		Log.i("TAG", listView + "");
		listView.setAdapter(listAdapter_Account);

		for (Account account : accounts) {
			listAdapter_Account.addItem(account);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.accountlist_menu, menu);

		MenuItem mi = menu.findItem(R.id.account_opm_addnew);
		mi.setTitle("Add Account");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection

		switch (item.getItemId()) {
		case R.id.account_opm_addnew: {

			Intent intent = new Intent(AccountsList.this,
					NewAccountAddView.class);
			AccountsList.this.startActivity(intent);

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
