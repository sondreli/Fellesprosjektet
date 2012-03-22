package controller;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AlterDate {
	
	public AlterDate() {
		
	}
	
	public static int getLastWeekOfYear(int year) {
		int week;
		int month = 11;
		
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.YEAR, year);
		week = cal.get(Calendar.WEEK_OF_YEAR);
		
		return week+6;
	}
	
	public static int getFirstWeekOfMonth(model.MyCalendar mycal) {
		int cweek = mycal.getCurrentWeek();
		int cmonth = mycal.getCurrentMonth();
		int cyear = mycal.getCurrentYear();
		
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(Calendar.MONTH, cmonth);
		cal.set(Calendar.YEAR, cyear);
		cweek = cal.get(Calendar.WEEK_OF_YEAR);
		
		return cweek;
	}
	
	public static void increaseMonth(model.MyCalendar mycal) {
//		int cdate;
		int cweek = mycal.getCurrentWeek();
		int cmonth = mycal.getCurrentMonth();
		int cyear = mycal.getCurrentYear();
		
		if(cmonth == 11 && cyear <= 2100) {
			cmonth = 0;
			cyear++;
		}
		else {
			cmonth++;
		}
		
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(Calendar.MONTH, cmonth);
		cal.set(Calendar.YEAR, cyear);
		cweek = cal.get(Calendar.WEEK_OF_YEAR);
//		cdate = cal.get(Calendar.DATE);
		
		mycal.setCurrentAll(cweek, cmonth, cyear);
	}
	
	public static void decreaseMonth(model.MyCalendar mycal) {
		int cweek = mycal.getCurrentWeek();
		int cmonth = mycal.getCurrentMonth();
		int cyear = mycal.getCurrentYear();
		
		if(cmonth == 0 && cyear >= 1920) {
			cmonth = 11;
			cyear--;
		}
		else {
			cmonth--;
		}
		
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(Calendar.MONTH, cmonth);
		cal.set(Calendar.YEAR, cyear);
		cweek = cal.get(Calendar.WEEK_OF_YEAR);
//		cdate = cal.get(Calendar.DATE);
		
		mycal.setCurrentAll(cweek, cmonth, cyear);
	}
	
	public static void increaseWeek(model.MyCalendar mycal) {
		int cweek = mycal.getCurrentWeek();
		int cmonth = mycal.getCurrentMonth();
		int cyear = mycal.getCurrentYear();
		
		if(cweek == getLastWeekOfYear(cyear) && cyear <= 2100) {
			cweek = 1;
			cyear++;
		}
		else {
			cweek++;
		}
		
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(Calendar.WEEK_OF_YEAR, cweek);
		cal.set(Calendar.YEAR, cyear);
		
		Date date = cal.getTime();
		cmonth = date.getMonth();
		mycal.setCurrentAll(cweek, cmonth, cyear);
	}

	public static void decreaseWeek(model.MyCalendar mycal) {
		int cweek = mycal.getCurrentWeek();
		int cmonth = mycal.getCurrentMonth();
		int cyear = mycal.getCurrentYear();
		
		if(cweek == 1 && cyear >= 1920) {
			cweek = 52;
			cyear--;
		}
		else {
			cweek--;
		}
		
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(Calendar.WEEK_OF_YEAR, cweek);
		cal.set(Calendar.YEAR, cyear);
		
		Date date = cal.getTime();
		cmonth = date.getMonth();
		mycal.setCurrentAll(cweek, cmonth, cyear);
	}
}
