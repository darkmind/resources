package com.example.asm.resources;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class full_char_list extends AppCompatActivity {
    private boolean mVisible;

    private View gifts;
    private View spheres;
    private View disciplines;
    private Spinner spinner_gd;
    private character char_o;

    private TextView attr_helper;
    private TextView abl_helper;
    private TextView bkg_helper;
    private TextView sph_helper;
    private TextView dis_helper;
    private TextView gft_helper;

    private ArrayAdapter<CharSequence> adapter_gd;

    private final String[] discs = { "dis_animalism", "dis_auspex", "dis_celerity", "dis_chimerstry",
            "dis_daimoinon", "dis_dementation", "dis_dominate", "dis_fortitude", "dis_melpominee",
            "dis_obfuscate", "dis_obtenebration", "dis_potence", "dis_presence", "dis_protean",
            "dis_quietus", "dis_serpentis", "dis_temporis", "dis_thanatosis", "dis_vicissitude",
            "dis_alchemistry", "dis_conveyance", "dis_enchantment", "dis_healing", "dis_hellfire",
            "dis_weathercraft" };

    private final String[] resources = { "rage", "faith", "wp" };

    public void switch_screen(View view) {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LOW_PROFILE
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
        mVisible = false;
    }

    @SuppressLint("InlinedApi")
    private void show() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.show();
        }
        mVisible = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_char_list);

        mVisible = true;

        char_o = (character) getIntent().getSerializableExtra("CHAR");

        String c_name = char_o.char_name;
        TextView name = (TextView) findViewById(R.id.charlist_char_name);
        name.setText( String.format(Locale.getDefault(), "Имя игрока: %s", c_name) );
        String cr_name = char_o.chronic_name;
        TextView chronic = (TextView) findViewById(R.id.charlist_chronic_name);
        chronic.setText( String.format(Locale.getDefault(), "Хроника: %s", cr_name) );

        attr_helper = (TextView) findViewById(R.id.attr_show_helper);
        abl_helper  = (TextView) findViewById(R.id.abl_show_helper);
        bkg_helper  = (TextView) findViewById(R.id.bkg_show_helper);
        sph_helper  = (TextView) findViewById(R.id.sph_show_helper);
        dis_helper  = (TextView) findViewById(R.id.dis_show_helper);
        gft_helper  = (TextView) findViewById(R.id.gft_show_helper);

        fill_map();
        update_helpers();

        gifts       = findViewById(R.id.gifts);
        spheres     = findViewById(R.id.spheres);
        disciplines = findViewById(R.id.disciplines);

        spinner_gd = (Spinner) findViewById(R.id.charlist_gods);
        adapter_gd = ArrayAdapter.createFromResource(this,
                R.array.gods_array, android.R.layout.simple_spinner_item);
        adapter_gd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_gd.setAdapter(adapter_gd);
        spinner_gd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String c_god = spinner_gd.getSelectedItem().toString();
                show_god_disciplines( c_god );
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });


        final Spinner spinner_al = (Spinner) findViewById(R.id.charlist_alignment);
        final ArrayAdapter<CharSequence> adapter_al = ArrayAdapter.createFromResource(this,
                R.array.alignments_array, android.R.layout.simple_spinner_item);
        adapter_al.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_al.setAdapter(adapter_al);
        spinner_al.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                fix_gods_list( spinner_al.getSelectedItem().toString() );

            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });


        final Spinner spinner_cl = (Spinner) findViewById(R.id.charlist_class);
        final ArrayAdapter<CharSequence> adapter_cl = ArrayAdapter.createFromResource(this,
                R.array.classes_array, android.R.layout.simple_spinner_item);
        adapter_cl.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_cl.setAdapter(adapter_cl);
        spinner_cl.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String c_class = spinner_cl.getSelectedItem().toString();
                switch (c_class) {
                    case "Воин":
                        show_sp("rage");
                        gifts.setVisibility(View.VISIBLE);
                        spheres.setVisibility(View.GONE);
                        disciplines.setVisibility(View.GONE);
                        spinner_gd.setVisibility(View.GONE);
                        break;
                    case "Маг":
                        show_sp("wp");
                        gifts.setVisibility(View.GONE);
                        spheres.setVisibility(View.VISIBLE);
                        disciplines.setVisibility(View.GONE);
                        spinner_gd.setVisibility(View.GONE);
                        break;
                    case "Жрец":
                        show_sp("faith");
                        gifts.setVisibility(View.GONE);
                        spheres.setVisibility(View.GONE);
                        disciplines.setVisibility(View.VISIBLE);
                        spinner_gd.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    private void update_helpers() {
        if ( char_o.Generated == 0 ) {
            attr_helper.setText(Arrays.toString(char_o.attr));
            abl_helper.setText(Arrays.toString(char_o.abl));
            bkg_helper.setText(String.format(Locale.getDefault(), " [%d]", char_o.bkg_gen_points));
            sph_helper.setText(String.format(Locale.getDefault(), " [%d]", char_o.cf_gen_points));
            dis_helper.setText(String.format(Locale.getDefault(), " [%d]", char_o.cf_gen_points));
            gft_helper.setText(String.format(Locale.getDefault(), " [%d]", char_o.cf_gen_points));
        }
        else if ( char_o.Generated == 1 ) {
            attr_helper.setText(String.format(Locale.getDefault(), "Очков [%d], цена 5",
                    char_o.free_points));
            abl_helper.setText(String.format(Locale.getDefault(), "Очков [%d], цена 2",
                    char_o.free_points));
            bkg_helper.setText(String.format(Locale.getDefault(), "Очков [%d], цена 1",
                    char_o.free_points));
            sph_helper.setText(String.format(Locale.getDefault(), "Очков [%d], цена 7",
                    char_o.free_points));
            dis_helper.setText(String.format(Locale.getDefault(), "Очков [%d], цена 7",
                    char_o.free_points));
            gft_helper.setText(String.format(Locale.getDefault(), "Очков [%d], цена 7",
                    char_o.free_points));
        }
    }

    private void show_sp( String resource ) {
        String id_ref;
        int resID;
        CheckBox res;
        View sp_view;

        for ( String r : resources ) {
            id_ref = String.format(Locale.getDefault(), "checkBox_%s", r);
            resID  = getResources().getIdentifier(id_ref, "id", getPackageName());
            res    = (CheckBox) findViewById(resID);

            id_ref  = String.format(Locale.getDefault(), "sp_%s", r);
            resID   = getResources().getIdentifier(id_ref, "id", getPackageName());
            sp_view = findViewById(resID);

            if ( r.equals(resource) ) {
                sp_view.setVisibility(View.VISIBLE);
                res.setChecked(false);
            }
            else {
                sp_view.setVisibility(View.GONE);
                res.setChecked(true);
            }
        }
    }

    public void radio_button_clicked(View view) {
        String IdAsString = view.getResources().getResourceName(view.getId());
        Pattern p = Pattern.compile("_(\\p{Lower}+)_(\\p{Lower}+)(\\d+)$");
        Matcher m = p.matcher(IdAsString);
        String group;
        String name;
        Integer number;
        if( m.find() ){
            group = m.group(1);
            name = m.group(2);
            number = Integer.parseInt(m.group(3));
        }
        else {
            throw new RuntimeException( "Can't fine resource and value in " + IdAsString );
        }

        if ( char_o.Generated == 0 ) {
            number = char_o.save_new_values( group, name, number );
            set_range( group, name, number );
            unset_upper_range( group, name, number );
            generation_complete();
            switch (group) {
                case "attr":
                    attr_helper.setText(Arrays.toString(char_o.attr));
                    break;
                case "abl":
                    abl_helper.setText(Arrays.toString(char_o.abl));
                    break;
                case "bkg":
                    bkg_helper.setText(String.format(Locale.getDefault(), " [%d]", char_o.bkg_gen_points));
                    break;
                case "sph":
                    sph_helper.setText(String.format(Locale.getDefault(), " [%d]", char_o.cf_gen_points));
                    break;
                case "dis":
                    dis_helper.setText(String.format(Locale.getDefault(), " [%d]", char_o.cf_gen_points));
                    break;
                case "gft":
                    gft_helper.setText(String.format(Locale.getDefault(), " [%d]", char_o.cf_gen_points));
                    break;
            }
        }
        else if ( char_o.Generated == 1 ) {
            number = char_o.save_fp_values( group, name, number );
            set_range( group, name, number );
            unset_upper_range( group, name, number );
            update_helpers();
            fp_complete();
        }
    }

    private void generation_complete() {
        if ( ! char_o.check_gen_points() ) {
            AlertDialog.Builder builder = new AlertDialog.Builder(full_char_list.this);
            builder.setTitle("Генерация");
            builder.setMessage(R.string.go_to_free);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    finish_gen_points();
                }
            });
            builder.setNegativeButton("Cancel", null);
            builder.show();

        }
    }

    private void finish_gen_points() {
        char_o.Generated = 1;
        update_helpers();
        char_o.store_values();
    }

    private void fp_complete() {
        if ( char_o.free_points == 0 ) {
            AlertDialog.Builder builder = new AlertDialog.Builder(full_char_list.this);
            builder.setTitle("Генерация");
            builder.setMessage(R.string.finish_gen);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    finish_fp_points();
                }
            });
            builder.setNegativeButton("Cancel", null);
            builder.show();
        }
    }

    private void finish_fp_points() {
        char_o.Generated = 2;
        attr_helper.setVisibility(View.GONE);
        abl_helper.setVisibility(View.GONE);
        bkg_helper.setVisibility(View.GONE);
        sph_helper.setVisibility(View.GONE);
        dis_helper.setVisibility(View.GONE);
        gft_helper.setVisibility(View.GONE);
        char_o.store_values();
    }

    private void set_range( String group, String name, Integer number ) {
        for ( Integer i = 1; i <= number; i++ ){
            mark_dots( group, name, i, R.drawable.ic_fiber_manual_record_black_24dp );
        }
    }

    private void unset_upper_range( String group, String name, Integer number ) {
        Integer limit = 5;
        if ( group.equals("button") ) {
            limit = 10;
        }
        for ( Integer i = (number + 1); i <= limit; i++ ){
            mark_dots( group, name, i, R.drawable.ic_radio_button_unchecked_black_24dp );
        }
    }

    private void mark_dots( String group, String name, Integer number, int pic ) {
        String id_ref = String.format(Locale.getDefault(), "rb_%s_%s%d", group,  name, number);
        int resID = getResources().getIdentifier(id_ref, "id", getPackageName());
        ImageView img = (ImageView) findViewById(resID);
        assert img != null;
        img.setImageResource(pic);
    }

    private void fix_gods_list( String algmnt ) {
        switch (algmnt) {
            case "Законопослушный-Добрый":
                adapter_gd = ArrayAdapter.createFromResource(this,
                        R.array.gods_lg_array, android.R.layout.simple_spinner_item);
                break;
            case "Нейтральный-Добрый":
                adapter_gd = ArrayAdapter.createFromResource(this,
                        R.array.gods_ng_array, android.R.layout.simple_spinner_item);
                break;
            case "Хаотичный-Добрый":
                adapter_gd = ArrayAdapter.createFromResource(this,
                        R.array.gods_cg_array, android.R.layout.simple_spinner_item);
                break;
            case "Законопослушный-Нейтральный":
                adapter_gd = ArrayAdapter.createFromResource(this,
                        R.array.gods_ln_array, android.R.layout.simple_spinner_item);
                break;
            case "Нейтральный":
                adapter_gd = ArrayAdapter.createFromResource(this,
                        R.array.gods_nn_array, android.R.layout.simple_spinner_item);
                break;
            case "Хаотичный-Нейтральный":
                adapter_gd = ArrayAdapter.createFromResource(this,
                        R.array.gods_cn_array, android.R.layout.simple_spinner_item);
                break;
            case "Законопослушный-Злой":
                adapter_gd = ArrayAdapter.createFromResource(this,
                        R.array.gods_le_array, android.R.layout.simple_spinner_item);
                break;
            case "Нейтральный-Злой":
                adapter_gd = ArrayAdapter.createFromResource(this,
                        R.array.gods_ne_array, android.R.layout.simple_spinner_item);
                break;
            case "Хаотичный-Злой":
                adapter_gd = ArrayAdapter.createFromResource(this,
                        R.array.gods_ce_array, android.R.layout.simple_spinner_item);
                break;
        }
        adapter_gd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_gd.setAdapter(adapter_gd);
    }

    private void show_god_disciplines( String god_name ) {
        for (String d: discs) {
            int resID = getResources().getIdentifier(d, "id", getPackageName());
            View disc = findViewById(resID);
            disc.setVisibility(View.GONE);
        }

        List<String> g_discs = new ArrayList<>();
        switch (god_name) {
            case "Акади":
                g_discs.add("dis_celerity");
                g_discs.add("dis_conveyance");
                g_discs.add("dis_weathercraft");
                break;
            case "Грумбар":
                g_discs.add("dis_fortitude");
                g_discs.add("dis_temporis");
                break;
            case "Истишия":
                g_discs.add("dis_potence");
                g_discs.add("dis_alchemistry");
                g_discs.add("dis_melpominee");
                break;
            case "Келемвор":
                g_discs.add("dis_conveyance");
                g_discs.add("dis_auspex");
                g_discs.add("dis_temporis");
                break;
            case "Коссут":
                g_discs.add("dis_potence");
                g_discs.add("dis_hellfire");
                g_discs.add("dis_dominate");
                break;
            case "Латандер":
                g_discs.add("dis_healing");
                g_discs.add("dis_potence");
                g_discs.add("dis_presence");
                break;
            case "Мистра":
                g_discs.add("dis_hellfire");
                g_discs.add("dis_alchemistry");
                g_discs.add("dis_enchantment");
                break;
            case "Огма":
                g_discs.add("dis_conveyance");
                g_discs.add("dis_alchemistry");
                g_discs.add("dis_enchantment");
                break;
            case "Сайрик":
                g_discs.add("dis_dominate");
                g_discs.add("dis_chimerstry");
                g_discs.add("dis_dementation");
                break;
            case "Суне":
                g_discs.add("dis_presence");
                g_discs.add("dis_healing");
                g_discs.add("dis_celerity");
                break;
            case "Сильванус":
                g_discs.add("dis_animalism");
                g_discs.add("dis_protean");
                g_discs.add("dis_serpentis");
                break;
            case "Талос":
                g_discs.add("dis_hellfire");
                g_discs.add("dis_weathercraft");
                g_discs.add("dis_dominate");
                break;
            case "Темпус":
                g_discs.add("dis_potence");
                g_discs.add("dis_healing");
                g_discs.add("dis_fortitude");
                break;
            case "Тир":
                g_discs.add("dis_auspex");
                g_discs.add("dis_fortitude");
                break;
            case "Чонти":
                g_discs.add("dis_weathercraft");
                g_discs.add("dis_healing");
                g_discs.add("dis_animalism");
                break;
            case "Шар":
                g_discs.add("dis_dominate");
                g_discs.add("dis_obtenebration");
                g_discs.add("dis_obfuscate");
                break;
            case "Бэйн":
                g_discs.add("dis_quietus");
                g_discs.add("dis_dominate");
                g_discs.add("dis_daimoinon");
                break;
        }

        for( String d: g_discs ) {
            int resID = getResources().getIdentifier(d, "id", getPackageName());
            View disc = findViewById(resID);
            disc.setVisibility(View.VISIBLE);
        }
    }


    private void fill_map() {
        Integer val;

        // attributes
        for ( String resource : char_o.phis_attr.keySet() ) {
            val = char_o.phis_attr.get(resource);
            set_range( "attr", resource, val );
            unset_upper_range( "attr", resource, val );
        }
        for ( String resource : char_o.soc_attr.keySet() ) {
            val = char_o.soc_attr.get(resource);
            set_range( "attr", resource, val );
            unset_upper_range( "attr", resource, val );
        }
        for ( String resource : char_o.men_attr.keySet() ) {
            val = char_o.men_attr.get(resource);
            set_range( "attr", resource, val );
            unset_upper_range( "attr", resource, val );
        }

        // abilities
        for ( String resource : char_o.tal_abl.keySet() ) {
            if ( resource.equals("gen_points") ) {
                continue;
            }

            val = char_o.tal_abl.get(resource);
            set_range( "abl", resource, val );
            unset_upper_range( "abl", resource, val );
        }
        for ( String resource : char_o.skl_abl.keySet() ) {
            if ( resource.equals("gen_points") ) {
                continue;
            }

            val = char_o.skl_abl.get(resource);
            set_range( "abl", resource, val );
            unset_upper_range( "abl", resource, val );
        }
        for ( String resource : char_o.kng_abl.keySet() ) {
            if ( resource.equals("gen_points") ) {
                continue;
            }

            val = char_o.kng_abl.get(resource);
            set_range( "abl", resource, val );
            unset_upper_range( "abl", resource, val );
        }

        // backgrounds
        for ( String resource : char_o.bkg.keySet() ) {
            val = char_o.bkg.get(resource);
            set_range( "bkg", resource, val );
            unset_upper_range( "bkg", resource, val );
        }

        // class features
        for ( String resource : char_o.sph.keySet() ) {
            val = char_o.sph.get(resource);
            set_range( "sph", resource, val );
            unset_upper_range( "sph", resource, val );
        }
        for ( String resource : char_o.gft.keySet() ) {
            val = char_o.gft.get(resource);
            set_range( "gft", resource, val );
            unset_upper_range( "gft", resource, val );
        }
        for ( String resource : char_o.dis.keySet() ) {
            val = char_o.dis.get(resource);
            set_range( "dis", resource, val );
            unset_upper_range( "dis", resource, val );
        }

        // special parameters
        Pattern p = Pattern.compile("perm_(\\p{Lower}+)");
        Matcher m;
        String res;
        for ( String resource : char_o.sp_resources.keySet() ) {
            val = char_o.sp_resources.get(resource);
            m = p.matcher(resource);
            if (m.find()) {
                res = m.group(1);
                set_range( "button", res, val );
                unset_upper_range( "button", res, val );
            }
            else {
                set_free_range( resource, val );
                unset_free_upper_range( resource, val );
            }
        }

        set_health_pen();
    }

    private void mark_checkboxes( String name, int id, int pic ) {
        String id_ref = String.format(Locale.getDefault(), "image_%s%d", name, id);
        int resID = getResources().getIdentifier(id_ref, "id", getPackageName());
        ImageView img = (ImageView) findViewById(resID);
        assert img != null;
        img.setImageResource(pic);

    }

    private void set_free_range( String name, int id ) {
        while ( id > 0 ) {
            mark_checkboxes( name, id, R.drawable.ic_check_box_black_24dp );
            id--;
        }
    }

    private void unset_free_upper_range( String name, int id ) {
        Integer limit = 10;
        if ( name.equals("health") ) {
            limit = 14;
        }
        id++;
        while( id <= limit ) {
            mark_checkboxes( name, id, R.drawable.ic_crop_din_black_24dp );
            id++;
        }
    }

    public void plus_button(View view) {
        String IdAsString = view.getResources().getResourceName(view.getId());
        Pattern p = Pattern.compile("/(\\p{Lower}+)_");
        Matcher m = p.matcher(IdAsString);
        String special_resource;
        if( m.find() ){
            special_resource = m.group(1);
        }
        else {
            throw new RuntimeException( "Can't fine resource in " + IdAsString );
        }

        Integer sp_value = char_o.sp_resources.get( special_resource );

        if ( special_resource.equals("health") ) {
            sp_value++;
            if ( sp_value > 14 ) {
                //noinspection UnusedAssignment
                sp_value = 14;
                return ;
            }
            char_o.sp_resources.put(special_resource, sp_value);
            mark_checkboxes( special_resource, sp_value, R.drawable.ic_check_box_black_24dp );
            set_health_pen();
            return ;
        }

        Integer limit = char_o.sp_resources.get( "perm_" + special_resource );
        if ( (special_resource.equals("faith")) || (sp_value < limit) ) {
            sp_value++;
            char_o.sp_resources.put(special_resource, sp_value);
            mark_checkboxes( special_resource, sp_value, R.drawable.ic_check_box_black_24dp );
        }
    }

    public void minus_button(View view) {
        String IdAsString = view.getResources().getResourceName(view.getId());
        Pattern p = Pattern.compile("/(\\p{Lower}+)_");
        Matcher m = p.matcher(IdAsString);
        String special_resource;
        if( m.find() ){
            special_resource = m.group(1);
        }
        else {
            throw new RuntimeException( "Can't fine resource in " + IdAsString );
        }

        Integer sp_value = char_o.sp_resources.get( special_resource );

        if ( special_resource.equals("health") ) {
            if ( sp_value == 0 ) {
                return ;
            }
            mark_checkboxes( special_resource, sp_value, R.drawable.ic_crop_din_black_24dp );
            sp_value--;
            char_o.sp_resources.put(special_resource, sp_value);
            set_health_pen();
            return ;
        }

        if ( sp_value > 0 ) {
            mark_checkboxes( special_resource, sp_value, R.drawable.ic_crop_din_black_24dp );
            sp_value--;
            char_o.sp_resources.put(special_resource, sp_value);

        }
    }

    private void set_health_pen() {
        TextView t = (TextView)findViewById(R.id.special_limit_health);
        assert t != null;
        t.setTextColor( Color.RED );
        Integer pen = char_o.get_health_pen();
        switch (pen) {
            case 0:
                t.setTextColor(Color.parseColor("#33b5e5"));
                t.setText(R.string.char_0);
                break;
            case 1:
                t.setText(R.string.char_m_1);
                break;
            case 2:
                t.setText(R.string.char_m_2);
                break;
            case 5:
                t.setText(R.string.char_m_5);
                break;
            case 10:
                t.setText(R.string.char_m_10);
                break;
        }
    }

    public void reset_button(View view) {
        String IdAsString = view.getResources().getResourceName(view.getId());
        Pattern p = Pattern.compile("/(\\p{Lower}+)_");
        Matcher m = p.matcher(IdAsString);
        String special_resource;
        if( m.find() ) {
            special_resource = m.group(1);
        }
        else {
            throw new RuntimeException( "Can't fine resource in " + IdAsString );
        }

        if ( special_resource.equals("health") ) {
            Integer sp_value = char_o.sp_resources.get(special_resource);

            TextView t = (TextView)findViewById(R.id.special_limit_health);
            assert t != null;
            t.setTextColor( Color.parseColor("#33b5e5") );
            t.setText( "0" );

            while ( sp_value > 0 ) {
                mark_checkboxes( special_resource, sp_value, R.drawable.ic_crop_din_black_24dp );
                sp_value--;
            }
            char_o.sp_resources.put(special_resource, 0);
            return ;
        }

        if ( special_resource.equals("rage") ) {
            char_o.sp_resources.put(special_resource, 1);
            for ( Integer i = 2; i <= 10; i++ ) {
                mark_checkboxes( special_resource, i, R.drawable.ic_crop_din_black_24dp );
            }
            mark_checkboxes( special_resource, 1, R.drawable.ic_check_box_black_24dp );
        }
        else {
            Integer perm_val = char_o.sp_resources.get( "perm_" + special_resource );
            char_o.sp_resources.put( special_resource, perm_val );
            for ( Integer i = 1; i <= perm_val; i++ ) {
                mark_checkboxes( special_resource, i, R.drawable.ic_check_box_black_24dp );
            }
            perm_val++;
            for ( Integer i = perm_val; i <= 10; i++ ) {
                mark_checkboxes( special_resource, i, R.drawable.ic_crop_din_black_24dp );
            }
        }
    }

    public void onCheckboxClicked_sp_resources(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        String IdAsString = view.getResources().getResourceName(view.getId());
        Pattern p = Pattern.compile("_(\\p{Lower}+)$");
        Matcher m = p.matcher(IdAsString);
        String special_resource;
        if( m.find() ){
            special_resource = m.group(1);
        }
        else {
            throw new RuntimeException( "Can't find resource name in " + IdAsString );
        }

        String id_ref = String.format(Locale.getDefault(), "sp_%s", special_resource);
        int resID = getResources().getIdentifier(id_ref, "id", getPackageName());
        View sp_view = findViewById(resID);

        if( checked ){
            assert sp_view != null;
            sp_view.setVisibility(View.GONE);
        }
        else {
            assert sp_view != null;
            sp_view.setVisibility(View.VISIBLE);
        }
    }

    public void save_char(MenuItem item) {
        Context context = this;
        char_o.flush( context );
    }

    public void run_load_screen(MenuItem item) {

        Intent intent = new Intent(this, load_char.class);
        startActivity(intent);
        finish();
    }

    public void new_char(MenuItem item) {
        Intent intent = new Intent(this, enter_name.class);
        startActivity(intent);
        finish();
    }
}
