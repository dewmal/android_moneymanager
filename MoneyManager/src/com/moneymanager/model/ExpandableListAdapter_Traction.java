package com.moneymanager.model;

import java.util.ArrayList;

import com.moneymanager.activity.R;
import com.moneymanager.entity.Transaction;



import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ExpandableListAdapter_Traction extends BaseExpandableListAdapter {

	

	@Override
	public boolean areAllItemsEnabled() {
		return true;
	}

	private Context context;

	private ArrayList<String> groups;

	private ArrayList<ArrayList<Transaction>> children;

	public ExpandableListAdapter_Traction(Context context, ArrayList<String> groups,
			ArrayList<ArrayList<Transaction>> children) {
		this.context = context;
		this.groups = groups;
		this.children = children;
	}

	/**
	 * A general add method, that allows you to add a Vehicle to this list
	 * 
	 * Depending on if the category opf the vehicle is present or not, the
	 * corresponding item will either be added to an existing group if it
	 * exists, else the group will be created and then the item will be added
	 * 
	 * @param vehicle
	 */
	public void addItem(Transaction word) {
		Log.i("TAG", word.toString());
		String group = word.getTransactiontype();
		if (!groups.contains(group)) {
			groups.add(group);
		}
		int index = groups.indexOf(group);
		if (children.size() < index + 1) {
			children.add(new ArrayList<Transaction>());
		}
		children.get(index).add(word);
	}

	public Object getChild(int groupPosition, int childPosition) {
		return children.get(groupPosition).get(childPosition);
	}

	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	// Return a child view. You can load your custom layout here.
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		Transaction transaction = (Transaction) getChild(groupPosition, childPosition);
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.transaction_child_layout, null);
		}
		TextView tv = (TextView) convertView.findViewById(R.id.transaction_tvChild);
		tv.setText("   " + transaction.getDescription()+" "+ transaction.getAmount());

		// Depending upon the child type, set the imageTextView01
		tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

		return convertView;
	}

	
	public int getChildrenCount(int groupPosition) {
		return children.get(groupPosition).size();
	}

	
	public Object getGroup(int groupPosition) {
		return groups.get(groupPosition);
	}

	
	public int getGroupCount() {
		return groups.size();
	}

	
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	// Return a group view. You can load your custom layout here.
	
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		String group = (String) getGroup(groupPosition);
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.transaction_group_layout, null);
		}
		TextView tv = (TextView) convertView.findViewById(R.id.tvGroup);
		tv.setText(group);

	
		
		return convertView;
	}

	
	public boolean hasStableIds() {
		return true;
	}

	
	public boolean isChildSelectable(int arg0, int arg1) {
		return true;
	}

}
