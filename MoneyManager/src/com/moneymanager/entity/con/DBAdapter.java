package com.moneymanager.entity.con;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.moneymanager.entity.Account;
import com.moneymanager.entity.Transaction;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.DateFormat;
import android.util.Log;

public class DBAdapter {

	private static final String TAG = "DBAdapter";
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;

	public static final String TABLE_ACCOUNT = "account";
	public static final String TABLE_TRANSACTION = "trans";

	public static final String DATABASE_NAME = "moneymanagerdb.db";
	private static final int DATABASE_VERSION = 1;

	private final Context mCtx;

	// Database creation sql statement
	private static final String ACCOUNT_CREATE = "create table "
			+ TABLE_ACCOUNT + " ( " + "ID"
			+ " INTEGER primary key autoincrement, " + " NAME "
			+ " TEXT not null, " + " CURR_VALUE " + " TEXT not null, "
			+ " INIT_VALUE " + " DECIMAL not null); ";
	private static final String TRANSACTION_CREATE = "create table "
			+ TABLE_TRANSACTION + " ( " + " ID "
			+ " INTEGER primary key autoincrement, " + " ACC_ID "
			+ " TEXT not null , " + " TYPE " + " TEXT not null ,"
			+ " DESCRIPTION " + " TEXT not null, " + " AMOUNT "
			+ " DECIMAL not null, " + " ACC_AMOUNT " + " DECIMAL not null ,"
			+ " TRDATE DATETIME not null);";

	private static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase database) {
			database.execSQL(ACCOUNT_CREATE);
			database.execSQL(TRANSACTION_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(DBAdapter.class.getName(), "Upgrading database from version "
					+ oldVersion + " to " + newVersion
					+ ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTION);
			onCreate(db);
		}
	}

	public DBAdapter(Context context) {
		this.mCtx = context;
	}

	public void inserAccount(Account account) {
		String sql = "INSERT or replace INTO " + TABLE_ACCOUNT
				+ " (NAME , CURR_VALUE , INIT_VALUE) VALUES( \""
				+ account.getName() + "\" " + " , " + account.getCurr_value()
				+ " , " + account.getInit_value() + " )";
		mDb.execSQL(sql);
	}

	public void updateAccount(Account account) {
		ContentValues values = new ContentValues();

		values.put("NAME", account.getName());
		values.put("CURR_VALUE", account.getCurr_value());

		mDb.update(TABLE_ACCOUNT, values, "ID=?",
				new String[] { account.getId() + "" });
	}

	public void insertTransaction(Transaction transaction) {
		double acc_cur_value = transaction.getAccountid().getCurr_value()
				- transaction.getAmount();
		String sql = "INSERT or replace INTO "
				+ TABLE_TRANSACTION
				+ " (ACC_ID , TYPE , DESCRIPTION  , AMOUNT, ACC_AMOUNT, TRDATE ) VALUES("
				+ transaction.getAccountid().getId() + " , \""
				+ transaction.getTransactiontype() + "\" " + " , \""
				+ transaction.getDescription() + "\" , "
				+ transaction.getAmount() + " , " + acc_cur_value + "," + ""
				+ "(SELECT date('now'))" + " )";
		mDb.execSQL(sql);
		transaction.getAccountid().setCurr_value(acc_cur_value);
		updateAccount(transaction.getAccountid());
	}

	public void updateTransaction(Transaction transaction) {

		Transaction preAction = getTransaction(transaction.getId());
		double cuurunt_amount = transaction.getAmount();
		double account_currValue = transaction.getAccountid().getCurr_value();
		double trans_curr_amount = preAction.getAmount();
		double accAmount = (account_currValue + trans_curr_amount)
				- cuurunt_amount;

		ContentValues values = new ContentValues();
		values.put("TYPE", transaction.getTransactiontype());
		values.put("DESCRIPTION", transaction.getDescription());
		values.put("AMOUNT", cuurunt_amount);
		values.put("ACC_AMOUNT", accAmount);

		mDb.update(TABLE_TRANSACTION, values, "ID=?",
				new String[] { transaction.getId() + "" });

		transaction.getAccountid().setCurr_value(accAmount);
		updateAccount(transaction.getAccountid());
	}

	public Transaction getTransaction(int id) {
		Transaction transaction = null;
		Log.i("KEY-1", id + "");
		for (Transaction transact : getAllTransactions()) {
			Log.i("KEY", transact.toString());
			if (transact.getId() == id) {
				transaction = transact;
				break;
			}
		}

		return transaction;
	}

	public Account getAccount(int id) {
		Account account = null;

		for (Account accElem : getAllAccounts()) {
			if (accElem.getId() == id) {
				account = accElem;
				break;
			}
		}

		return account;
	}

	public List<Account> getAllAccounts() {
		String[] columns = new String[] { "ID", "NAME", "INIT_VALUE",
				"CURR_VALUE" };
		Cursor cursor = mDb.query(TABLE_ACCOUNT, columns, null, null, null,
				null, null);
		List<Account> accounts = new ArrayList<Account>();

		for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {
			Account account = new Account();
			account.setId(cursor.getInt(cursor.getColumnIndex("ID")));
			account.setName(cursor.getString(cursor.getColumnIndex("NAME")));
			account.setInit_value(cursor.getDouble(cursor
					.getColumnIndex("INIT_VALUE")));
			account.setCurr_value(cursor.getDouble(cursor
					.getColumnIndex("CURR_VALUE")));
			accounts.add(account);
		}
		return accounts;
	}

	public List<Transaction> getAllTransactions() {
		String[] columns = new String[] { "ID", "ACC_ID", "TYPE",
				"DESCRIPTION", "AMOUNT", "ACC_AMOUNT","TRDATE" };
		Cursor cursor = mDb.query(TABLE_TRANSACTION, columns, null, null, null,
				null, null);
		List<Transaction> transactions = new ArrayList<Transaction>();

		for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {
			Transaction transaction = new Transaction();
			transaction.setId(cursor.getInt(cursor.getColumnIndex("ID")));

			Account account = getAccount(cursor.getInt(cursor
					.getColumnIndex("ACC_ID")));

			transaction.setAccountid(account);

			transaction.setAmount(cursor.getDouble(cursor
					.getColumnIndex("AMOUNT")));
			transaction.setDescription(cursor.getString(cursor
					.getColumnIndex("DESCRIPTION")));
			transaction.setTransactiontype(cursor.getString(cursor
					.getColumnIndex("TYPE")));

			String stdate = cursor.getString(cursor.getColumnIndex("TRDATE"));
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date date = null;
			try {
				date = formatter.parse(stdate);
				Log.i("MSG", date.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		
			Log.i("MSG", date.toString());
			transaction.setDatetime(date);
			
			transactions.add(transaction);
		}
		return transactions;
	}

	public DBAdapter open() throws SQLException {
		mDbHelper = new DatabaseHelper(mCtx);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		mDbHelper.close();
	}

}
