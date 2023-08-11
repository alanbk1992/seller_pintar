package project.kimora.sellerpintar.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by msinfo on 13/02/23.
 */

public class DateTime {

    public static String formatDate(String timestamp, String format){
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(timestamp));
        } catch (ParseException e) {e.printStackTrace();}

        Locale locale = Locale.getDefault();
        sdf = new SimpleDateFormat(format, locale);
        return sdf.format(calendar.getTime());
    }

    public static String formatDateLong(String timestamp, String format){
        String myFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(timestamp));
        } catch (ParseException e) {e.printStackTrace();}

        Locale locale = Locale.getDefault();
        sdf = new SimpleDateFormat(format, locale);
        return sdf.format(calendar.getTime());
    }

    public static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    public static String countAge(String birthDate){
        String[] splits = birthDate.split("-");
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        dob.set(Integer.parseInt(splits[0]), Integer.parseInt(splits[1])-1, Integer.parseInt(splits[2]));

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }
        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }
}
