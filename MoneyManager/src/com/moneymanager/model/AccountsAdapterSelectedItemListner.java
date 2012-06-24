package com.moneymanager.model;

import java.sql.Array;
import java.util.List;

import com.moneymanager.entity.Account;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class AccountsAdapterSelectedItemListner implements OnItemSelectedListener {
	
	private Account selectedItem;

	public Account getSelectedItem() {
		return selectedItem;
	}

	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		selectedItem=((Account)parent.getItemAtPosition(pos));
		
	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
	}

	

}