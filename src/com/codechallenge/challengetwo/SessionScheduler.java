package com.codechallenge.challengetwo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class is responsible for reading the list of conferences from input file and schedule accordingly based on the given constraints.
 * 
 * @author Santhoshkumar.DS
 *
 */
public class SessionScheduler {
	
	public static void main(String args[]) {
		List<String> fileLines = new ArrayList<>();
		
		try (Stream<String> fileStream = Files.lines(Paths.get(ConferenceConstants.INPUT_FILEPATH))) {
			fileLines = fileStream.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<Conference> conferenceDetails = new ArrayList<>();
		if (fileLines != null && !fileLines.isEmpty()) {
			int conferenceId = 1;
			for(String line: fileLines) {
				String duration = line.substring(line.length()-5, line.length()-3);
				conferenceDetails.add(new Conference(conferenceId, duration, line));
				conferenceId++;
			}
		}
		scheduleSessions(conferenceDetails);
	}
	
	/**
	 * Method to schedule sessions until all conferences are scheduled in any of the track.
	 * @param conferenceDetails
	 */
	private static void scheduleSessions(List<Conference> conferenceDetails) {
		String startTime = ConferenceConstants.START_TIME;
		SimpleDateFormat dateFormat = new SimpleDateFormat(ConferenceConstants.DATE_FORMAT);
		try {
			
			boolean isAllScheduled = false;
			int trackId = 1;
			while (!isAllScheduled) {
				Date date = dateFormat.parse(startTime);
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				
				Iterator<Conference> confIterator = conferenceDetails.iterator();
				System.out.println("Track"+trackId+":");
				
				while (confIterator.hasNext()) {
					boolean endOfDay = false;
					Conference conference = confIterator.next();
					String duration = conference.getDuration();
					String title = conference.getTitle();
					Calendar tempCal = Calendar.getInstance();
					Date tempDate = cal.getTime();
					tempCal.setTime(tempDate);
				    
				    //check if less than 12:00 PM 
				    String lunchTime = ConferenceConstants.LUNCH_TIME;
				    Date lunchDate = dateFormat.parse(lunchTime);
				    tempCal.add(Calendar.MINUTE, Integer.valueOf(duration));
				    
				    
				    if ((tempCal.getTime().compareTo(lunchDate) <= 0)) {
				    	System.out.println(dateFormat.format(cal.getTime()) +" - " +  title);
				    	cal.add(Calendar.MINUTE, Integer.valueOf(duration));
				    	confIterator.remove();
				    	if(cal.getTime().compareTo(lunchDate) == 0) {
				    		System.out.println(dateFormat.format(cal.getTime()) +" - " +  ConferenceConstants.LUNCH_TIME_STR);
				    		cal.add(Calendar.MINUTE, 60);
				    	}
				    } else {
				    	//check if greater than 1:00 PM
					    String noonSession = ConferenceConstants.NOON_TIME;
					    Date noonDate = dateFormat.parse(noonSession);
					    if ((tempCal.getTime().compareTo(noonDate) >= 0)) { 
					    	//3pm time
					    	String threepmTime = ConferenceConstants.THREEPM_TIME;
					    	Date threePmDate = dateFormat.parse(threepmTime);
					    	String lastSession = ConferenceConstants.LAST_SESSION;
						    Date lastSessionDate = dateFormat.parse(lastSession);
					    	if ((cal.getTime().compareTo(threePmDate) >= 0) && (cal.getTime().compareTo(lastSessionDate) <= 0)) {
					    		long diffInMillies =  lastSessionDate.getTime() - cal.getTime().getTime();
					    		long diff = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
					    		if (Long.valueOf(duration) == diff) {
					    			System.out.println(dateFormat.format(cal.getTime()) +" - " +  title);
							    	cal.add(Calendar.MINUTE, Integer.valueOf(duration));
							    	confIterator.remove();
					    		} else {
					    			continue;
					    		}
					    	} else {
					    		System.out.println(dateFormat.format(cal.getTime()) +" - " +  title);
						    	cal.add(Calendar.MINUTE, Integer.valueOf(duration));
						    	confIterator.remove();
					    	}
					    	
					    	if(cal.getTime().compareTo(lastSessionDate) == 0) {
					    		System.out.println(dateFormat.format(cal.getTime()) +" - " +  ConferenceConstants.NETWORKING_EVENT);
					    		cal.add(Calendar.MINUTE, 60);
					    		endOfDay = true;
					    	}
					    }
				    }
				    if (endOfDay)
				    	break;
				}
				if (conferenceDetails.size() == 0) {
					isAllScheduled = true;
					break;
				}
				trackId++;
				System.out.println();
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
