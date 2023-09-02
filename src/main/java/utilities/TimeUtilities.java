package utilities;

import org.joda.time.Instant;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.TimeZone;

public final class TimeUtilities 
{

	private TimeUtilities()
	{

	}

	private static final String DATEFORMAT = "MM-dd-yyyy HH:mm:ss";

	public static String getTime(final String timezone, final int offsetInHours)
	{
		final String format = "HH:mm";
		return timeWrapper(format, timezone, offsetInHours);
	}

	public static String getTimeWithSeconds(final String timezone, final int offsetInHours)
	{
		final String format = "HH:mm:ss";
		return timeWrapper(format, timezone, offsetInHours);
	}

	public static String getTimeWithSecondsAndMiliSeconds(final String timezone, final int offsetInHours)
	{
		final String format = "HH:mm:ss.SS";
		return timeWrapper(format, timezone, offsetInHours);
	}

	public static String getTimeWithSecondsAndMiliSecondsAndTimeZone(final String timezone, final int offsetInHours)
	{
		final String format = "HH:mm:ss.SS Z";
		return timeWrapper(format, timezone, offsetInHours);
	}

	public static String getDate(final String timezone, final int offsetInHours)
	{
		final String format = "MM-dd-yyyy";
		return timeWrapper(format, timezone, offsetInHours);
	}

	public static String getDateWithTime(final String timezone, final int offsetInHours)
	{
		final String format = "MM-dd-yyyy HH:mm";
		return timeWrapper(format, timezone, offsetInHours);
	}

	public static String getCompleteDateWithTimeAndTimeZone(final String timezone, final int offsetInHours)
	{
		final String format = "MM-dd-yyyy HH:mm:ss.SS Z";
		return timeWrapper(format, timezone, offsetInHours);
	}

	public static LocalTime getLocalTimeHHmm(final String timezone, final int offsetInSeconds) 
	{
		final String format = "HH:mm";
		return LocalTime.parse(timeWrapperOffsetInSeconds(format, timezone, offsetInSeconds));
	}

	public static LocalTime getLocalTimeHHmmss(final String timezone, final int offsetInSeconds) 
	{
		final String format = "HH:mm:ss";
		return LocalTime.parse(timeWrapperOffsetInSeconds(format, timezone, offsetInSeconds));
	}

	public static LocalTime getLocalTimeHHmmssSS(final String timezone, final int offsetInSeconds) 
	{
		final String format = "HH:mm:ss.SS";
		return LocalTime.parse(timeWrapperOffsetInSeconds(format, timezone, offsetInSeconds));
	}

	public static LocalTime getCompleteLocalDateWithTimeAndTimeZone(final String timezone, final int offsetInSeconds) 
	{
		final String format = "MM-dd-yyyy HH:mm:ss.SS Z";
		return LocalTime.parse(timeWrapperOffsetInSeconds(format, timezone, offsetInSeconds));
	}

	public static LocalDateTime getEasternDateTimeWithOffset(final int offsetInSeconds) 
	{
		return LocalDateTime.parse(
				timeWrapperOffsetInSeconds("DATEFORMAT", "US/Eastern", offsetInSeconds), 
				DateTimeFormatter.ofPattern(DATEFORMAT));
	}

	public static LocalDateTime getCentralDateTimeWithOffset(final int offsetInSeconds) 
	{
		return LocalDateTime.parse(
				timeWrapperOffsetInSeconds("DATEFORMAT", "US/Central", offsetInSeconds), 
				DateTimeFormatter.ofPattern(DATEFORMAT));
	}

	public static LocalDateTime getMountainDateTimeWithOffset(final int offsetInSeconds) 
	{
		return LocalDateTime.parse(
				timeWrapperOffsetInSeconds("DATEFORMAT", "US/Mountain", offsetInSeconds), 
				DateTimeFormatter.ofPattern(DATEFORMAT));
	}

	public static LocalDateTime getPacificDateTimeWithOffset(final int offsetInSeconds) 
	{
		return LocalDateTime.parse(
				timeWrapperOffsetInSeconds("DATEFORMAT", "US/Pacific", offsetInSeconds), 
				DateTimeFormatter.ofPattern(DATEFORMAT));
	}

	/*
	 * This private method return time based on input format
	 */
	private static String timeWrapper(final String dateformat, final String timezone, final int offsetInHours )
	{
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateformat);
		final TimeZone timezoneObject = TimeZone.getTimeZone(timezone);
		final ZoneId zone = timezoneObject.toZoneId();
		final Object curTime = ZonedDateTime.now().minusHours(Long.valueOf((long) - 1 * offsetInHours)).withZoneSameInstant(zone);
		return formatter.format((TemporalAccessor) curTime);
	}

	/*
	 * This private method return time based on input format
	 */
	private static String timeWrapper(final String dateformat, final String timezone, final int offsetInDays, final int offsetInMinutes)
	{
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateformat);
		final TimeZone timezoneObject = TimeZone.getTimeZone(timezone);
		final ZoneId zone = timezoneObject.toZoneId();
		final Object curTime = ZonedDateTime.now()
				.minusDays(Long.valueOf((long) - 1 * offsetInDays))
				.minusMinutes(Long.valueOf((long) - 1 * offsetInMinutes))
				.withZoneSameInstant(zone);
		return formatter.format((TemporalAccessor) curTime);
	}
	/*
	 * This private method return time based on input format
	 */
	private static String timeWrapperOffsetInSeconds(final String dateformat, final String timezone, final int offsetInSeconds )
	{
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateformat);
		final TimeZone timezoneObject = TimeZone.getTimeZone(timezone);
		final ZoneId zone = timezoneObject.toZoneId();
		final Object curTime = ZonedDateTime.now().minusSeconds(Long.valueOf((long) - 1 * offsetInSeconds)).withZoneSameInstant(zone);
		return formatter.format((TemporalAccessor) curTime);
	}

	/*
	 * This private method return time based on input format
	 */
	private static String timeWrapperOffsetInDays(final String dateformat, final String timezone, final int offsetInDays )
	{
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateformat);
		final TimeZone timezoneObject = TimeZone.getTimeZone(timezone);
		final ZoneId zone = timezoneObject.toZoneId();
		final Object curTime = ZonedDateTime.now().minusDays(Long.valueOf((long) - 1 * offsetInDays)).withZoneSameInstant(zone);
		return formatter.format((TemporalAccessor) curTime);
	}

	public static String getCurrentTimeInMilliSeconds() {
		//TO-DO
		return "";
	}

	public static String convertCurrentTimeZone(
			final String inputTime, 
			final String inputTimeZone, 
			final String outputTimeZone, 
			final int offsetInHours) throws ParseException 
	{
		final DateFormat inputDateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm");
		inputDateFormat.setTimeZone(TimeZone.getTimeZone(inputTimeZone));
		final DateFormat outputDateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm");
		outputDateFormat.setTimeZone(TimeZone.getTimeZone(outputTimeZone));
		final Object inputDateObject = inputDateFormat.parse(inputTime);
		final String outputDate = outputDateFormat.format(inputDateObject);
		return outputDate;
	}

	public static long getCurrentTime() {
		return Instant.now().getMillis();
	}

	public static String getCompleteDateWithTimeAndTimezone(final String timezone, final int OffsetInDays, final int offsetInMinutes)
	{
		final String format = "MM/dd/yyyy, HH:mm:ss a";
		return timeWrapper(format, timezone, OffsetInDays, offsetInMinutes);
	}

	public static void waitInSeconds(final int secondsToWait) 
	{
		try 
		{
			final long longSecondsToWait = secondsToWait;
			final long finalSecondsToWait = (longSecondsToWait * 1000);
			Thread.sleep(finalSecondsToWait);
		} 
		catch (final InterruptedException e) 
		{
			LoggerUtilities.addLog(e, "Exception while wating....");
		}
	}
}
