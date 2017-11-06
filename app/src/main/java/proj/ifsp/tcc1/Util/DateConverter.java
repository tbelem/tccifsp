package proj.ifsp.tcc1.Util;

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

        Date aux = new Date(1);

        try {
            aux = df.parse(pDate);
        }
        catch(Exception e){
            return 0;
        }

        return aux.getTime();
    };

    public static long dateToTimestamp (Date pDate){
        return pDate.getTime();
    }

    public static long sysdateToTimestamp(){
        return dateToTimestamp(Calendar.getInstance().getTime());
    };
}
