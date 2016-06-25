package com.example.asm.resources;

import android.content.Context;
import android.os.Environment;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@SuppressWarnings("serial")
public class character implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, Integer> sp_resources = new HashMap<>();

    private Map<String, Integer> phis_attr = new HashMap<>();
    private Map<String, Integer> soc_attr  = new HashMap<>();
    private Map<String, Integer> men_attr  = new HashMap<>();

    private Map<String, Integer> tal_abl = new HashMap<>();
    private Map<String, Integer> skl_abl = new HashMap<>();
    private Map<String, Integer> kng_abl = new HashMap<>();

    private Map<String, Integer> bkg = new HashMap<>();
    private Map<String, Integer> sph = new HashMap<>();
    private Map<String, Integer> gft = new HashMap<>();
    private Map<String, Integer> dis = new HashMap<>();

    public String char_name;
    public String chronic_name;

    // Private constructor prevents instantiation from other classes
    public character( String name, String chronic ) {
        char_name = name;
        chronic_name = chronic;

        phis_attr.put("strength", 1);
        phis_attr.put("dexterity", 1);
        phis_attr.put("stamina", 1);

        soc_attr.put("charisma", 1);
        soc_attr.put("manipulation", 1);
        soc_attr.put("appearance", 1);


        sp_resources.put("rage", 1);
        sp_resources.put("faith", 0);
        sp_resources.put("wp", 0);
        sp_resources.put("health", 0);
        sp_resources.put("perm_rage", 1);
        sp_resources.put("perm_faith", 0);
        sp_resources.put("perm_wp", 0);
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
