package com.moneymanager.model;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.moneymanager.entity.Account;

public class TrCatagorySelectedItem  implements OnItemSelectedListener {
	
	private String selectedItem;

	public String getSelectedItem() {
		return selectedItem;
	}

	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		selectedItem=(String)parent.getItemAtPosition(pos);
		
	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
	}

	

}