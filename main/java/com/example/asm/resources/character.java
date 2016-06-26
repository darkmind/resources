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

    public Map<String, Integer> phis_attr = new HashMap<>();
    public Map<String, Integer> soc_attr  = new HashMap<>();
    public Map<String, Integer> men_attr  = new HashMap<>();

    public Map<String, Integer> tal_abl = new HashMap<>();
    public Map<String, Integer> skl_abl = new HashMap<>();
    public Map<String, Integer> kng_abl = new HashMap<>();

    private Map<String, Integer> bkg = new HashMap<>();
    private Map<String, Integer> sph = new HashMap<>();
    private Map<String, Integer> gft = new HashMap<>();
    private Map<String, Integer> dis = new HashMap<>();

    public String char_name;
    public String chronic_name;

    public String main_attr = "NULL";
    public String scnd_attr = "NULL";
    public String thrd_attr = "NULL";

    public Integer[] attr = new Integer[3];
    public Integer[] abl  = new Integer[3];

    // Private constructor prevents instantiation from other classes
    public character( String name, String chronic ) {
        char_name    = name;
        chronic_name = chronic;

        attr[0] = 7;
        attr[1] = 5;
        attr[2] = 3;
        abl[0]  = 13;
        abl[1]  = 9;
        abl[2]  = 5;

        phis_attr.put("strength", 1);
        phis_attr.put("dexterity", 1);
        phis_attr.put("stamina", 1);

        soc_attr.put("charisma", 1);
        soc_attr.put("manipulation", 1);
        soc_attr.put("appearance", 1);

        men_attr.put("perception", 1);
        men_attr.put("intelligence", 1);
        men_attr.put("wits", 1);

        tal_abl.put("alertness", 0);
        tal_abl.put("athletics", 0);
        tal_abl.put("brawl", 0);
        tal_abl.put("subterfuge", 0);
        tal_abl.put("ambidexter", 0);
        tal_abl.put("leadership", 0);
        tal_abl.put("intimidation", 0);
        tal_abl.put("persuasion", 0);
        tal_abl.put("streetwise", 0);

        skl_abl.put("security", 0);
        skl_abl.put("craft", 0);
        skl_abl.put("blindfight", 0);
        skl_abl.put("etiquete", 0);
        skl_abl.put("firearms", 0);
        skl_abl.put("survival", 0);
        skl_abl.put("melee", 0);
        skl_abl.put("herbalism", 0);
        skl_abl.put("stealth", 0);

        kng_abl.put("alchemistry", 0);
        kng_abl.put("logic", 0);
        kng_abl.put("investigation", 0);
        kng_abl.put("religion", 0);
        kng_abl.put("linguistics", 0);
        kng_abl.put("medecine", 0);
        kng_abl.put("occult", 0);
        kng_abl.put("politics", 0);
        kng_abl.put("science", 0);

        bkg.put("allies", 0);
        bkg.put("contacts", 0);
        bkg.put("fame", 0);
        bkg.put("resources", 0);
        bkg.put("dignity", 0);
        bkg.put("artifact", 0);

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
