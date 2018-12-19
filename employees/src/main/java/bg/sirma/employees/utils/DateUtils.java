package bg.sirma.employees.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	private static String[] formats = {"yyyy-MM-dd", "yyyy/MM/dd", "yyyy.MM.dd", "yyyy.MM.d", 
									   "dd-MM-yyyy", "dd/MM/yyyy", "dd.MM.yyyy", "d.MM.yyyy"};
	
	public static Date formatDate(String date) {
		for (String format : formats) {
			DateFormat dateFormat = new SimpleDateFormat(format);
			try {
				Date parsedDate = dateFormat.parse(date);
				return parsedDate;
			} catch (ParseException e) {}
		}
		return null;
	}
}
