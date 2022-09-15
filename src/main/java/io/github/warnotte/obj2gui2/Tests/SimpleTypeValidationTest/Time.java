package io.github.warnotte.obj2gui2.Tests.SimpleTypeValidationTest;

import java.util.List;
import java.util.regex.Pattern;

import io.github.warnotte.obj2gui2.PROPERTY_MIGLAYOUT;
import io.github.warnotte.obj2gui2.PROPERTY_MIG_GRID;
import io.github.warnotte.waxlib3.core.TemplatePropertyMerger.property_mode;
import io.github.warnotte.waxlib3.core.TemplatePropertyMerger.Annotations.PROPERTY_interface;

@PROPERTY_MIGLAYOUT(LayoutConstraints = "ins 0", ColumnConstraints = "fill,grow", RowConstraints = "")
public class Time {

	private double minutes;
	public final static int PRECISION = 9;
	public Time() {
		super();
	}	
	public Time(double minutes) {
		super();
		this.minutes = minutes;
	}
	
	public static void main (String [] args) {
		Time t = new Time(2878);
		System.out.println(t.getDDHHMMOutput());
		System.out.println(t.getTimeOfTheDayDDHHMM());
	}
	
	/**
	 * NOTE: if the last character of the minutes is 0, the character is missing. 
	 * Ex: 1.2 = 1 hour and 20 minutes; 1.02 = 1 hour and 2 minutes
	 * @param hoursMinutes [hh.mm]
	 */
	public static int getMinutes (String hoursMinutes) {
		String[] split = hoursMinutes.split(Pattern.quote("."));
		if (split.length == 1) { // no dot -> the input is in minutes
			return Integer.valueOf(split[0]);
		}
		
		int hours = Integer.valueOf(split[0]);
		
		int minutes;
		if (split[1].length() == 1)		
			minutes = Integer.valueOf(split[1]) * 10;
		else
			minutes = Integer.valueOf(split[1]);
		
		return hours * 60 + minutes;
	}
	
	/**
	 * NOTE: if the last character of the minutes is 0, the character is missing. 
	 * Ex: 1.2 = 1 hour and 20 minutes; 1.02 = 1 hour and 2 minutes
	 * @param day (day = 0 means 0 * 24 * 60 minutes)
	 * @param hoursMinutes [hh.mm]
	 * @return time in minutes
	 */
	public static int getMinutes (int day, String hoursMinutes) {		
		int minutes = getMinutes(hoursMinutes);
		return day * 24 * 60 + minutes;
	}
	
	public static int getMinutes (int day, int hour, int minute) {		
		return day * 24 * 60 + hour * 60 + minute;
	}
	
	/**
	 * 
	 * @param day (day = 0 means 0 * 24 * 60 minutes)
	 * @param hours
	 * @return
	 */
	public static int getMinutes(int day, int hours) {
		return day * 24 * 60 + hours * 60;
	}
	
	public double getMinutes() {
		return minutes;
	}
	
	public void setMinutes(double minutes) {
		this.minutes = minutes;
	}
	
	public static Time getSum(Time time1, Time time2) {
		return new Time(time1.getMinutes() + time2.getMinutes());
	}
	
	public static Time getMin(Time time1, Time time2) {
		if (time1.getMinutes() < time2.getMinutes()) {
			return time1;
		}
		else {
			return time2;
		}			
	}
	
	public static Time getMax(Time time1, Time time2) {
		if (time1.getMinutes() > time2.getMinutes()) {
			return time1;
		}
		else {
			return time2;
		}			
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		//return "Time [minutes=" + minutes + "]";
		return ""+minutes;
	}
	
	public static Time getSum(Time time1, double time2) {
		return new Time(time1.getMinutes() + time2);
	}
	
	public Time multiply(Time time) {
		return new Time(minutes * time.getMinutes());
	}
	
	public Time multiply(double time) {
		return new Time(minutes * time);
	}
	
	public Time divideBy(Time time) {
		if (time.getMinutes() > 0) {
			return new Time(minutes / time.getMinutes());
		}
		else {
			return new Time(0);
		}
	}
	
	public Time divideBy(double time) {
		if (time > 0) {
			return new Time (minutes / time);
		}
		else {
			return new Time(0);
		}
	}
	
	public boolean isLessThan(Time time) {
		if (round(minutes, PRECISION) < round(time.getMinutes(), PRECISION))
			return true;
		else
			return false;
	}
	
	public boolean isLessOrEqualThan(Time time) {
		if (round(minutes, PRECISION) <= round(time.getMinutes(), PRECISION))
			return true;
		else
			return false;
	}
	
	public boolean isGreaterThan(Time time) {
		if (round(minutes, PRECISION) > round(time.getMinutes(), PRECISION))
			return true;
		else
			return false;
	}
	
	public boolean isGreaterOrEqualThan(Time time) {
		if (round(minutes, PRECISION) >= round(time.getMinutes(), PRECISION))
			return true;
		else
			return false;
	}
	
	public boolean isEqualTo(Time endTime) {
		if (round(minutes, PRECISION) == round(endTime.getMinutes(), PRECISION))
			return true;
		else
			return false;
	}
	
	/**
	 * @param minutes
	 * @return array containing the number of days, hours and minutes
	 */
	public static ShipGenerationTime getDaysHoursMinutes(double minutes) {
		double m = minutes;
		int days = (int) (minutes / 60 / 24);
		m = m - (days * 60 * 24);
		int hours = (int) (m / 60);
		int mins = (int) (m - (hours * 60));
		return new ShipGenerationTime(days, hours, mins);
	}
	
	public static Time getMin(List<Time> times) {
		double t = times.get(0).getMinutes();
		for (Time time : times) {
			if (time.getMinutes() < t) {
				t = time.getMinutes();
			}
		}
		return new Time(t);
	}
	
	public static Time getMax(List<Time> times) {
		double t = times.get(0).getMinutes();
		for (Time time : times) {
			if (time.getMinutes() > t) {
				t = time.getMinutes();
			}
		}
		return new Time(t);
	}
	
	public static Time getAverage(List<Time> times) {
		Time sumTime = new Time(0);
		for (Time time : times) {
			sumTime = sumTime.add(time);
		}
		return sumTime.divideBy(times.size());
	}
	
	/**
	 * Returns the time passed from the begining of the current day, in minutes.
	 * @param currentSimulationTime
	 * @return
	 */
	public static Time getTimeOfTheDay(Time currentSimulationTime) {
		int numberOfDays = (int) currentSimulationTime.getMinutes() / (24 * 60);
		double timeOfTheDay = currentSimulationTime.getMinutes() - 24 * 60 * numberOfDays;
		return new Time(timeOfTheDay);
	}
	
	public String getTimeOfTheDayDDHHMM() {
		Time timeOfDay = Time.getTimeOfTheDay(this);
		return timeOfDay.getHHMMOutput();
	}
	
	public Time add(double minutes) {
		return new Time(this.getMinutes() + minutes);
	}
	
	public Time add(Time minutes) {
		return new Time(this.getMinutes() + minutes.getMinutes());
	}
	
	public Time substract(Time minutes) {
		return new Time(this.getMinutes() - minutes.getMinutes());
	}
	
	public Time substract(double minutes) {
		return new Time(this.minutes - minutes);
	}
	
	public static double round (double value, int decimals) {
		double multi = Math.pow(10, decimals);
		return Math.round(value * multi) / multi;
	}
	
	/**
	 * Trying to find a way to make the editor work with OBJ2GUI2 because I cannot make use TimeEditor component within OBJ2GUI2 (as it has 3 values instead of only one :|). 
	 */
	
	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE, orderDisplay=10)
	@PROPERTY_MIG_GRID(attribut = "cell 0 0")
	public int getDays_()
	{
		ShipGenerationTime tim = getDaysHoursMinutes(this.getMinutes());
		return tim.getDays();
	}
	
	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE, orderDisplay=20)
	@PROPERTY_MIG_GRID(attribut = "cell 1 0")
	public int getHours_()
	{
		ShipGenerationTime tim = getDaysHoursMinutes(this.getMinutes());
		return tim.getHours();
	}
	
	@PROPERTY_interface(Operation = property_mode.PROPERTY_MERGEABLE, orderDisplay=30)
	@PROPERTY_MIG_GRID(attribut = "cell 2 0")
	public int getMinutes_()
	{
		ShipGenerationTime tim = getDaysHoursMinutes(this.getMinutes());
		return tim.getMinutes();
	}
	
	public void setDays_(int days)
	{
		setMinutes(getMinutes(days, getHours_(), getMinutes_()));
	}
	
	public void setHours_(int hours)
	{
		if (hours<0) hours = 0;
		if (hours>23) hours = 23;
		setMinutes(getMinutes(getDays_(), hours, getMinutes_()));
	}
	
	public void setMinutes_(int minutes)
	{
		if (minutes<0) minutes = 0;
		if (minutes>59) minutes = 59;
		setMinutes(getMinutes(getDays_(), getHours_(), minutes));
	}
	
	public String getHHMMOutput() {
		int hours = (int) this.getMinutes() / 60;
		int minutes = (int) this.getMinutes() % 60;
		return (hours < 10 ? "0" + hours : hours) + "h" +
			(minutes < 10 ? "0" + minutes : minutes) + "m";		
		//return hours + "h" + minutes + "m";
	}

	public String getDDHHMMOutput() {
		int days = (int) this.getMinutes() / 24 / 60;
		int hours = (int) this.getMinutes() % (24 * 60) / 60;
		int minutes = (int) this.getMinutes() % (24 * 60) % 60;
		
		return (days < 10 ? "00" + days : (days < 100 ? "0" + days : days)) + "d" +
				(hours < 10 ? "0" + hours : hours) + "h" +
				(minutes < 10 ? "0" + minutes : minutes) + "m";		
		//return days + "d" + hours + "h" + minutes + "m";
	}
	
	
}
