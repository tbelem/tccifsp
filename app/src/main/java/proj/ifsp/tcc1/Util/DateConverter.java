package proj.ifsp.tcc1.Util;

import android.util.Log;

import java.security.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Tiago on 24/10/2017.
 */

public class DateConverter {

    public static String timestampToStringDate (long pTimestamp){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date aux = new Date(pTimestamp);
        return df.format(aux);
    };

    public static long stringDateToTimestamp (String pDate){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        Calendar cal = Calendar.getInstance();

        try {
            cal.setTime(df.parse(pDate));
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
        }
        catch(Exception e){
            return 0;
        }

        return cal.getTimeInMillis();
    };

    public static long dateToTimestamp (Date pDate){

        Calendar cal = Calendar.getInstance();

        try {
            cal.setTime(pDate);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
        }
        catch(Exception e){
            return 0;
        }

        return cal.getTimeInMillis();
    }

    public static long sysdateToTimestamp(){
        return Calendar.getInstance().getTimeInMillis();
    };
}
