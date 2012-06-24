/**
 * Copyright (C) 2009, 2010 SC 4ViewSoft SRL
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.moneymanager.report.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.achartengine.ChartFactory;
import org.achartengine.renderer.DefaultRenderer;

import com.moneymanager.entity.Transaction;
import com.moneymanager.entity.con.DBAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

/**
 * Budget demo pie chart.
 */
public class TrCatagoryPieChart extends AbstractDemoChart {
	/**
	 * Returns the chart name.
	 * 
	 * @return the chart name
	 */
	public String getName() {
		return "Budget chart";
	}

	/**
	 * Returns the chart description.
	 * 
	 * @return the chart description
	 */
	public String getDesc() {
		return "The budget per project for this year (pie chart)";
	}

	/**
	 * Executes the chart demo.
	 * 
	 * @param context
	 *            the context
	 * @return the built intent
	 */
	public Intent execute(Context context) {
		dbAdapter = new DBAdapter(context);
		dbAdapter.open();
		List<Transaction> listTr = dbAdapter.getAllTransactions();
		dbAdapter.close();
		
		List<String> lists=new ArrayList<String>();
		Map<String ,Double > values=new HashMap<String ,Double >();
		for (Transaction transaction : listTr) {
			String type=transaction.getTransactiontype();
			
			Double value=values.get(type);
			if(value!=null){
				value=value.doubleValue()+transaction.getAmount();
			}else{
				value=transaction.getAmount();
			}
			
			values.put(type, value);
			
		}
		

		
		
		
		int[] colors = new int[values.values().size()];
		for (int j = 0; j < colors.length; j++) {
			colors[j]=getRandomColor();
		}
		DefaultRenderer renderer = buildCategoryRenderer(colors);
		
		renderer.setZoomButtonsVisible(true);
		renderer.setZoomEnabled(true);
		renderer.setChartTitleTextSize(20);
		return ChartFactory.getPieChartIntent(context,
				buildCategoryDataset("Project budget", values), renderer,
				"Budget");
	}

}
