package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static final SimpleDateFormat LONGDATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
    
    public static long toLong(String time){
        try {
            return DATETIME_FORMAT.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public static String toDate(long time){
        return DATE_FORMAT.format(new Date(time));
    }
    
    public static String toDateTime(long time){
        return DATETIME_FORMAT.format(new Date(time));
    }
    
    public static String toLongDateTime(long time){
        return LONGDATETIME_FORMAT.format(new Date(time));
    }
}
