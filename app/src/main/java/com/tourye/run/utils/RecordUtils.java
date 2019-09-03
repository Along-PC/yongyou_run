package com.tourye.run.utils;

import com.tourye.run.base.BaseApplication;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RecordUtils {

    public static void record(String content){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String path=BaseApplication.mApplicationContext.getExternalFilesDir("")+File.separator+"run";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        File location = new File(file, "log.txt");
        FileOutputStream fileOutputStream=null;
        try {
            fileOutputStream = new FileOutputStream(location,true);
            StringBuffer stringBuffer = new StringBuffer();
            Date date = new Date();
            String format = simpleDateFormat.format(date);
            stringBuffer.append(format+" ~ ");
            stringBuffer.append(content+"\n");
            fileOutputStream.write(stringBuffer.toString().getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
