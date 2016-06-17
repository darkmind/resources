package com.example.asm.resources;

import android.content.Context;
import android.os.Environment;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class character  {
    private static final character instance = new character();
    private Map<String, Integer> sp_resources = new HashMap<>();
    public String char_name;

    // Private constructor prevents instantiation from other classes
    private character() {
        sp_resources.put("rage", 1);
        sp_resources.put("faith", 0);
        sp_resources.put("wp", 0);
        sp_resources.put("health", 0);
        sp_resources.put("perm_rage", 1);
        sp_resources.put("perm_faith", 0);
        sp_resources.put("perm_wp", 0);
    }

    public static character getInstance() {
        return instance;
    }

    public Map<String, Integer> get_sp_res() {
        return sp_resources;
    }

    public void set_sp_res( Map<String, Integer> sp_res ) {
        sp_resources = sp_res;
    }

    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    public void flush( Context context ) {
        if ( isExternalStorageWritable() ){
            Properties properties = new Properties();
            for (Map.Entry<String,Integer> entry : sp_resources.entrySet()) {
                properties.put( entry.getKey(), entry.getValue().toString() );
            }
            FileOutputStream file_handler;
            try {
                file_handler = new FileOutputStream( context.getFilesDir() + "/" + char_name);
                properties.store(file_handler, null);
            }
            catch( IOException e){
                e.printStackTrace();
                System.exit(0);
            }
        }
    }

    public void reset() {
        sp_resources.put("rage", 1);
        sp_resources.put("faith", 0);
        sp_resources.put("wp", 0);
        sp_resources.put("health", 0);
        sp_resources.put("perm_rage", 1);
        sp_resources.put("perm_faith", 0);
        sp_resources.put("perm_wp", 0);
    }
}
