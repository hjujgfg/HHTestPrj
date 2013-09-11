package com.hhschool.testprj;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.os.Parcel;
import android.os.Parcelable;

public class Request implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 504741975749699456L;
	public Request() {
		// TODO Auto-generated constructor stub
		init();
	}
	private String name;
	private Calendar birthDate;
	private boolean isMale;
	private String position;
	private int salary; 
	private String phone;
	private String email;
	private void init(){
		name = "";
		birthDate = new GregorianCalendar(2000, 0, 1);
		isMale = true;
		position = "";
		salary = 0;
		phone = "";
		email = "";
	}
	private void validateName() {
		if (name == "") return; 
		String tmp = name.toLowerCase();
		tmp.trim();		
		String[] words = tmp.split(" ");
		StringBuilder sb = new StringBuilder();
		for (String s : words){
			sb.append(s.substring(0, 1).toUpperCase() + s.substring(1) + " ");
		}
		name = sb.toString();
	}
	private void validatePosition() {
		if (position == "") return; 
		position = position.substring(0, 1).toUpperCase() + position.substring(1);
	}
	public void setName(String s) {
		name = s;
		validateName();
	}
	public String Name() {
		return name;
	}
	public void setDate(Calendar d) throws NullPointerException {
		if (d == null) {
			throw new NullPointerException();
		}
		birthDate = (Calendar) d.clone();
	}
	public Calendar getDate(){
		return birthDate;
	}
	public void IsMale(boolean bb) {
		isMale = bb;
	}
	public boolean getGender(){
		return isMale;
	}
	public void setPosition(String pos){
		if (pos == "") return; 
		position = pos;
		validatePosition();
	}
	public String getPosition() {
		return position;
	}
	public void setSalary(int sal){
		if (sal < 0) return;
		salary = sal;
	}
	public int getSalary(){
		return salary;
	}
	public void setPhone(String ph) {
		phone = ph;
	}
	public String getPhone() {
		return phone;
	}
	public void setEmail(String em) {
		email = em;
	}
	public String getEmail() {
		return email;
	}
	public static boolean checkPhone(String ph){
		Character c = ph.charAt(0);		
		if (! (c == '+' || Character.isDigit(c))) return false;
		for (int i = 1; i < ph.length(); i ++){
			c = ph.charAt(i);
			if (! (Character.isDigit(c) || c == ' ')) return false;
		}
		return true;
	}
	public String getBirthString(){
		return birthDate.get(Calendar.DAY_OF_MONTH) + "/" + (birthDate.get(Calendar.MONTH)+1) 
				+ "/" + birthDate.get(Calendar.YEAR);
	}	
}
