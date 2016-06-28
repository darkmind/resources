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

    private String main_abl = "NULL";
    private String scnd_abl = "NULL";
    private String thrd_abl = "NULL";

    private Integer attr_gen_limit;
    private Integer abl_gen_limit;
    public  Integer bkg_gen_points;
    public  Integer cf_gen_points;
    private Integer cf_gen_limit;

    public final Integer[] attr = new Integer[3];
    public final Integer[] abl  = new Integer[3];

    public Integer Generated;

    public character( String name, String chronic ) {
        char_name    = name;
        chronic_name = chronic;
        Generated = 0;

        attr[0] = 7;
        attr[1] = 5;
        attr[2] = 3;
        abl[0]  = 13;
        abl[1]  = 9;
        abl[2]  = 5;
        attr_gen_limit = 4;
        abl_gen_limit  = 3;
        bkg_gen_points = 5;
        cf_gen_points  = 5;
        cf_gen_limit   = 3;

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

        sph.put("time", 0);
        sph.put("spirit", 0);
        sph.put("life", 0);
        sph.put("matter", 0);
        sph.put("prime", 0);
        sph.put("mind", 0);
        sph.put("connection", 0);
        sph.put("forces", 0);
        sph.put("entropy", 0);

        dis.put("animalism", 0);
        dis.put("auspex", 0);
        dis.put("celerity", 0);
        dis.put("chimerstry", 0);
        dis.put("daimoinon", 0);
        dis.put("dementation", 0);
        dis.put("dominate", 0);
        dis.put("fortitude", 0);
        dis.put("melpominee", 0);
        dis.put("obfuscate", 0);
        dis.put("obtenebration", 0);
        dis.put("potence", 0);
        dis.put("presence", 0);
        dis.put("protean", 0);
        dis.put("quietus", 0);
        dis.put("serpentis", 0);
        dis.put("temporis", 0);
        dis.put("thanatosis", 0);
        dis.put("vicissitude", 0);
        dis.put("alchemistry", 0);
        dis.put("conveyance", 0);
        dis.put("enchantment", 0);
        dis.put("healing", 0);
        dis.put("hellfire", 0);
        dis.put("weathercraft", 0);

        gft.put("common", 0);
        gft.put("monk", 0);
        gft.put("warlord", 0);
        gft.put("assassin", 0);
        gft.put("mageslayer", 0);
        gft.put("shapeshifter", 0);

        sp_resources.put("rage", 0);
        sp_resources.put("faith", 0);
        sp_resources.put("wp", 0);
        sp_resources.put("health", 0);
        sp_resources.put("perm_rage", 0);
        sp_resources.put("perm_faith", 0);
        sp_resources.put("perm_wp", 0);
    }

    public Integer save_new_values( String group, String name, Integer number ) {
        String type;
        Integer prev_num;
        if (group.equals("attr")) {
            if ( Generated == 0 ) {
                if (number > attr_gen_limit) {
                    number = attr_gen_limit;
                }
            }
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

        if (group.equals("abl")) {
            if ( Generated == 0 ) {
                if (number > abl_gen_limit) {
                    number = abl_gen_limit;
                }
            }
            Integer gen_points;
            if ( tal_abl.get(name) != null ) {
                prev_num = tal_abl.get(name);
                if (tal_abl.get( "gen_points" ) == null) {
                    if (main_abl.equals("NULL")) {
                        tal_abl.put( "gen_points", abl[0] );
                        main_abl = "tal";
                    }
                    else if (scnd_abl.equals("NULL")) {
                        tal_abl.put( "gen_points", abl[1] );
                        scnd_abl = "tal";
                    }
                    else if (thrd_abl.equals("NULL")) {
                        tal_abl.put( "gen_points", abl[2] );
                        thrd_abl = "tal";
                    }
                }
                gen_points = tal_abl.get( "gen_points" );
                if ( (number - prev_num) <= gen_points ) {
                    gen_points -= (number - prev_num);
                }
                else {
                    number = prev_num + gen_points;
                    gen_points = 0;
                }
                tal_abl.put(name, number);
                tal_abl.put( "gen_points", gen_points );
                if ( main_abl.equals("tal") ) {
                    abl[0] = gen_points;
                }
                else if ( scnd_abl.equals("tal") ) {
                    abl[1] = gen_points;
                }
                else if ( thrd_abl.equals("tal") ) {
                    abl[2] = gen_points;
                }
            }
            if ( skl_abl.get(name) != null ) {
                prev_num = skl_abl.get(name);
                if (skl_abl.get( "gen_points" ) == null) {
                    if (main_abl.equals("NULL")) {
                        skl_abl.put( "gen_points", abl[0] );
                        main_abl = "skl";
                    }
                    else if (scnd_abl.equals("NULL")) {
                        skl_abl.put( "gen_points", abl[1] );
                        scnd_abl = "skl";
                    }
                    else if (thrd_abl.equals("NULL")) {
                        skl_abl.put( "gen_points", abl[2] );
                        thrd_abl = "skl";
                    }
                }
                gen_points = skl_abl.get( "gen_points" );
                if ( (number - prev_num) <= gen_points ) {
                    gen_points -= (number - prev_num);
                }
                else {
                    number = prev_num + gen_points;
                    gen_points = 0;
                }
                skl_abl.put(name, number);
                skl_abl.put( "gen_points", gen_points );
                if ( main_abl.equals("skl") ) {
                    abl[0] = gen_points;
                }
                else if ( scnd_abl.equals("skl") ) {
                    abl[1] = gen_points;
                }
                else if ( thrd_abl.equals("skl") ) {
                    abl[2] = gen_points;
                }
            }
            if ( kng_abl.get(name) != null ) {
                prev_num = kng_abl.get(name);
                if (kng_abl.get( "gen_points" ) == null) {
                    if (main_abl.equals("NULL")) {
                        kng_abl.put( "gen_points", abl[0] );
                        main_abl = "kng";
                    }
                    else if (scnd_abl.equals("NULL")) {
                        kng_abl.put( "gen_points", abl[1] );
                        scnd_abl = "kng";
                    }
                    else if (thrd_abl.equals("NULL")) {
                        kng_abl.put( "gen_points", abl[2] );
                        thrd_abl = "kng";
                    }
                }
                gen_points = kng_abl.get( "gen_points" );
                if ( (number - prev_num) <= gen_points ) {
                    gen_points -= (number - prev_num);
                }
                else {
                    number = prev_num + gen_points;
                    gen_points = 0;
                }
                kng_abl.put(name, number);
                kng_abl.put( "gen_points", gen_points );
                if ( main_abl.equals("kng") ) {
                    abl[0] = gen_points;
                }
                else if ( scnd_abl.equals("kng") ) {
                    abl[1] = gen_points;
                }
                else if ( thrd_abl.equals("kng") ) {
                    abl[2] = gen_points;
                }
            }
        }

        if (group.equals("bkg")) {
            prev_num = bkg.get(name);
            if ( (number - prev_num) <= bkg_gen_points ) {
                bkg_gen_points -= (number - prev_num);
            }
            else {
                number = prev_num + bkg_gen_points;
                bkg_gen_points = 0;
            }
            bkg.put(name, number);
        }

        if (group.equals("sph")) {
            if ( Generated == 0 ) {
                if (number > cf_gen_limit) {
                    number = cf_gen_limit;
                }
            }
            prev_num = sph.get(name);
            if ( (number - prev_num) <= cf_gen_points ) {
                cf_gen_points -= (number - prev_num);
            }
            else {
                number = prev_num + cf_gen_points;
                cf_gen_points = 0;
            }
            sph.put(name, number);
        }

        if (group.equals("dis")) {
            if ( Generated == 0 ) {
                if (number > cf_gen_limit) {
                    number = cf_gen_limit;
                }
            }
            prev_num = dis.get(name);
            if ( (number - prev_num) <= cf_gen_points ) {
                cf_gen_points -= (number - prev_num);
            }
            else {
                number = prev_num + cf_gen_points;
                cf_gen_points = 0;
            }
            dis.put(name, number);
        }

        if (group.equals("gft")) {
            if ( Generated == 0 ) {
                if (number > cf_gen_limit) {
                    number = cf_gen_limit;
                }
            }
            prev_num = gft.get(name);
            if ( (number - prev_num) <= cf_gen_points ) {
                cf_gen_points -= (number - prev_num);
            }
            else {
                number = prev_num + cf_gen_points;
                cf_gen_points = 0;
            }
            gft.put(name, number);
        }

        if (group.equals("button")) {
            sp_resources.put( "perm_" + name, number );
        }

        return number;
    }

    public boolean check_gen_points() {
        if ( Generated == 0 ) {
            Integer sum = 0;
            for (int e : attr) sum += e;
            for (int e : abl) sum += e;
            sum += bkg_gen_points;
            sum += cf_gen_points;

            if (sum > 0) {
                return true;
            }
        }

        return false;
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
