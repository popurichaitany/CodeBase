import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Anand on 2/29/2016.
 */
public class logWriter {

    static String today;
    File extern;

    public File getExtern() {
        return extern;
    }

    public logWriter(Context c){
        try {
            Calendar Now = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            today= sdf.format(Now.getTime());
            String filename="activityLog_"+today+".csv";

            extern = new File(c.getFilesDir(),filename);
            if (extern.createNewFile());
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    public void lastActivityLogger(String l,File log,Context app){
        try {
            FileOutputStream outputStream = app.openFileOutput(log.getName(), Context.MODE_APPEND);
            outputStream.write(l.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
