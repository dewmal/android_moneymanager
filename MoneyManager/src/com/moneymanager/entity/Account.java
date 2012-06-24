package com.moneymanager.entity;

import java.io.Serializable;

public class Account implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9019527725564960201L;
	private double init_value;
	private double curr_value;
	private String name;
	private int id;

	public double getInit_value() {
		return init_value;
	}

	public void setInit_value(double init_value) {
		this.init_value = init_value;
	}

	public double getCurr_value() {
		return curr_value;
	}

	public void setCurr_value(double curr_value) {
		this.curr_value = curr_value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return name +"  - "+curr_value +" GB";
	}
	
	

}
