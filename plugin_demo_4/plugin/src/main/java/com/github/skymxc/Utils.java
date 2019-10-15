package com.github.skymxc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Utils {
    public static String getNow(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss.SSS");
        return dateFormat.format(Calendar.getInstance().getTime());
    }
    public static void writeFile(String path,String content) throws IOException {
        File file = new File(path);
        if (!file.exists()){
            if (!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
        }
        FileWriter writer = new FileWriter(file.getAbsolutePath(),true);
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        bufferedWriter.write(content);
        bufferedWriter.close();
    }
}
