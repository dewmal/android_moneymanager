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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer.Orientation;

import com.moneymanager.entity.Transaction;
import com.moneymanager.entity.con.DBAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;

/**
 * Sales demo bar chart.
 */
public class TransactionByDate extends AbstractDemoChart {

	/**
	 * Returns the chart name.
	 * 
	 * @return the chart name
	 */
	public String getName() {
		return "Sales horizontal bar chart";
	}

	/**
	 * Returns the chart description.
	 * 
	 * @return the chart description
	 */
	public String getDesc() {
		return "The monthly sales for the last 2 years (horizontal bar chart)";
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

		SimpleDateFormat formatter = new SimpleDateFormat(
				"d-MMM-yyyy");
		dbAdapter.open();
		List<Transaction> transactions = dbAdapter.getAllTransactions();
		dbAdapter.close();

		List<Date> titlesDate = new ArrayList<Date>();
		Map<String, List<Transaction>> values = new HashMap<String, List<Transaction>>();
		double max=0;
		for (Transaction transaction : transactions) {
			Log.i("TAG", transaction.getDatetime() + "");
			String trDate = formatter.format(transaction.getDatetime());
			List<Transaction> transactions2 = values.get(trDate);
			if (transactions2 == null) {
				transactions2 = new ArrayList<Transaction>();
			}

			transactions2.add(transaction);

			values.put(trDate, transactions2);
			if(max<transaction.getAmount()){
				max=transaction.getAmount();
			}

		}

		String[] titles = new String[] {};
		titles = values.keySet().toArray(titles);

		List<double[]> doubles = new ArrayList<double[]>();

		for (String key : values.keySet()) {
			List<Transaction> trList = values.get(key);
			
			Log.i("KEY", key);

			double[] dbValues = new double[trList.size()];
			for (int i = 0; i < dbValues.length; i++) {
				dbValues[i] = trList.get(i).getAmount();
			}

			doubles.add(dbValues);

		}
		
		
		XYMultipleSeriesDataset dataset = buildBarDataset(titles, doubles);
		
		
		
		int[] colors = new int[dataset.getSeriesCount()] ;
	

		for (int i = 0; i < colors.length; i++) {
			colors[i]=getRandomColor();
		}
		
		
		XYMultipleSeriesRenderer renderer = buildBarRenderer(colors);
		
		
		
		
		renderer.setBackgroundColor(Color.WHITE);		
		renderer.setOrientation(Orientation.HORIZONTAL);
		setChartSettings(renderer, "Transaction Report By Date Vise",
				"Date", "Spend Values", 0.5, 12.5, 0, max, Color.BLACK,
				Color.BLUE);
		renderer.setXLabels(1);
		renderer.setYLabels(10);

		for (int i = 0; i < titles.length; i++) {
			renderer.addXTextLabel((i + 1), titles[i]);
			Log.i("KEY", titles[i]);
		}

		int length = renderer.getSeriesRendererCount();
		for (int i = 0; i < length; i++) {
			SimpleSeriesRenderer seriesRenderer = renderer
					.getSeriesRendererAt(i);
			seriesRenderer.setDisplayChartValues(true);
		}

		
		for (int i = 0; i < renderer.getSeriesRendererCount(); i++) {
			Log.i("RENDER",renderer.getSeriesRendererAt(i).toString());
		}
		
		
		Log.i("RENDER", renderer.getSeriesRendererCount()+"");
		Log.i("DATASET",dataset.getSeries().length+"");
		
		Intent barChartIntent = ChartFactory.getBarChartIntent(context,
				dataset, renderer, Type.STACKED);
	
		return barChartIntent;
	}
	
	
}
