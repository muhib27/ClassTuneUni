package com.classtune.classtuneuni.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;


import com.classtune.classtuneuni.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AppUtility {

	public static final String DATE_FORMAT_SERVER = "yyyy-MM-dd";
	public static final String DATE_FORMAT_APP = "dd MMM yyyy";
	public static final String DATE_FORMAT_APP_ = "dd MMM, yyyy";
	public static final String DATE_FORMAT_APP_M_Y = "MMM, yyyy";

	public static final String DATE_FORMAT_D_M = "dd,MMM";
	public static final String DATE_FORMAT_d_m = "dd MMM";
	public static final String DATE_FORMAT_FACEBOOK = "MM/dd/yyyy";

	private static String[] suffix = new String[] { "", "k", "m", "b", "t" };
	private static int MAX_LENGTH = 4;
	private static String[] month = new String[]{"Jan", "Feb", "March", "April", "May", "June", "July", "Aug", "Sep", "Oct", "Nov", "Dec"};

	private static char[] c = new char[] { 'k', 'm', 'b', 't' };

//	/**
//	 * Recursive implementation, invokes itself for each factor of a thousand,
//	 * increasing the class on each invokation.
//	 *
//	 * @param n
//	 *            the number to format
//	 * @param iteration
//	 *            in fact this is the class from the array c
//	 * @return a String representing the number n formatted in a cool looking
//	 *         way.
//	 */

	public static int convertDipToPixels(Context context, float dips) {
		return (int) (dips * context.getResources().getDisplayMetrics().density + 0.5f);
	}

//	public static String getCatNameById(Context con, int id) {
//		if (id == 59)
//			return "Education Changes Life";
//		if( id == 74)
//			return "Summit Salutes Nation Builders";
//		String[] myMenuArrayText = con.getResources().getStringArray(
//				R.array.free_menus_text);
//		/*
//		 * String[] myMenuArrayImages = con.getResources().getStringArray(
//		 * R.array.free_menus_images);
//		 */
//		String[] myMenuArrayIds = con.getResources().getStringArray(
//				R.array.free_menus_id);
//		String name = "";
//		for (int i = 0; i < myMenuArrayIds.length; i++) {
//			if (id == Integer.parseInt(myMenuArrayIds[i])) {
//				name = myMenuArrayText[i];
//				break;
//			}
//		}
//		return name;
//	}

	public static String coolFormat(double n, int iteration) {
		double d = ((long) n / 100) / 10.0;
		boolean isRound = (d * 10) % 10 == 0;// true if the decimal part is
												// equal to 0 (then it's trimmed
												// anyway)
		return (d < 1000 ? ((d > 99.9 || isRound || (!isRound && d > 9.99) ? (int) d * 10 / 10
				: d + "")
				+ "" + c[iteration])
				: coolFormat(d, iteration + 1));

	}

	/*
	 * public static String format(double number) { String r = new
	 * DecimalFormat("##0E0").format(number); r = r.replaceAll("E[0-9]",
	 * suffix[Character.getNumericValue(r.charAt(r.length() - 1)) / 3]); while
	 * (r.length() > MAX_LENGTH || r.matches("[0-9]+\\.[a-z]")) { r =
	 * r.substring(0, r.length() - 2) + r.substring(r.length() - 1); } return r;
	 * }
	 */

	public static String getMonth(String m){
		if (m!=null)
		return month[Integer.parseInt(m)-1];
		else
			return "";
	}

	public static int getProductImageWidth(Context context) {
		int density = context.getResources().getDisplayMetrics().densityDpi;
		int imageWidth = 0;
		switch (density) {
		case DisplayMetrics.DENSITY_LOW:
			imageWidth = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 100, context.getResources()
							.getDisplayMetrics());
			break;
		case DisplayMetrics.DENSITY_MEDIUM:
			imageWidth = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 100, context.getResources()
							.getDisplayMetrics());
			break;
		case DisplayMetrics.DENSITY_HIGH:
			imageWidth = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 150, context.getResources()
							.getDisplayMetrics());
			break;
		case DisplayMetrics.DENSITY_XHIGH:
			imageWidth = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 150, context.getResources()
							.getDisplayMetrics());
			break;
		default:
			imageWidth = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 150, context.getResources()
							.getDisplayMetrics());
			break;
		}

		return imageWidth;
	}

	public static String getMonthFullName(int monthNumber) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, monthNumber);

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM");
		simpleDateFormat.setCalendar(calendar);
		String monthName = simpleDateFormat.format(calendar.getTime());

		return monthName;
	}





	public static int getColorFromString(String name)
	{
		int resId = 0;
		name = name.split("\\.")[0];
		for(int i=0;i<R.color.class.getFields().length;i++) {
			if(name.equalsIgnoreCase(R.color.class.getFields()[i].getName())) {
				try {
					resId = R.color.class.getFields()[i].getInt(null);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				break;
			}
		}

		return resId;
	}





	public static int getMonthNumberFromName(String month) {
        Map<String, Integer> hm = new HashMap<String, Integer>();

		hm.put("January", 1);
		hm.put("February", 2);
		hm.put("March", 3);
		hm.put("April", 4);
		hm.put("May", 5);
		hm.put("June", 6);
		hm.put("July", 7);
		hm.put("August", 8);
		hm.put("September", 9);
		hm.put("October", 10);
		hm.put("November", 11);
		hm.put("December", 12);

		return hm.get(month);
	}

	public static String getCurrentYear() {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		return year + "";
	}

	public static String getCurrentDate(String format) {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat df_output = new SimpleDateFormat(format,
				java.util.Locale.getDefault());
		return df_output.format(c.getTime());
	}

	public static String getFormatedDateString(String format, Calendar c) {
		SimpleDateFormat df_output = new SimpleDateFormat(format,
				java.util.Locale.getDefault());
		return df_output.format(c.getTime());
	}

	public static String getDateString(String str, String toFormat,
                                       String fromFormat) {
		Date parsed = null;
		String outputDate = "";

		SimpleDateFormat df_input = new SimpleDateFormat(fromFormat,
				java.util.Locale.getDefault());
		SimpleDateFormat df_output = new SimpleDateFormat(toFormat,
				java.util.Locale.getDefault());

		try {
			parsed = df_input.parse(str);
			outputDate = df_output.format(parsed);

		} catch (ParseException e) {
			Log.e("Bal", "ParseException - dateFormat");
		}
		Log.e("Formated Date", outputDate);
		return outputDate;
	}

	public static String getDate(long milliSeconds) {
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_SERVER);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(milliSeconds);
		return formatter.format(calendar.getTime());
	}



	public static int getImageViewerImageHeight(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		int imageHeight;
		if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
			imageHeight = (int) Math.floor((double) display.getHeight() * .4);
		else
			imageHeight = (int) Math.floor((double) display.getWidth() * .4);
		return imageHeight;

	}


    public static  IDatePickerCancel listenerDatePickerCancel;

	public static void showDateTimePicker(final String key, final String title,
                                          final String description, final Context context) {
		CustomDateTimePicker custom = new CustomDateTimePicker(context,
				new CustomDateTimePicker.ICustomDateTimeListener() {

					@Override
					public void onCancel() {

                        listenerDatePickerCancel.onCancelCalled();

					}

					@Override
					public void onSet(Dialog dialog, Calendar calendarSelected,
                                      Date dateSelected, int year, String monthFullName,
                                      String monthShortName, int monthNumber, int date,
                                      String weekDayFullName, String weekDayShortName,
                                      int hour24, int hour12, int min, int sec,
                                      String AM_PM) {
						// TODO Auto-generated method stub
						// TODO Auto-generated method stub

						SimpleDateFormat format = new SimpleDateFormat(
								"yyyy-MM-dd hh:mm:ss");

						String dateStr = format.format(dateSelected);

//						ReminderHelper.getInstance().setReminder(key, title,
//								description, dateStr, context);
//						Log.e("Date Selected", dateStr);
					}
				});
		/**
		 * Pass Directly current time format it will return AM and PM if you set
		 * false
		 */
		custom.set24HourFormat(false);
		/**
		 * Pass Directly current data and time to show when it pop up
		 */
		custom.setDate(Calendar.getInstance());
		custom.showDialog();
	}



    public interface IDatePickerCancel
    {
        public void onCancelCalled();
    }



	/**
	 * This method converts dp unit to equivalent pixels, depending on device
	 * density.
	 * 
	 * @param dp
	 *            A value in dp (density independent pixels) unit. Which we need
	 *            to convert into pixels
	 * @param context
	 *            Context to get resources and device specific display metrics
	 * @return A float value to represent px equivalent to dp depending on
	 *         device density
	 */
	public static float convertDpToPixel(float dp, Context context) {
		Resources resources = context.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		float px = dp * (metrics.densityDpi / 160f);
		return px;
	}

	/**
	 * This method converts device specific pixels to density independent
	 * pixels.
	 * 
	 * @param px
	 *            A value in px (pixels) unit. Which we need to convert into db
	 *
	 * @param context
	 *            Context to get resources and device specific display metrics
	 * @return A float value to represent dp equivalent to px value
	 */
	public static float convertPixelsToDp(float px, Context context) {
		Resources resources = context.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		float dp = px / (metrics.densityDpi / 160f);
		return dp;
	}

	public static float getDeviceIndependentDpFromPixel(Context context,
                                                        float value) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value,
				context.getResources().getDisplayMetrics());
	}


	public static String getDayFromDate(String date){
//		String input_date="01/08/2012";
		String finalDay = "";
		SimpleDateFormat format1=new SimpleDateFormat(DATE_FORMAT_SERVER);
		Date dt1= null;
		try {
			dt1 = format1.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		DateFormat format2=new SimpleDateFormat("EEEE");
		finalDay = format2.format(dt1);
		return finalDay;
	}

	public static String getTimeDifference(String current, String created){

		String finalDay = "";

		long millis = getDateFromString(current).getTime() - getDateFromString(created).getTime();

		finalDay =getDurationBreakdown(millis);

		return finalDay;
	}

	public static String getTimeDue(String due, String current){

		String finalDay = "";

		long millis = getDateFromStringYMD(due).getTime() - getDateFromStringYMD(current).getTime();

		long diffInDays = TimeUnit.MILLISECONDS.toDays(millis);


//		if(diffInDays>=30)
//		{
//			long m = diffInDays/30;
//			if(m == 1)
//				finalDay = m + " Month";
//			else
//				finalDay = m + " Months";
//		}
		if(diffInDays>1)
		{
			finalDay = diffInDays + " Days left";
		}
		else if(diffInDays == 1){
			finalDay = "1 Day left";
		}
		else if(diffInDays<=0)
		{
			finalDay = "Today";
		}


		return finalDay;
	}

	public static String getCourseDurtion(String due, String current){

		String finalDay = "";

		long millis = getDateFromStringYMD(due).getTime() - getDateFromStringYMD(current).getTime();

		long diffInDays = TimeUnit.MILLISECONDS.toDays(millis);

		if(diffInDays>=30)
		{
			long m = diffInDays/30;
			if(m == 1)
				finalDay = m + " Month";
			else
				finalDay = m + " Months";
		}
		else if(diffInDays<30){
			if (diffInDays < 0) {
				diffInDays = diffInDays * (-1);
			}
			if(diffInDays <= 1) {

				finalDay = diffInDays + " Day";
			}
			else
				finalDay = diffInDays + " Days";
		}

		return finalDay;
	}

	public static Date getDateFromStringYMD(String dateSt){
		Date date = null;
		String dtStart = dateSt;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = format.parse(dtStart);
			System.out.println(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	public static Date getDateFromString(String dateSt){
		Date date = null;
		String dtStart = dateSt;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date = format.parse(dtStart);
			System.out.println(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	public static String getDurationBreakdown(long millis) {
		if(millis<= 0)
		{
			return "0";
		}
		if(millis < 0) {
			throw new IllegalArgumentException("Duration must be greater than zero!");

		}

		long days = TimeUnit.MILLISECONDS.toDays(millis);
		millis -= TimeUnit.DAYS.toMillis(days);
		long hours = TimeUnit.MILLISECONDS.toHours(millis);
		millis -= TimeUnit.HOURS.toMillis(hours);
		long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
		millis -= TimeUnit.MINUTES.toMillis(minutes);
		long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

		StringBuilder sb = new StringBuilder(64);
		if(days>0) {
			sb.append(days);
			if(days>1)
			sb.append(" Days ");
			else
				sb.append(" Day ");
			return sb.toString();

		}
		else if(hours>0) {
			sb.append(hours);
			if(hours>1)
			sb.append(" Hours ");
			else
				sb.append(" Hour ");

			return sb.toString();
		}
		else if(minutes>0) {
			sb.append(minutes);
			if(minutes>1)
			sb.append(" Minutes ");
			else
				sb.append(" Minute ");
			return sb.toString();
		}
		else if(seconds>0) {
			sb.append(seconds);
			if(seconds>1)
			sb.append(" Seconds");
			else
				sb.append(" Second");
			return sb.toString();
		}

		return(sb.toString());
	}


	public static String getDuration(String endTime, String startTime) {
		String time = "";
		time = getTime(startTime) +  " - " +getTime(endTime);
		return time;
	}
	public static String getTime(String st){
		String time = "";
		if(st.length()>2) {

			if (Integer.parseInt(st.substring(0, 2)) >= 12) {
				time = st + "pm";
			} else {
				time = st + "am";
			}

		}
		return time;
	}

	public static String convertSecondsToHMS(long second){
		String result = "";

//		int second = Integer.parseInt(seconds);

		int hr = (int)(second/3600);
		int rem = (int)(second%3600);
		int mn = rem/60;
		int sec = rem%60;
		//String hrStr = (hr<10 ? "0" : "")+hr;
		String mnStr = (mn<10 ? "0" : "")+mn;
		String secStr = (sec<10 ? "0" : "")+sec;

		//result = hrStr+":"+mnStr+":"+secStr;
		result = mnStr+":"+secStr;

		return result;
	}

	public static String timeTaken(String myTime) {

		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(myTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String formattedTime = sdf.format(date);
		return formattedTime;
	}
}
