package application;

import java.time.Clock;
import java.time.ZonedDateTime;

import javafx.scene.control.Label;

public class TimeLocalZoneSetter {
	
	private String dateString, timeString;

	public TimeLocalZoneSetter() {
		super();
		calculate_actual_time();
	}


	public String getDateString() {
		return dateString;
	}

	public String getTimeString() {
		return timeString;
	}


	private void calculate_actual_time() {
		// Clock with the system's default timezone
		Clock clock = Clock.systemDefaultZone();
		ZonedDateTime dateTime = ZonedDateTime.now(); // Gets the current date and time, with your default time-zone
		Integer day = dateTime.now(clock).getDayOfWeek().getValue();
		String to_text_day="";
		
		if(day.equals(1)) {
			to_text_day="Mon. ";
		}else if(day.equals(2)) {
			to_text_day="Tues. ";
		}else if(day.equals(3)) {
			to_text_day="Wed. ";
		}else if(day.equals(4)) {
			to_text_day="Thu. ";
		}else if(day.equals(5)) {
			to_text_day="Fri. ";
		}else if(day.equals(6)) {
			to_text_day="Sat. ";
		}else 
			to_text_day="Sun. ";
		
		
		dateString=to_text_day + dateTime.now(clock).getDayOfMonth()+"-"+dateTime.now(clock).getMonth().getValue()+"-"+dateTime.now(clock).getYear();
		timeString=dateTime.now(clock).getHour()+":"+dateTime.now(clock).getMinute();
		
	}
}
