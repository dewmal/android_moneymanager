package com.moneymanager.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.Action;
import com.markupartist.android.widget.ActionBar.IntentAction;
import com.moneymanager.entity.Account;
import com.moneymanager.entity.Transaction;
import com.moneymanager.entity.con.DBAdapter;
import com.moneymanager.model.AccountsAdapterSelectedItemListner;
import com.moneymanager.model.TrCatagorySelectedItem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class TransactionAddEdit extends Activity {
	private DBAdapter dbAdapter;
	private EditText etDesc;
	private EditText etAmount;
	private Spinner spinnerTrSpAcc;
	private Spinner spinnerTrSpCat;
	private AccountsAdapterSelectedItemListner accountsAdapterSelectedListner;
	private TrCatagorySelectedItem trCatagorySelectedItem;
	// /
	private boolean editableView = false;
	private Transaction transaction = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		try {
			transaction = (Transaction) getIntent().getExtras().getSerializable("EDITTR");
		} catch (Exception e) {
			// TODO: handle exception
		}
		setContentView(R.layout.transaction_addeditview);

		final ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
		// actionBar.setHomeAction(new IntentAction(this, createIntent(this),
		// R.drawable.ic_title_home_demo));
		actionBar.setTitle("Transactions Add Edit");
		final Action homeAction = new IntentAction(this, getHomeIntent(), R.drawable.ic_title_home_default);
		actionBar.setHomeAction(homeAction);
		actionBar.setDisplayHomeAsUpEnabled(true);

		

		dbAdapter = new DBAdapter(this);

		List<Account> accounts = new ArrayList<Account>();
		dbAdapter.open();
		accounts = dbAdapter.getAllAccounts();
		dbAdapter.close();

		spinnerTrSpAcc = (Spinner) findViewById(R.id.transactions_sp_accounts);
		ArrayAdapter<Account> accountSpinnnerAdapter = new ArrayAdapter<Account>(
				this, android.R.layout.simple_spinner_item, accounts);
		spinnerTrSpAcc.setAdapter(accountSpinnnerAdapter);
		accountsAdapterSelectedListner = new AccountsAdapterSelectedItemListner();
		spinnerTrSpAcc
				.setOnItemSelectedListener(accountsAdapterSelectedListner);

		spinnerTrSpCat = (Spinner) findViewById(R.id.transactions_sp_category);
		ArrayAdapter spinnerTrSpCatAdapter = ArrayAdapter
				.createFromResource(this, R.array.tr_cat_list,
						android.R.layout.simple_spinner_item);

		spinnerTrSpCat.setAdapter(spinnerTrSpCatAdapter);
		trCatagorySelectedItem = new TrCatagorySelectedItem();
		spinnerTrSpCat.setOnItemSelectedListener(trCatagorySelectedItem);

		etAmount = (EditText) findViewById(R.id.transactions_et_amounts);
		etDesc = (EditText) findViewById(R.id.transactions_et_desc);

		Button buttonAdd = (Button) findViewById(R.id.transactions_bt_add);
		buttonAdd.setOnClickListener(btAddTr);

		if (transaction != null) {
			etAmount.setText(transaction.getAmount() + "");
			etDesc.setText(transaction.getDescription());

			spinnerTrSpAcc.setSelection(accountSpinnnerAdapter
					.getPosition(transaction.getAccountid()));
			spinnerTrSpCat.setSelection(spinnerTrSpCatAdapter
					.getPosition(transaction.getTransactiontype()));
			
			buttonAdd.setText("Update");
			buttonAdd.setOnClickListener(btUpdateTr);
		}
	}

	private Intent getHomeIntent() {
		return new Intent(this,
				TransactionList.class);
	}
	
	
	private View.OnClickListener btUpdateTr=new View.OnClickListener() {
		
		public void onClick(View arg0) {
			transaction.setAccountid(accountsAdapterSelectedListner
					.getSelectedItem());
			transaction.setTransactiontype(trCatagorySelectedItem
					.getSelectedItem());
			transaction.setDescription(etDesc.getEditableText().toString());
			transaction.setAmount(Double.valueOf(etAmount.getEditableText()
					.toString()));

			dbAdapter.open();
			dbAdapter.updateTransaction(transaction);
			dbAdapter.close();
			
			Intent home=getHomeIntent();
			TransactionAddEdit.this.startActivity(home);
			TransactionAddEdit.this.finish();
			
		}
	};

	private View.OnClickListener btAddTr = new View.OnClickListener() {

		public void onClick(View arg0) {

			transaction=new Transaction();
			transaction.setAccountid(accountsAdapterSelectedListner
					.getSelectedItem());
			transaction.setTransactiontype(trCatagorySelectedItem
					.getSelectedItem());
			transaction.setDescription(etDesc.getEditableText().toString());
			transaction.setAmount(Double.valueOf(etAmount.getEditableText()
					.toString()));

			dbAdapter.open();
			dbAdapter.insertTransaction(transaction);
			dbAdapter.close();
			
			
			Intent home=getHomeIntent();
			TransactionAddEdit.this.startActivity(home);
			TransactionAddEdit.this.finish();
			

		}
	};

}
