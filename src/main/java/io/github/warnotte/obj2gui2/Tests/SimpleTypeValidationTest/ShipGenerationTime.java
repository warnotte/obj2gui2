package io.github.warnotte.obj2gui2.Tests.SimpleTypeValidationTest;

public class ShipGenerationTime {

	private int days;
	private int hours;
	private int minutes;

	public ShipGenerationTime() {
		super();
	}

	public ShipGenerationTime(int days, int hours, int minutes) {
		super();
		this.days = days;
		this.hours = hours;
		this.minutes = minutes;
	}

	public int getDays() {
		return days;
	}
	
	public int getHours() {
		return hours;
	}
	
	public int getMinutes() {
		return minutes;
	}
	
	public void setDays(int days) {
		this.days = days;
	}
	
	public void setHours(int hours) {
		this.hours = hours;
	}
	
	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	@Override
	public String toString() {
		return "ShipGenerationTime [days=" + days + ", hours=" + hours + ", minutes=" + minutes + "]";
	}

}
