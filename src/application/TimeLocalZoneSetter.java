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
		Integer month = dateTime.now(clock).getMonth().getValue();
		String to_text_day="", to_text_mont="";
		
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
		
		if(month.equals(1)) {
			to_text_mont="Jan. ";
		} else if(month.equals(2)) {
			to_text_mont="Feb. ";
		} else if(month.equals(3)) {
			to_text_mont="Mar. ";
		} else if(month.equals(4)) {
			to_text_mont="Apr. ";
		} else if(month.equals(5)) {
			to_text_mont="May ";
		} else if(month.equals(6)) {
			to_text_mont="Jun. ";
		} else if(month.equals(7)) {
			to_text_mont="Jul. ";
		}else if(month.equals(8)) {
			to_text_mont="Aug. ";
		}else if(month.equals(9)) {
			to_text_mont="Sept. ";
		} else if(month.equals(10)) {
			to_text_mont="Oct. ";
		} else if(month.equals(11)) {
			to_text_mont="Nov. ";
		} else 
			to_text_mont="Dec. ";
		
		
		dateString=to_text_day + dateTime.now(clock).getDayOfMonth()+" "+to_text_mont+dateTime.now(clock).getYear();
		timeString=dateTime.now(clock).getHour()+":"+dateTime.now(clock).getMinute();
		
	}
}
