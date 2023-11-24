package com.example.demo;

import biweekly.component.VEvent;
import biweekly.property.ICalProperty;
import biweekly.property.Image;
import biweekly.property.TextProperty;

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;
import biweekly.property.Summary;
import biweekly.property.Location;
import biweekly.property.Organizer;
import biweekly.property.Description;
import biweekly.property.ICalProperty;
import biweekly.property.DateStart;
import biweekly.property.Classification;
import biweekly.property.DateEnd;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.ByteArrayOutputStream;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeBodyPart;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import java.util.Properties;
import javax.mail.Session;
import javax.mail.Transport;

import java.util.Properties;
import javax.mail.internet.MimeMessage;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;
import java.util.Properties;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;



import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import com.google.api.services.calendar.model.ConferenceData;
import com.google.api.services.calendar.model.CreateConferenceRequest;
import com.google.api.services.calendar.model.ConferenceSolution;
import com.google.api.services.calendar.model.ConferenceSolutionKey;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.ArrayList;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.gmail.GmailScopes;

import com.google.api.services.calendar.model.ConferenceData;
import com.google.api.services.calendar.model.ConferenceSolution;
import com.google.api.services.calendar.model.ConferenceSolutionKey;
import com.google.api.services.calendar.model.CreateConferenceRequest;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;

@Controller
public class SpringBootJdbcController {

	private static final String APPLICATION_NAME = "CalendarTest";
	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
	/** Directory to store authorization tokens for this application. */
	private static final String TOKENS_DIRECTORY_PATH = "tokens1";

	/**
	 * Global instance of the scopes required by this quickstart. If modifying these
	 * scopes, delete your previously saved tokens/ folder.
	 */
	private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);
	private static final String CREDENTIALS_FILE_PATH = "/credentials1.json";

	private static final String SUMMARY = "IT Patch Management";
	private static final String LOCATION = "800 Howard St., San Francisco, CA 94103";
	private static final String DESCRIPTION = "1Great chance to resolve IT issues seamlessly!";
	private static final String RECURRENTCE_RULE = "RRULE:FREQ=DAILY;COUNT=1";
	private static final String calendarId = "primary";
	private static final int DAY_START_TIME = 9;
	private static final int DAY_END_TIME = 17;
	private static final String DAY_TYPE_WEEKDAY = "WEEKDAY";
	private static final String DAY_TYPE_WEEKEND = "WEEKEND";
	private static final String DAY_SLOT_START_TIME = "09:00";
	private static final String DAY_SLOT_END_TIME = "17:00";
	private static final String SCOPE_IDENTITY_QUERY = "SELECT SCOPE_IDENTITY()";
	private static String emailSubject = "Event Invitation: IT Patch Management";
	private static String emailPatchingRequestSubject = "Automated Patching Request Notification";

	private static final String senderEmail = "saurabh2204@gmail.com";
	private static final String DEFAULT_RECIPIENT_EMAIL = "saurabh2204@gmail.com";

	
	


	long no_of_days = 0;
	String[] slot_start_date_time = null, slot_end_date_time = null;

	@Autowired
	JdbcTemplate jdbc;

//	@Autowired
//	HomeController homeController;

	@RequestMapping("/insert")
	public String index() {
		jdbc.execute("insert into user(name,email)values('javatpoint','java@javatpoint.com')");
		return "data inserted Successfully";
	}
//create table slots(id int UNSIGNED primary key not null auto_increment, slot_start timestamp, slot_end timestamp, time_zone varchar(100), it_email_list varchar(1000), attendee_email_list varchar(1000), region varchar(100),is_booked boolean);  

	/**
	 * @param slot_start
	 * @param slot_end
	 * @param it_email_list
	 * @param attendee_email_list
	 * @param region
	 * @param is_booked
	 * @param time_zone
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/insertslot")
	@ResponseBody
	public String insertslot(String slot_start, String slot_end, String it_email_list, String attendee_email_list,
			String region, boolean is_booked, String time_zone) throws ParseException {

		String query1 = "insert into slots(slot_start,slot_end, time_zone, it_email_list,attendee_email_list,region,is_booked) values('2022-08-09 07:30:30','2022-08-09 08:30:30', 'EST','saurabh.gupta1@hcl.com','saurabh22045@gmail.com', 'Spain', 0)";

		String query2 = "insert into slots(slot_start,slot_end, time_zone, it_email_list,attendee_email_list,region,is_booked) values('"
				+ slot_start + "','" + slot_end + "', '" + time_zone + "','" + it_email_list + "','"
				+ attendee_email_list + "', '" + region + "', " + BooleanUtils.toInteger(is_booked) + ")";

		jdbc.execute(query2);
		return "slot created Successfully";
	}

	/**
	 * @param slot_start
	 * @param slots_end
	 * @param slot_duration
	 * @param it_email_list
	 * @param attendee_email_list
	 * @param region
	 * @param is_booked
	 * @param time_zone
	 * @param include_weekends
	 * @param holidays
	 * @param create_event
	 * @return
	 * @throws ParseException
	 * @throws GeneralSecurityException
	 * @throws IOException
	 * @throws MessagingException 
	 */
	@RequestMapping("/insertslots")
	@ResponseBody
	public String insertslots(String slot_start, String slots_end, String slot_duration, String it_email_list,
			String attendee_email_list, String region, boolean is_booked, String time_zone, boolean include_weekends,
			String holidays, String create_event) throws ParseException, IOException, GeneralSecurityException {

		getSlotDurationDays(slot_start, slots_end);
		String slot_end = "";

		String query1 = "insert into slots(slot_start,slot_end, time_zone, it_email_list,attendee_email_list,region,is_booked) values('2022-08-09 07:30:30','2022-08-09 08:30:30', 'EST','saurabh.gupta1@hcl.com','saurabh22045@gmail.com', 'Spain', 0)";

		String startDate = slot_start_date_time[0];
		String currentSlotStartDate, currentSlotStartTime;
		String currentDate;
		currentDate = startDate;
		String query2 = "";

		int totalSlotsPerDay = ((DAY_END_TIME - DAY_START_TIME) * 60) / Integer.parseInt(slot_duration);
		// int totalSlots = (slots_end - slot_start)/slot_duration;
		// String[] slot_start_date_time = null, slot_end_date_time = null;

		int i = 0;
		int j = 0;
		currentSlotStartDate = slot_start_date_time[0];
		currentSlotStartTime = slot_start_date_time[1];
		// LocalDateTime dateTime = LocalDateTime.parse(slot_start);
		String slot_start_tmp = slot_start;

		while (i <= no_of_days) {
			j = 0;
			while (j < totalSlotsPerDay) {
				if (!StringUtils.contains(holidays, currentDate)) {
					if (dayType(currentDate).equalsIgnoreCase(DAY_TYPE_WEEKDAY)
							|| (dayType(currentDate).equalsIgnoreCase(DAY_TYPE_WEEKEND) && include_weekends)) {
						LocalDateTime dateTime = LocalDateTime.parse(slot_start_tmp);
						slot_end = dateTime.plusMinutes(Long.parseLong(slot_duration)).toString();

						if (StringUtils.isBlank(attendee_email_list)) {
							attendee_email_list = it_email_list;
						}
						query2 = "insert into slots(slot_start,slot_end, time_zone, it_email_list,attendee_email_list,region,is_booked) values('"
								+ slot_start_tmp + "','" + slot_end + "', '" + time_zone + "','" + it_email_list + "','"
								+ attendee_email_list + "', '" + region + "', " + BooleanUtils.toInteger(is_booked)
								+ ")";
						// jdbc.execute(query2);

						final String SQL = query2;
						KeyHolder keyHolder = new GeneratedKeyHolder();
						jdbc.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
							return ps;
						}, keyHolder);

						int requestId = keyHolder.getKey().intValue();

						System.err.print("Key--->" + requestId);
						SlotModel currentSlot = findSingleSlot(requestId);

						if (Boolean.TRUE.toString().equalsIgnoreCase(create_event))
							bookSlotAndCreateEvent(currentSlot.getSlot_start(),
									currentSlot.getSlot_end(), attendee_email_list, requestId, time_zone,
									Boolean.toString(is_booked));
					}
				}
				j++;
				slot_start_tmp = slot_end;
			}
			LocalDate dateTime = LocalDate.parse(slot_start_date_time[0]);
			currentDate = dateTime.plusDays(i + 1).toString();
			slot_start_tmp = currentDate + "T" + DAY_SLOT_START_TIME;
			i++;
		}

		return "slots created Successfully";
	}

	public String dayType(String slot_start_date) {
		String dayType;
		LocalDate date = LocalDate.parse(slot_start_date);
		if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
			dayType = DAY_TYPE_WEEKEND;
		} else {
			dayType = DAY_TYPE_WEEKDAY;
		}
		return dayType;
	}

	/**
	 * @param slotId
	 * @param attendee_email_list
	 * @param is_booked
	 * @param eventLink
	 * @return
	 */
	// UPDATE springbootdb.slots SET
	// attendee_email_list='saurabh.gupta@hcl.com',is_booked=false,event_link='https://www.google.com/calendar/event?eid=N2JxZnNrNHEycWZmcDRicmViOHIzdWJxZDhfMjAyMjA4MTFUMDYzMDMwWiBzYXVyYWJoMjIwNEBt'
	// WHERE id = 1;
	@RequestMapping("/updateslot")
	@ResponseBody
	public String updateSlot(int slotId, String attendee_email_list, String is_booked, String eventLink) {

		int isBooked = BooleanUtils.toInteger(Boolean.parseBoolean(is_booked));

		String query = "UPDATE springbootdb.slots SET attendee_email_list=" + "'" + attendee_email_list + "'"
				+ ",is_booked=" + isBooked + ",event_link=" + "'" + eventLink + "'" + " WHERE id = " + slotId + ";";
		jdbc.execute(query);
		return "slot updated Successfully";
	}

	public List<SlotModel> findAllSlots(String[] filterArr) {
//SELECT * FROM springbootdb.slots where slot_start>='2022-08-31 11:00:00' and slot_end <= '2022-09-01 14:00:00';
		String sql = "SELECT * FROM slots";

		if (StringUtils.isNotBlank(filterArr[0]))
			sql = sql + " where slot_start>='" + filterArr[0] + "'";
		if (StringUtils.isNotBlank(filterArr[0]) && StringUtils.isNotBlank(filterArr[1]))
			sql = sql + " and slot_end<='" + filterArr[1] + "'";
		if (StringUtils.isBlank(filterArr[0]) && StringUtils.isNotBlank(filterArr[1]))
			sql = sql + " where slot_end<='" + filterArr[1] + "'";

		List<SlotModel> slots = new ArrayList<>();

		List<Map<String, Object>> rows = jdbc.queryForList(sql);
		String tz_Tmp, region_tmp;
		boolean isBookTmp;
		for (Map row : rows) {

			SlotModel obj = new SlotModel();
			obj.setId(((Long) row.get("id")));
			obj.setSlot_start(((Timestamp) row.get("slot_start")).toString());
			obj.setSlot_end(((Timestamp) row.get("slot_end")).toString());
			tz_Tmp = ((String) row.get("time_zone")).toString();
			obj.setTime_zone(tz_Tmp);
			obj.setIt_email_list(((String) row.get("it_email_list")).toString());
			obj.setAttendee_email_list(((String) row.get("attendee_email_list")).toString());
			region_tmp = ((String) row.get("region")).toString();
			obj.setRegion(region_tmp);
			isBookTmp = ((boolean) row.get("is_booked"));
			obj.setIs_booked(isBookTmp);

			if (StringUtils.isNotBlank(filterArr[2]) && StringUtils.equalsIgnoreCase(filterArr[2].trim(), "GMT")) {
				System.err.println("GMT");
			}
			if (StringUtils.isNotBlank(filterArr[2])){ // time_zone, Dont add to slots list, in case not matching filter criteria
					if(!StringUtils.equalsIgnoreCase(filterArr[2].trim(), tz_Tmp.trim()) && !StringUtils.equalsIgnoreCase(filterArr[2].trim(), "ANY") && !StringUtils.equalsIgnoreCase(filterArr[2].trim(), "ALL"))
					continue;
			}

			if (StringUtils.isNotBlank(filterArr[3])) { //region, Dont add to slots list, in case not matching filter criteria
					if(!StringUtils.equalsIgnoreCase(filterArr[3].trim(), region_tmp.trim()) && !StringUtils.equalsIgnoreCase(filterArr[3].trim(), "ANY") && !StringUtils.equalsIgnoreCase(filterArr[3].trim(), "ALL"))
				continue;
			}
			
			if (StringUtils.isNotBlank(filterArr[4]) && BooleanUtils.toBoolean(filterArr[4].trim()) == isBookTmp) //isAvailable
				continue;

			slots.add(obj);
		}

		return slots;
	}

	public SlotModel findSingleSlot(int requestId) {

		String sql = "SELECT * FROM slots where id=" + requestId;

		List<SlotModel> slots = new ArrayList<>();

		List<Map<String, Object>> rows = jdbc.queryForList(sql);

		for (Map row : rows) {
			SlotModel obj = new SlotModel();
			obj.setId(((Long) row.get("id")));

			obj.setSlot_start(((Timestamp) row.get("slot_start")).toString());
			obj.setSlot_end(((Timestamp) row.get("slot_end")).toString());
			obj.setTime_zone(((String) row.get("time_zone")).toString());
			obj.setIt_email_list(((String) row.get("it_email_list")).toString());
			obj.setAttendee_email_list(((String) row.get("attendee_email_list")).toString());
			obj.setRegion(((String) row.get("region")).toString());

			obj.setIs_booked(((boolean) row.get("is_booked")));

			slots.add(obj);
		}

		return slots.get(0);
	}
	
	public LinkedHashSet<String> findTimeZones() {

		String sql = "SELECT time_zone FROM slots";

		LinkedHashSet<String> timeZones = new LinkedHashSet<>();
		timeZones.add("ANY");

		List<Map<String, Object>> rows = jdbc.queryForList(sql);
		
		for (Map row : rows) {
			timeZones.add(row.get("time_zone").toString());
			//System.out.print(row);
		}

		return timeZones;
	}
	
	public LinkedHashSet<String> findRegions() {

		String sql = "SELECT region FROM slots";

		LinkedHashSet<String> regions = new LinkedHashSet<>();
		regions.add("ANY");


		List<Map<String, Object>> rows = jdbc.queryForList(sql);
		
		for (Map row : rows) {
			regions.add(row.get("region").toString());
			//System.out.print(row);
		}

		return regions;
	}

	public String findPassword(String user) {
		String password = "";
		String sql = "SELECT password FROM user where name=" + "'" + user + "'";

		List<SlotModel> slots = new ArrayList<>();

		List<Map<String, Object>> rows = jdbc.queryForList(sql);

		for (Map row : rows) {
			password = (String) row.get("password");

		}

		return password;
	}



	/**
	 * @param startDateTime1
	 * @param endDateTime1
	 * @param timeZone
	 * @param emailArr
	 * @param requestId
	 * @return
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */

	public String createEvent(String startDateTimeStr, String endDateTimeStr, String timeZone, String[] emailArr, int requestId) throws IOException, GeneralSecurityException {
	    final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
	    Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
	        .setApplicationName(APPLICATION_NAME).build();

	    Event event = new Event().setSummary(SUMMARY).setLocation(LOCATION).setDescription(DESCRIPTION);

	    DateTime startDateTime = new DateTime(startDateTimeStr);
	    EventDateTime start = new EventDateTime().setDateTime(startDateTime).setTimeZone(timeZone);
	    event.setStart(start);

	    DateTime endDateTime = new DateTime(endDateTimeStr);
	    EventDateTime end = new EventDateTime().setDateTime(endDateTime).setTimeZone(timeZone);
	    event.setEnd(end);

	    String[] recurrence = new String[] { RECURRENTCE_RULE };
	    event.setRecurrence(Arrays.asList(recurrence));

	    ArrayList<EventAttendee> emailList = new ArrayList<EventAttendee>();
	    for (String emailIdAttendee : emailArr) {
	        emailList.add(new EventAttendee().setEmail(emailIdAttendee));
	    }
	    event.setAttendees(emailList);

	    // Set up email notifications for the event attendees to be sent immediately
	    EventReminder[] reminderOverrides = new EventReminder[] {
	        new EventReminder().setMethod("email").setMinutes(0) // 0 minutes for immediate notification
	    };

	    Event.Reminders reminders = new Event.Reminders()
	        .setUseDefault(false)
	        .setOverrides(Arrays.asList(reminderOverrides));
	    event.setReminders(reminders);

	    // conferenceData.
	    ConferenceData conferenceData = new com.google.api.services.calendar.model.ConferenceData();
	    CreateConferenceRequest conferenceRequest = new CreateConferenceRequest();
	    ConferenceSolutionKey kk = new ConferenceSolutionKey().setType("hangoutsMeet");
	    ConferenceSolution conferenceSolution = new ConferenceSolution();
	    conferenceSolution.setKey(kk);

	    conferenceRequest.setConferenceSolutionKey(kk);
	    conferenceRequest.setRequestId(requestId + "");
	    conferenceData.setCreateRequest(conferenceRequest);

	    event.setConferenceData(conferenceData);

	    event = service.events().insert(calendarId, event).setConferenceDataVersion(1).execute();

	    System.out.printf("Event created: %s\n", event.getHtmlLink());
	    System.err.println("Google Meet created: %s\n" + event.getHangoutLink());
	    
	    sendEmailWithEventDetails(emailArr, event);

	    return event.getHtmlLink();
	}

	private void sendEmailWithEventDetails(String[] emailArr, Event event)
	        throws IOException, GeneralSecurityException {
	    Gmail gmailService = getGmailService(); // Implement this method to obtain Gmail API service

	    // Compose the email to send to event attendees
	    String emailMessage = "<html><body>";
	    emailMessage += "<h2>You are invited to the event</h2>";
	    emailMessage += "<p><b>Organizer:</b> " + "SKG" + "</p>";
	    emailMessage += "<p><b>Summary:</b> " + event.getSummary() + "</p>";
	    emailMessage += "<p><b>Location:</b> " + event.getLocation() + "</p>";
	    emailMessage += "<p><b>Description:</b> " + event.getDescription() + "</p>";
	    emailMessage += "<p><b>Start Time:</b> " + formatDate(event.getStart().getDateTime()) + "</p>";
	    emailMessage += "<p><b>End Time:</b> " + formatDate(event.getEnd().getDateTime()) + "</p>";
	    emailMessage += "<p>See the attached calendar event for more details.</p>";
	    emailMessage += "<p><b>Google Meet details:</b>" + event.getHangoutLink() + "</p>";

	    emailMessage += "</body></html>";

	    MimeMessage emailMimeMessage = createMimeMessage(senderEmail, emailArr, event.getSummary(), emailMessage);

	    // Attach the calendar event
	    byte[] icsFile = generateCalendarEventFile(event);
	    try {
	        // emailMimeMessage.setContent(emailMessage, "text/html");
	        emailMimeMessage = attachCalendarEvent(emailMimeMessage, icsFile);
	    } catch (MessagingException e1) {
	        e1.printStackTrace();
	    }

	    // Send the email
	    try {
	        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	        emailMimeMessage.writeTo(buffer);

	        Message message = new Message();
	        message.setRaw(Base64.getUrlEncoder().encodeToString(buffer.toByteArray()));

	        getGmailService().users().messages().send("me", message).execute();
	    } catch (MessagingException | IOException e) {
	        e.printStackTrace();
	    }
	}

	private static byte[] generateCalendarEventFile(Event event) throws IOException {
        ICalendar ical = new ICalendar();

        VEvent vEvent = new VEvent();

     // Convert DateTime objects to java.util.Date
     Date startDate = new Date(event.getStart().getDateTime().getValue());
     Date endDate = new Date(event.getEnd().getDateTime().getValue());

     vEvent.setDateStart(new DateStart(startDate));
     vEvent.setDateEnd(new DateEnd(endDate));

     com.google.api.services.calendar.model.Event.Organizer organizer = event.getOrganizer();
     if (organizer != null) {
         String organizerEmail = organizer.getEmail();
         vEvent.setOrganizer(organizerEmail);
     } else {
         // Handle the case where the organizer is not available
     }
     
     vEvent.setSummary(event.getSummary() + " Slot");

     vEvent.addImage(new Image("image/jpg","EasyConnect.jpg"));

     vEvent.setClassification(Classification.PUBLIC);
     vEvent.setDescription("Hello");

     

  // Add HTML link and Hangout link as custom properties
     String htmlLink = event.getHtmlLink();
     String hangoutLink = event.getHangoutLink();

     // Set location if available
     if (event.getLocation() != null) {
    	    String locationWithBoldText = "<b>Location:</b> <a href=\"" + event.getHangoutLink() + "\" style=\"text-decoration: none; color: #007bff;\">Google Meet Link</a>";
    	    vEvent.setLocation("<b>"+event.getHangoutLink()+"</b>");
    }
 	
     // Add the event to an iCalendar

        ical.addEvent(vEvent);

        File tempFile = File.createTempFile("event-", ".ics");
        Biweekly.write(ical).go(tempFile);

        byte[] icsBytes = new byte[(int) tempFile.length()];
        try (FileInputStream fileInputStream = new FileInputStream(tempFile)) {
            fileInputStream.read(icsBytes);
        }

        return icsBytes;
    }
	
	
	// Helper method to format DateTime to a readable format
	private String formatDate(DateTime dateTime) {
	    if (dateTime != null) {
	        Date date = new Date(dateTime.getValue());
	        DateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
	        return dateFormat.format(date);
	    }
	    return "";
	}


	// Helper method to attach the calendar event to the email
	private MimeMessage attachCalendarEvent(MimeMessage mimeMessage, byte[] icsFile) throws MessagingException {
	    MimeBodyPart attachmentPart = new MimeBodyPart();
	    ByteArrayDataSource dataSource = new ByteArrayDataSource(icsFile, "text/calendar;method=REQUEST;name=event.ics");
	    attachmentPart.setDataHandler(new DataHandler(dataSource));
	    attachmentPart.setFileName("event.ics");

	    MimeMultipart multipart = new MimeMultipart();
	    multipart.addBodyPart(attachmentPart);

	    mimeMessage.setContent(multipart);
	    return mimeMessage;
	}
	
	// Obtain Gmail API service with OAuth 2.0 credentials
	public Gmail getGmailService() throws IOException, GeneralSecurityException {
	    final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
	    return new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
	        .setApplicationName(APPLICATION_NAME)
	        .build();
	}
	

	private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
	    // Load client secrets.
	    InputStream in = SpringBootJdbcController.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
	    if (in == null) {
	        throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
	    }
	    GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

	    // Modify the existing scopes to include "https://www.googleapis.com/auth/gmail.compose"
	    List<String> updatedScopes = new ArrayList<>(SCOPES);
	    updatedScopes.add(GmailScopes.GMAIL_COMPOSE);

	    // Build flow and trigger user authorization request with the updated scopes.
	    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
	            clientSecrets, updatedScopes) // Use the updated scopes
	            .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
	            .setAccessType("offline").build();
	    LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
	    Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
	    // Returns an authorized Credential object with the updated scope.
	    return credential;
	}

	
	
	/**
	 * @param slot_start
	 * @param slots_end
	 * @throws ParseException
	 */
	public void getSlotDurationDays(String slot_start, String slots_end) throws ParseException {
		slot_start_date_time = StringUtils.split(slot_start, 'T');
		slot_end_date_time = StringUtils.split(slots_end, 'T');

		SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd", Locale.ENGLISH);
		Date firstDate = sdf.parse(slot_start_date_time[0]);
		Date secondDate = sdf.parse(slot_end_date_time[0]);

		long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
		no_of_days = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

		System.err.println(no_of_days);
	}

	private ResponseEntity bookSlotAndCreateEvent( String startDateTime,  String endDateTime,
			 String emailId,  int requestId,  String timeZone,
			 String is_booked) throws IOException, GeneralSecurityException {
		// System.err.println("sku for adding to estimate----------->" + sku);
		// redirAttrs.addFlashAttribute("message", "This is message from flash");
		// Sample startDateTime-> 2022-08-12 19:30:00.0->2022-08-12T20:30:00.0
		String startDateTimeStr = StringUtils.replace(startDateTime, " ", "T");// + "-00:00";
		String endDateTimeStr = StringUtils.replace(endDateTime, " ", "T");// + "-00:00";

		String[] emailArr = StringUtils.stripAll(StringUtils.split(emailId, ","));
//2022-08-12T20:30:00.0
//2022-08-24T09:00:00.0
		String eventLink = createEvent(startDateTimeStr, endDateTimeStr, timeZone, emailArr,
				requestId);
		System.err.println(emailId);

		updateSlot(requestId, emailId, is_booked, eventLink);
		// return " Success ";
		return new ResponseEntity(HttpStatus.OK);

	}

	public MimeMessage createMimeMessage(String senderEmail, String[] recipientEmails, String subject, String messageText)  {
        // Set up the properties for the JavaMail session
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "false"); // Set to true if authentication is required
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com"); // Replace with your SMTP server
        properties.put("mail.smtp.port", "587"); // Replace with the appropriate port for your SMTP server

        // Create a Session with the properties
        Session session = Session.getInstance(properties, null);

        // Create a new MimeMessage
        MimeMessage mimeMessage = new MimeMessage(session);

        try {
            // Set the sender (from) address
            mimeMessage.setFrom(new InternetAddress(senderEmail));

            // Set the recipient (to) addresses
            for (String recipientEmail : recipientEmails) {
                mimeMessage.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(recipientEmail));
            }

            // Set the subject of the email
            mimeMessage.setSubject(subject);

            // Set the content of the email (text)
            mimeMessage.setText(messageText);

            // You can also set the content of the email as HTML if needed:
            // mimeMessage.setContent("<html><body>" + messageText + "</body></html>", "text/html");

            return mimeMessage;
        } catch (Exception e) {
            // Handle or log any exceptions related to message creation
            e.printStackTrace();
        }
		return mimeMessage;
    }

	public String createPatchingRequest(PatchingRequest patchingRequest) {
		try {
			sendEmailWithPatchingDetails(patchingRequest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private void sendEmailWithPatchingDetails(PatchingRequest patchingRequest)
			throws IOException, GeneralSecurityException {
		
		String sql2 = "SELECT asset_id FROM springbootdb.EMPLOYEE_ASSET WHERE roll_no = ?";
		String assetId = jdbc.queryForObject(sql2, String.class, patchingRequest.getEmpId());
		patchingRequest.setHostName(assetId);
		
		
		String sql = "SELECT DISTINCT e_mail_id FROM Employee WHERE roll_no="+patchingRequest.getEmpId()+";";

		List<Map<String, Object>> rows = jdbc.queryForList(sql);
		ArrayList<String> emailList = new ArrayList<String>();
		emailList.add(DEFAULT_RECIPIENT_EMAIL);
		int i=0;
		
		for (Map row : rows) {

			emailList.add(row.get("e_mail_id").toString());
		
		}
		
		String[] emailArr = emailList.toArray(new String[emailList.size()]);		
		System.err.println(emailArr);

	
		Gmail gmailService = getGmailService(); // Implement this method to obtain Gmail API service

		String emailMessage = "<html><body>";
		emailMessage += "<h2>Your automated patching request is created</h2>";
		emailMessage += "<p><b>Hostname:</b> " + patchingRequest.getHostName() + "</p>";
		emailMessage += "<p><b>Patch Name:</b> " + patchingRequest.getPatchName() + "</p>";
		emailMessage += "<p><b>Selected Time:</b> " + patchingRequest.getPreferredTime() + "</p>";
		emailMessage += "</body></html>";

		MimeMessage emailMimeMessage = createMimeMessage(senderEmail, emailArr, emailPatchingRequestSubject, emailMessage);
		try {
			emailMimeMessage.setContent(emailMessage, "text/html");
		} catch (MessagingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	    // Attach the calendar event
	   
	    // Send the email
	    try {
	        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	        emailMimeMessage.writeTo(buffer);

	        Message message = new Message();
	        message.setRaw(Base64.getUrlEncoder().encodeToString(buffer.toByteArray()));

	        getGmailService().users().messages().send("me", message).execute();
	    } catch (MessagingException | IOException e) {
	        e.printStackTrace();
	    }
	}

}