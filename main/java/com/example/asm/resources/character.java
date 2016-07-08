package com.example.asm.resources;

import android.content.Context;
import android.os.Environment;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
class character implements Serializable {

    public final String char_name;
    public final String chronic_name;

    public String class_name;
    public String alignment;
    public String god;

    public Integer Generated;

    public  final Map<String, Integer> phis_attr;
    public  final Map<String, Integer> soc_attr;
    public  final Map<String, Integer> men_attr;

    public  final Integer[] attr;
    private final Integer attr_gen_limit;

    private final Map<String, Integer> stored_phis_attr;
    private final Map<String, Integer> stored_soc_attr;
    private final Map<String, Integer> stored_men_attr;

    public  final Map<String, Integer> tal_abl;
    public  final Map<String, Integer> skl_abl;
    public  final Map<String, Integer> kng_abl;

    public  final Integer[] abl;
    private final Integer abl_gen_limit;

    private final Map<String, Integer> stored_tal_abl;
    private final Map<String, Integer> stored_skl_abl;
    private final Map<String, Integer> stored_kng_abl;

    public  final Map<String, Integer> bkg;

    public  Integer bkg_gen_points;
    private final Map<String, Integer> stored_bkg;

    public Integer class_feature_gen_points;
    private final Integer class_feature_gen_limit;

    public  final Map<String, Integer> sph;
    public  final Map<String, Integer> gft;
    public  final Map<String, Integer> dis;

    private final Map<String, Integer> stored_sph;
    private final Map<String, Integer> stored_gft;
    private final Map<String, Integer> stored_dis;

    public final Map<String, Integer> sp_resources;

    private final Map<String, Integer> postponed_payments;

    public Integer free_points;

    private String main_attr;
    private String scnd_attr;
    private String thrd_attr;

    private String main_abl;
    private String scnd_abl;
    private String thrd_abl;


    public character( String name, String chronic ) {
        char_name    = name;
        chronic_name = chronic;

        class_name = "Воин";
        alignment  = "Законопослушный-Добрый";
        god        = "Акади";

        Generated = 0;

        phis_attr = new HashMap<String, Integer>() {{
            put("strength", 1);
            put("dexterity", 1);
            put("stamina", 1);
        }};

        soc_attr = new HashMap<String, Integer>() {{
            put("charisma", 1);
            put("manipulation", 1);
            put("appearance", 1);
        }};

        men_attr = new HashMap<String, Integer>() {{
            put("perception", 1);
            put("intelligence", 1);
            put("wits", 1);
        }};

        attr = new Integer[]{ 7, 5, 3 };
        attr_gen_limit = 4;

        stored_phis_attr = new HashMap<>();
        stored_soc_attr  = new HashMap<>();
        stored_men_attr  = new HashMap<>();

        tal_abl = new HashMap<String, Integer>() {{
            put("alertness", 0);
            put("athletics", 0);
            put("brawl", 0);
            put("subterfuge", 0);
            put("ambidexter", 0);
            put("leadership", 0);
            put("intimidation", 0);
            put("persuasion", 0);
            put("streetwise", 0);
            put("enlightenment", 0);
        }};

        skl_abl = new HashMap<String, Integer>() {{
            put("security", 0);
            put("craft", 0);
            put("blindfight", 0);
            put("etiquette", 0);
            put("firearms", 0);
            put("survival", 0);
            put("melee", 0);
            put("herbalism", 0);
            put("stealth", 0);
            put("concentration", 0);
        }};

        kng_abl = new HashMap<String, Integer>() {{
            put("alchemistry", 0);
            put("logic", 0);
            put("investigation", 0);
            put("religion", 0);
            put("linguistics", 0);
            put("medicine", 0);
            put("occult", 0);
            put("politics", 0);
            put("science", 0);
        }};

        abl = new Integer[]{ 13, 9, 5 };
        abl_gen_limit = 3;

        stored_tal_abl = new HashMap<>();
        stored_skl_abl = new HashMap<>();
        stored_kng_abl = new HashMap<>();

        bkg = new HashMap<String, Integer>() {{
            put("allies", 0);
            put("contacts", 0);
            put("fame", 0);
            put("resources", 0);
            put("dignity", 0);
            put("artifact", 0);
        }};

        bkg_gen_points = 5;
        stored_bkg = new HashMap<>();

        class_feature_gen_points = 5;
        class_feature_gen_limit = 3;

        sph = new HashMap<String, Integer>() {{
            put("time", 0);
            put("spirit", 0);
            put("life", 0);
            put("matter", 0);
            put("prime", 0);
            put("mind", 0);
            put("connection", 0);
            put("forces", 0);
            put("entropy", 0);
        }};

        dis = new HashMap<String, Integer>() {{
            put("animalism", 0);
            put("auspex", 0);
            put("celerity", 0);
            put("chimerstry", 0);
            put("daimoinon", 0);
            put("dementation", 0);
            put("dominate", 0);
            put("fortitude", 0);
            put("melpominee", 0);
            put("obfuscate", 0);
            put("obtenebration", 0);
            put("potence", 0);
            put("presence", 0);
            put("protean", 0);
            put("quietus", 0);
            put("serpentis", 0);
            put("temporis", 0);
            put("thanatosis", 0);
            put("vicissitude", 0);
            put("alchemistry", 0);
            put("conveyance", 0);
            put("enchantment", 0);
            put("healing", 0);
            put("hellfire", 0);
            put("weathercraft", 0);
        }};

        gft = new HashMap<String, Integer>() {{
            put("common", 0);
            put("monk", 0);
            put("warlord", 0);
            put("assassin", 0);
            put("mageslayer", 0);
            put("shapeshifter", 0);
        }};

        stored_sph = new HashMap<>();
        stored_dis = new HashMap<>();
        stored_gft = new HashMap<>();

        sp_resources = new HashMap<String, Integer>() {{
            put("rage", 0);
            put("faith", 0);
            put("wp", 0);
            put("health", 0);
            put("perm_faith", 0);
            put("perm_wp", 0);
        }};

        postponed_payments = new HashMap<>();

        free_points = 15;
    }

    public Integer save_new_values(String group, String name, Integer number ) {
        Integer prev_num;
        if (group.equals("attr")) {
            number = save_attr_values( name, number );
        }

        if (group.equals("abl")) {
            number = save_abl_values( name, number );
        }

        if (group.equals("bkg")) {
            number = save_bkg_values( name, number );
        }

        if (group.equals("sph")) {
            prev_num = sph.get(name);
            number = save_class_feature_values( number, prev_num );
            sph.put(name, number);
        }

        if (group.equals("dis")) {
            prev_num = dis.get(name);
            number = save_class_feature_values( number, prev_num );
            dis.put(name, number);
        }

        if (group.equals("gft")) {
            prev_num = gft.get(name);
            number = save_class_feature_values( number, prev_num );
            gft.put(name, number);
        }

        if (group.equals("button")) {
            return 1;
        }

        return number;
    }

    private Integer save_attr_values( String name, Integer number ) {
        String type;
        Integer prev_num;
        if ( Generated == 0 ) {
            if (number > attr_gen_limit) {
                number = attr_gen_limit;
            }
        }

        if ( phis_attr.get(name) != null ) {
            type = "phis";
            prev_num = phis_attr.get(name);

        }
        else if ( soc_attr.get(name) != null ) {
            type = "soc";
            prev_num = soc_attr.get(name);
        }
        else {
            type = "men";
            prev_num = men_attr.get(name);
        }

        if ( main_attr == null      || main_attr.equals(type) ) {
            main_attr = type;
        }
        else if ( scnd_attr == null || scnd_attr.equals(type) ) {
            scnd_attr = type;
        }
        else if ( thrd_attr == null || thrd_attr.equals(type) ) {
            thrd_attr = type;
        }

        Integer[] returned;
        if ( main_attr.equals(type) ) {
            returned = add_num_to_attr( name, number, prev_num, attr[0] );
            attr[0]  = returned[1];
        }

        else if ( scnd_attr.equals(type) ) {
            returned = add_num_to_attr( name, number, prev_num, attr[1] );
            attr[1]  = returned[1];
        }

        else {
            returned = add_num_to_attr( name, number, prev_num, attr[2] );
            attr[2]  = returned[1];
        }
        number = returned[0];

        return number;
    }

    private Integer[] add_num_to_attr( String name, Integer number, Integer prev_num, Integer attr_v ) {
        if ( (number - prev_num) <= attr_v ) {
            attr_v -= (number - prev_num);
        }
        else {
            number = prev_num + attr_v;
            attr_v = 0;
        }

        if ( phis_attr.get(name) != null ) {
            phis_attr.put(name, number);

        }
        else if ( soc_attr.get(name) != null ) {
            soc_attr.put(name, number);
        }
        else {
            men_attr.put(name, number);
        }

        return new Integer[] {number, attr_v};
    }

    private Integer save_abl_values( String name, Integer number ) {
        String type;
        Integer prev_num;
        if ( Generated == 0 ) {
            if (number > abl_gen_limit) {
                number = abl_gen_limit;
            }
        }

        if ( tal_abl.get(name) != null ) {
            type = "tal";
            prev_num = tal_abl.get(name);

            if (number == 1 && prev_num == 1) {
                //noinspection StatementWithEmptyBody
                if (name.equals("enlightenment") && class_name.equals("Маг")) {
                } else {
                    number = 0;
                }
            }
        }
        else if ( skl_abl.get(name) != null ) {
            type = "skl";
            prev_num = skl_abl.get(name);

            if ( number == 1 && prev_num == 1 ) {
                number = 0;
            }
        }
        else {
            type = "kng";
            prev_num = kng_abl.get(name);

            if (number == 1 && prev_num == 1) {
                //noinspection StatementWithEmptyBody
                if (name.equals("religion") && class_name.equals("Жрец")) {
                } else {
                    number = 0;
                }
            }
        }

        if ( main_abl == null      || main_abl.equals(type) ) {
            main_abl = type;
        }
        else if ( scnd_abl == null || scnd_abl.equals(type) ) {
            scnd_abl = type;
        }
        else if ( thrd_abl == null || thrd_abl.equals(type) ) {
            thrd_abl = type;
        }

        Integer[] returned;
        if ( main_abl.equals(type) ) {
            returned = add_num_to_abl( name, number, prev_num, abl[0] );
            abl[0]  = returned[1];
        }

        else if ( scnd_abl.equals(type) ) {
            returned = add_num_to_abl( name, number, prev_num, abl[1] );
            abl[1]  = returned[1];
        }

        else {
            returned = add_num_to_abl( name, number, prev_num, abl[2] );
            abl[2]  = returned[1];
        }
        number = returned[0];

        return number;
    }

    private Integer[] add_num_to_abl( String name, Integer number, Integer prev_num, Integer abl_v ) {

        if ( (number - prev_num) <= abl_v ) {
            abl_v -= (number - prev_num);
        }
        else {
            number = prev_num + abl_v;
            abl_v = 0;
        }

        if ( tal_abl.get(name) != null ) {
            tal_abl.put(name, number);

            Integer postponed = postponed_payments.get("tal");
            if ( postponed != null ) {
                abl_v -= postponed;
                postponed_payments.remove("tal");
            }
        }
        else if ( skl_abl.get(name) != null ) {
            skl_abl.put(name, number);
        }
        else {
            kng_abl.put(name, number);

            Integer postponed = postponed_payments.get("kng");
            if ( postponed != null ) {
                abl_v -= postponed;
                postponed_payments.remove("kng");
            }
        }

        return new Integer[] {number, abl_v};
    }

    private Integer save_bkg_values( String name, Integer number ) {
        Integer prev_num;
        prev_num = bkg.get(name);

        if ( number == 1 && prev_num == 1 ) {
            number = 0;
        }

        if ( (number - prev_num) <= bkg_gen_points ) {
            bkg_gen_points -= (number - prev_num);
        }
        else {
            number = prev_num + bkg_gen_points;
            bkg_gen_points = 0;
        }
        bkg.put(name, number);

        return number;
    }

    private Integer save_class_feature_values(Integer number, Integer prev_num ) {
        if ( Generated == 0 ) {
            if (number > class_feature_gen_limit) {
                number = class_feature_gen_limit;
            }
        }

        if ( number == 1 && prev_num == 1 ) {
            number = 0;
        }

        if ( (number - prev_num) <= class_feature_gen_points ) {
            class_feature_gen_points -= (number - prev_num);
        }
        else {
            number = prev_num + class_feature_gen_points;
            class_feature_gen_points = 0;
        }

        return number;
    }


    public boolean check_gen_points() {
        if ( Generated == 0 ) {
            Integer sum = 0;
            for (int e : attr) sum += e;
            for (int e : abl)  sum += e;
            sum += bkg_gen_points;
            sum += class_feature_gen_points;

            if (sum != 0) {
                return true;
            }
        }

        return false;
    }

    public Integer save_fp_values( String group, String name, Integer number ) {
        Integer prev;
        Integer price;
        Integer diff;
        switch (group) {
            case "attr":
                price = 5;
                if (phis_attr.get(name) != null) {
                    prev = phis_attr.get(name);
                    diff = number - prev;
                    if (number < stored_phis_attr.get(name)) {
                        Integer st = prev - stored_phis_attr.get(name);
                        diff = -st;
                        number = stored_phis_attr.get(name);
                    }

                    if ((free_points - (price * diff)) >= 0) {
                        free_points -= (price * diff);
                    } else {
                        Integer available = free_points / price;
                        free_points -= available;
                        number = prev + available;
                    }
                    phis_attr.put(name, number);
                } else if (soc_attr.get(name) != null) {
                    prev = soc_attr.get(name);
                    diff = number - prev;
                    if (number < stored_soc_attr.get(name)) {
                        Integer st = prev - stored_soc_attr.get(name);
                        diff = -st;
                        number = stored_soc_attr.get(name);
                    }

                    if ((free_points - (price * diff)) >= 0) {
                        free_points -= (price * diff);
                    } else {
                        Integer available = free_points / price;
                        free_points -= available;
                        number = prev + available;
                    }
                    soc_attr.put(name, number);
                } else if (men_attr.get(name) != null) {
                    prev = men_attr.get(name);
                    diff = number - prev;
                    if (number < stored_men_attr.get(name)) {
                        Integer st = prev - stored_men_attr.get(name);
                        diff = -st;
                        number = stored_men_attr.get(name);
                    }

                    if ((free_points - (price * diff)) >= 0) {
                        free_points -= (price * diff);
                    } else {
                        Integer available = free_points / price;
                        free_points -= available;
                        number = prev + available;
                    }
                    men_attr.put(name, number);
                }
                break;
            case "abl":
                price = 2;
                if (tal_abl.get(name) != null) {
                    prev = tal_abl.get(name);

                    if ( number == 1 && prev == 1 && stored_tal_abl.get(name) == 0 ) {
                        number = 0;
                    }

                    diff = number - prev;
                    if (number < stored_tal_abl.get(name)) {
                        Integer st = prev - stored_tal_abl.get(name);
                        diff = -st;
                        number = stored_tal_abl.get(name);
                    }

                    if ((free_points - (price * diff)) >= 0) {
                        free_points -= (price * diff);
                    } else {
                        Integer available = free_points / price;
                        free_points -= available;
                        number = prev + available;
                    }
                    tal_abl.put(name, number);
                } else if (skl_abl.get(name) != null) {
                    prev = skl_abl.get(name);

                    if ( number == 1 && prev == 1 && stored_skl_abl.get(name) == 0 ) {
                        number = 0;
                    }

                    diff = number - prev;
                    if (number < stored_skl_abl.get(name)) {
                        Integer st = prev - stored_skl_abl.get(name);
                        diff = -st;
                        number = stored_skl_abl.get(name);
                    }

                    if ((free_points - (price * diff)) >= 0) {
                        free_points -= (price * diff);
                    } else {
                        Integer available = free_points / price;
                        free_points -= available;
                        number = prev + available;
                    }
                    skl_abl.put(name, number);
                } else if (kng_abl.get(name) != null) {
                    prev = kng_abl.get(name);

                    if ( number == 1 && prev == 1 && stored_kng_abl.get(name) == 0 ) {
                        number = 0;
                    }

                    diff = number - prev;
                    if (number < stored_kng_abl.get(name)) {
                        Integer st = prev - stored_kng_abl.get(name);
                        diff = -st;
                        number = stored_kng_abl.get(name);
                    }

                    if ((free_points - (price * diff)) >= 0) {
                        free_points -= (price * diff);
                    } else {
                        Integer available = free_points / price;
                        free_points -= available;
                        number = prev + available;
                    }
                    kng_abl.put(name, number);
                }
                break;
            case "bkg":
                price = 1;
                prev = bkg.get(name);

                if ( number == 1 && prev == 1 && stored_bkg.get(name) == 0 ) {
                    number = 0;
                }

                diff = number - prev;
                if (number < stored_bkg.get(name)) {
                    Integer st = prev - stored_bkg.get(name);
                    diff = -st;
                    number = stored_bkg.get(name);
                }

                if ((free_points - (price * diff)) >= 0) {
                    free_points -= (price * diff);
                } else {
                    Integer available = free_points / price;
                    free_points -= available;
                    number = prev + available;
                }
                bkg.put(name, number);
                break;
            case "sph":
                price = 7;
                prev = sph.get(name);

                if ( number == 1 && prev == 1 && stored_sph.get(name) == 0 ) {
                    number = 0;
                }

                diff = number - prev;
                if (number < stored_sph.get(name)) {
                    Integer st = prev - stored_sph.get(name);
                    diff = -st;
                    number = stored_sph.get(name);
                }

                if ((free_points - (price * diff)) >= 0) {
                    free_points -= (price * diff);
                } else {
                    Integer available = free_points / price;
                    free_points -= available;
                    number = prev + available;
                }
                sph.put(name, number);
                break;
            case "dis":
                price = 7;
                prev = dis.get(name);

                if ( number == 1 && prev == 1 && stored_dis.get(name) == 0 ) {
                    number = 0;
                }

                diff = number - prev;
                if (number < stored_dis.get(name)) {
                    Integer st = prev - stored_dis.get(name);
                    diff = -st;
                    number = stored_dis.get(name);
                }

                if ((free_points - (price * diff)) >= 0) {
                    free_points -= (price * diff);
                } else {
                    Integer available = free_points / price;
                    free_points -= available;
                    number = prev + available;
                }
                dis.put(name, number);
                break;
            case "gft":
                price = 7;
                prev = gft.get(name);

                if ( number == 1 && prev == 1 && stored_gft.get(name) == 0 ) {
                    number = 0;
                }

                diff = number - prev;
                if (number < stored_gft.get(name)) {
                    Integer st = prev - stored_gft.get(name);
                    diff = -st;
                    number = stored_gft.get(name);
                }

                if ((free_points - (price * diff)) >= 0) {
                    free_points -= (price * diff);
                } else {
                    Integer available = free_points / price;
                    free_points -= available;
                    number = prev + available;
                }
                gft.put(name, number);
                break;
            case "button":
                if (! name.equals("wp")) {
                    return 0;
                }
                price = 2;
                prev  = sp_resources.get("perm_" + name);

                if ( number == 1 && prev == 1 && !class_name.equals("Маг") ) {
                    number = 0;
                }

                diff  = number - prev;

                if ((free_points - (price * diff)) >= 0) {
                    free_points -= (price * diff);
                } else {
                    Integer available = free_points / price;
                    free_points -= available;
                    number = prev + available;
                }
                sp_resources.put("perm_" + name, number);

                break;
        }

        return number;
    }

    public void store_values() {
        stored_phis_attr.putAll( phis_attr );
        stored_soc_attr.putAll( soc_attr );
        stored_men_attr.putAll( men_attr );

        stored_tal_abl.putAll( tal_abl );
        stored_skl_abl.putAll( skl_abl );
        stored_kng_abl.putAll( kng_abl );

        stored_bkg.putAll( bkg );

        stored_sph.putAll( sph );
        stored_dis.putAll( dis );
        stored_gft.putAll( gft );
    }

    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    public void flush( Context context ) {
        String filename = char_name + " " + chronic_name;
        if ( isExternalStorageWritable() ){
            FileOutputStream fos;
            try {
                fos = context.openFileOutput(
                        filename,
                        Context.MODE_PRIVATE);
            }
            catch ( FileNotFoundException e) {
                throw new RuntimeException( "Can't open file <" + filename + ">" );
            }
            ObjectOutputStream os;
            try {
                os = new ObjectOutputStream(fos);
                os.writeObject(this);
                os.close();
                fos.close();
            }
            catch ( IOException e ) {
                throw new RuntimeException( "IO error <" + filename + ">" );
            }
        }
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

    public void return_m_point( String group ) {
        if ( group.equals("tal_abl") ) {
            if (postponed_payments.get("tal") == null) {
                Integer r_val = tal_abl.get("enlightenment");
                if (main_abl.equals("tal")) {
                    abl[0] += r_val;
                } else if (scnd_abl.equals("tal")) {
                    abl[1] += r_val;
                } else if (thrd_abl.equals("tal")) {
                    abl[2] += r_val;
                }
            } else {
                postponed_payments.remove("tal");
            }
            tal_abl.put("enlightenment", 0);
        }
        else if ( group.equals("kng_abl") ) {
            if (postponed_payments.get("kng") == null) {
                Integer r_val = kng_abl.get("religion");
                if (main_abl.equals("kng")) {
                    abl[0] += r_val;
                } else if (scnd_abl.equals("kng")) {
                    abl[1] += r_val;
                } else if (thrd_abl.equals("kng")) {
                    abl[2] += r_val;
                }
            } else {
                postponed_payments.remove("kng");
            }
            kng_abl.put("religion", 0);
        }
    }

    @SuppressWarnings("SameParameterValue")
    public void delay_charge(String name, Integer num ) {
        if ( name.equals("enlightenment") ) {
            tal_abl.put( name, num );
            if ( main_abl != null && main_abl.equals("tal") ) {
                abl[0] -= num;
            }
            else if ( main_abl != null && scnd_abl.equals("tal") ) {
                abl[1] -= num;
            }
            else if ( main_abl != null && thrd_abl.equals("tal") ) {
                abl[2] -= num;
            }
            else {
                postponed_payments.put( "tal", num );
            }
        }
        else if ( name.equals("religion") ) {
            kng_abl.put( name, num );
            if ( main_abl != null && main_abl.equals("kng") ) {
                abl[0] -= num;
            }
            else if ( main_abl != null && scnd_abl.equals("kng") ) {
                abl[1] -= num;
            }
            else if ( main_abl != null && thrd_abl.equals("kng") ) {
                abl[2] -= num;
            }
            else {
                postponed_payments.put( "kng", num );
            }
        }

    }
}
