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

    public final Map<String, Integer> sp_resources = new HashMap<>();

    public final Map<String, Integer> phis_attr = new HashMap<>();
    public final Map<String, Integer> soc_attr  = new HashMap<>();
    public final Map<String, Integer> men_attr  = new HashMap<>();

    public final Map<String, Integer> tal_abl = new HashMap<>();
    public final Map<String, Integer> skl_abl = new HashMap<>();
    public final Map<String, Integer> kng_abl = new HashMap<>();

    public final Map<String, Integer> bkg = new HashMap<>();
    public final Map<String, Integer> sph = new HashMap<>();
    public final Map<String, Integer> gft = new HashMap<>();
    public final Map<String, Integer> dis = new HashMap<>();

    public final String char_name;
    public final String chronic_name;

    private String main_attr = "NULL";
    private String scnd_attr = "NULL";
    private String thrd_attr = "NULL";

    public final Integer[] attr = new Integer[3];
    public final Integer[] abl  = new Integer[3];

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

    public Integer save_new_values( String group, String name, Integer number ) {
        String type;
        Integer prev_num;
        if (group.equals("attr")) {
            if (name.equals("strength") || name.equals("dexterity") || name.equals("stamina")) {
                type = "phis";
                prev_num = phis_attr.get(name);
            }
            else if (name.equals("charisma") || name.equals("manipulation") || name.equals("appearance")) {
                type = "soc";
                prev_num = soc_attr.get(name);
            }
            else {
                type = "men";
                prev_num = men_attr.get(name);
            }

            if ( main_attr.equals("NULL") || main_attr.equals(type) ) {
                main_attr = type;
            }
            else if ( scnd_attr.equals("NULL") || scnd_attr.equals(type) ) {
                scnd_attr = type;
            }
            else if ( thrd_attr.equals("NULL") || thrd_attr.equals(type) ) {
                thrd_attr = type;
            }

            if ( main_attr.equals(type) ) {
                if ( (number - prev_num) <= attr[0] ) {
                    attr[0] -= (number - prev_num);
                }
                else {
                    number = prev_num + attr[0];
                    attr[0] = 0;
                }

                switch (type) {
                    case "phis":
                        phis_attr.put(name, number);
                        break;
                    case "soc":
                        soc_attr.put(name, number);
                        break;
                    case "men":
                        men_attr.put(name, number);
                        break;
                }
            }

            else if ( scnd_attr.equals(type) ) {
                if ( (number - prev_num) <= attr[1] ) {
                    attr[1] -= (number - prev_num);
                }
                else {
                    number = prev_num + attr[1];
                    attr[1] = 0;
                }

                switch (type) {
                    case "phis":
                        phis_attr.put(name, number);
                        break;
                    case "soc":
                        soc_attr.put(name, number);
                        break;
                    case "men":
                        men_attr.put(name, number);
                        break;
                }
            }

            else if ( thrd_attr.equals(type) ) {
                if ( (number - prev_num) <= attr[2] ) {
                    attr[2] -= (number - prev_num);
                }
                else {
                    number = prev_num + attr[2];
                    attr[2] = 0;
                }

                switch (type) {
                    case "phis":
                        phis_attr.put(name, number);
                        break;
                    case "soc":
                        soc_attr.put(name, number);
                        break;
                    case "men":
                        men_attr.put(name, number);
                        break;
                }
            }
        }
        return number;
    }


    public Map<String, Integer> get_sp_res() {
        return sp_resources;
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

    public Integer get_health_pen() {
        Integer pen = 0;
        Integer health_val = sp_resources.get("health");
        if ( 2 < health_val && health_val < 7 ) {
            pen = 1;
        }
        else if ( 6 < health_val && health_val < 11 ) {
            pen = 2;
        }
        else if ( 10 < health_val && health_val < 13 ) {
            pen = 5;
        }
        else if ( 12 < health_val && health_val <= 14 ) {
            pen = 10;
        }

        return pen;
    }
}
