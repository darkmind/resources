package com.example.asm.resources;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.v7.app.ActionBar;
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
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class full_char_list extends AppCompatActivity {
    private boolean mVisible;

    View gifts;
    View spheres;
    View disciplines;
    Spinner spinner_gd;
    character char_o;
    private Map<String, Integer> sp_resources = new HashMap<>();
    ArrayAdapter<CharSequence> adapter_gd;

    private String[] discs = { "dis_animalism", "dis_auspex", "dis_celerity", "dis_chimerstry",
            "dis_daimoinon", "dis_dementation", "dis_dominate", "dis_fortitude", "dis_melpominee",
            "dis_obfuscate", "dis_obtenebration", "dis_potence", "dis_presence", "dis_protean",
            "dis_quietus", "dis_serpentis", "dis_temporis", "dis_thanatosis", "dis_vicissitude",
            "dis_alchemistry", "dis_conveyance", "dis_enchantment", "dis_healing", "dis_hellfire",
            "dis_weathercraft" };

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
        sp_resources = char_o.get_sp_res();

        String c_name = char_o.char_name;
        TextView name = (TextView) findViewById(R.id.charlist_char_name);
        name.setText( "Имя игрока: " + c_name );

        String cr_name = char_o.chronic_name;
        TextView chronic = (TextView) findViewById(R.id.charlist_chronic_name);
        chronic.setText(  "Хроника: " + cr_name );

        fill_map();

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
                if( c_class.equals("Воин") ) {
                    gifts.setVisibility(View.VISIBLE);
                    spheres.setVisibility(View.GONE);
                    disciplines.setVisibility(View.GONE);
                    spinner_gd.setVisibility(View.GONE);
                }
                else if( c_class.equals("Маг") ) {
                    gifts.setVisibility(View.GONE);
                    spheres.setVisibility(View.VISIBLE);
                    disciplines.setVisibility(View.GONE);
                    spinner_gd.setVisibility(View.GONE);
                }
                else if( c_class.equals("Жрец") ) {
                    gifts.setVisibility(View.GONE);
                    spheres.setVisibility(View.GONE);
                    disciplines.setVisibility(View.VISIBLE);
                    spinner_gd.setVisibility(View.VISIBLE);
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

        if( save_new_values( group, name, number )) {
            set_range( group, name, number );
            unset_upper_range( group, name, number );
        }
    }

    private boolean save_new_values( String group, String name, Integer number ) {
        return true;
    }

    private void set_range( String group, String name, Integer number ) {
        for ( Integer i = 1; i <= number; i++ ){
            mark_dots( group, name, i, R.drawable.ic_fiber_manual_record_black_24dp );
        }
    }

    private void unset_upper_range( String group, String name, Integer number ) {
        for ( Integer i = (number + 1); i <= 5; i++ ){
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
        if( algmnt.equals("Законопослушный-Добрый") ) {
            adapter_gd = ArrayAdapter.createFromResource(this,
                    R.array.gods_lg_array, android.R.layout.simple_spinner_item);
        }
        else if( algmnt.equals("Нейтральный-Добрый") ) {
            adapter_gd = ArrayAdapter.createFromResource(this,
                    R.array.gods_ng_array, android.R.layout.simple_spinner_item);
        }
        else if( algmnt.equals("Хаотичный-Добрый") ) {
            adapter_gd = ArrayAdapter.createFromResource(this,
                    R.array.gods_cg_array, android.R.layout.simple_spinner_item);
        }
        else if( algmnt.equals("Законопослушный-Нейтральный") ) {
            adapter_gd = ArrayAdapter.createFromResource(this,
                    R.array.gods_ln_array, android.R.layout.simple_spinner_item);
        }
        else if( algmnt.equals("Нейтральный") ) {
            adapter_gd = ArrayAdapter.createFromResource(this,
                    R.array.gods_nn_array, android.R.layout.simple_spinner_item);
        }
        else if( algmnt.equals("Хаотичный-Нейтральный") ) {
            adapter_gd = ArrayAdapter.createFromResource(this,
                    R.array.gods_cn_array, android.R.layout.simple_spinner_item);
        }
        else if( algmnt.equals("Законопослушный-Злой") ) {
            adapter_gd = ArrayAdapter.createFromResource(this,
                    R.array.gods_le_array, android.R.layout.simple_spinner_item);
        }
        else if( algmnt.equals("Нейтральный-Злой") ) {
            adapter_gd = ArrayAdapter.createFromResource(this,
                    R.array.gods_ne_array, android.R.layout.simple_spinner_item);
        }
        else if( algmnt.equals("Хаотичный-Злой") ) {
            adapter_gd = ArrayAdapter.createFromResource(this,
                    R.array.gods_ce_array, android.R.layout.simple_spinner_item);
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

        List<String> g_discs = new ArrayList<String>();
        if( god_name.equals("Акади") ) {
            g_discs.add( "dis_celerity" );
            g_discs.add( "dis_conveyance" );
            g_discs.add( "dis_weathercraft" );
        }
        else if( god_name.equals("Грумбар") ) {
            g_discs.add( "dis_fortitude" );
            g_discs.add( "dis_temporis" );
        }
        else if( god_name.equals("Истишия") ) {
            g_discs.add( "dis_potence" );
            g_discs.add( "dis_alchemistry" );
            g_discs.add( "dis_melpominee" );
        }
        else if( god_name.equals("Келемвор") ) {
            g_discs.add( "dis_conveyance" );
            g_discs.add( "dis_auspex" );
            g_discs.add( "dis_temporis" );
        }
        else if( god_name.equals("Коссут") ) {
            g_discs.add( "dis_potence" );
            g_discs.add( "dis_hellfire" );
            g_discs.add( "dis_dominate" );
        }
        else if( god_name.equals("Латандер") ) {
            g_discs.add( "dis_healing" );
            g_discs.add( "dis_potence" );
            g_discs.add( "dis_presence" );
        }
        else if( god_name.equals("Мистра") ) {
            g_discs.add( "dis_hellfire" );
            g_discs.add( "dis_alchemistry" );
            g_discs.add( "dis_enchantment" );
        }
        else if( god_name.equals("Огма") ) {
            g_discs.add( "dis_conveyance" );
            g_discs.add( "dis_alchemistry" );
            g_discs.add( "dis_enchantment" );
        }
        else if( god_name.equals("Сайрик") ) {
            g_discs.add( "dis_dominate" );
            g_discs.add( "dis_chimerstry" );
            g_discs.add( "dis_dementation" );
        }
        else if( god_name.equals("Суне") ) {
            g_discs.add( "dis_presence" );
            g_discs.add( "dis_healing" );
            g_discs.add( "dis_celerity" );
        }
        else if( god_name.equals("Сильванус") ) {
            g_discs.add( "dis_animalism" );
            g_discs.add( "dis_protean" );
            g_discs.add( "dis_serpentis" );
        }
        else if( god_name.equals("Талос") ) {
            g_discs.add( "dis_hellfire" );
            g_discs.add( "dis_weathercraft" );
            g_discs.add( "dis_dominate" );
        }
        else if( god_name.equals("Темпус") ) {
            g_discs.add( "dis_potence" );
            g_discs.add( "dis_healing" );
            g_discs.add( "dis_fortitude" );
        }
        else if( god_name.equals("Тир") ) {
            g_discs.add( "dis_auspex" );
            g_discs.add( "dis_fortitude" );
        }
        else if( god_name.equals("Чонти") ) {
            g_discs.add( "dis_weathercraft" );
            g_discs.add( "dis_healing" );
            g_discs.add( "dis_animalism" );
        }
        else if( god_name.equals("Шар") ) {
            g_discs.add( "dis_dominate" );
            g_discs.add( "dis_obtenebration" );
            g_discs.add( "dis_obfuscate" );
        }
        else if( god_name.equals("Бэйн") ) {
            g_discs.add( "dis_quietus" );
            g_discs.add( "dis_dominate" );
            g_discs.add( "dis_daimoinon" );
        }

        for( String d: g_discs ) {
            int resID = getResources().getIdentifier(d, "id", getPackageName());
            View disc = findViewById(resID);
            disc.setVisibility(View.VISIBLE);
        }
    }


    private void fill_map() {
        Pattern p = Pattern.compile("perm_(\\p{Lower}+)");
        Matcher m;
        String res;
        Integer val;
        for (String resource : sp_resources.keySet()) {
            val = sp_resources.get(resource);
            m = p.matcher(resource);
            if (m.find()) {
                res = m.group(1);
                set_range( res, val );
                unset_upper_range( res, val );
            }
            else {
                set_free_range( resource, val );
                unset_free_upper_range( resource, val );
            }
        }

        // calculate health penalty
        Integer health_val = sp_resources.get("health");
        if ( health_val == 0 ) {
            return ;
        }

        TextView t = (TextView)findViewById(R.id.special_limit_health);
        assert t != null;
        if ( health_val <= 2 ) {
            t.setTextColor( Color.parseColor("#33b5e5") );
            t.setText( R.string.char_0 );
            return ;
        }

        t.setTextColor(Color.RED);
        if ( 2 < health_val && health_val < 7 ) {
            t.setText( R.string.char_m_1 );
        }
        else if ( 6 < health_val && health_val < 11 ) {
            t.setText( R.string.char_m_2 );
        }
        else if ( 10 < health_val && health_val < 13 ) {
            t.setText( R.string.char_m_5 );
        }
        else if ( 12 < health_val && health_val <= 14 ) {
            t.setText( R.string.char_m_10 );
        }
    }

    public void sp_radio_button_clicked(View view) {
        String IdAsString = view.getResources().getResourceName(view.getId());
        Pattern p = Pattern.compile("radio_button_(\\p{Lower}+)(\\d+)$");
        Matcher m = p.matcher(IdAsString);
        String name;
        int id;
        if( m.find() ){
            name = m.group(1);
            id = Integer.parseInt(m.group(2));
        }
        else {
            throw new RuntimeException( "Can't fine resource and value in " + IdAsString );
        }
        set_range( name, id );
        unset_upper_range( name, id );
        refresh_sp( name, id );
    }

    private void set_range( String name, int id ) {
        RadioButton b;
        String id_ref;
        int resID;
        while ( id > 0 ) {
            id_ref = String.format(Locale.getDefault(), "radio_button_%s%d", name, id);
            resID = getResources().getIdentifier(id_ref, "id", getPackageName());
            b = (RadioButton) findViewById(resID);
            assert b != null;
            b.setChecked(true);
            id--;
        }
    }

    private void unset_upper_range( String name, int id ) {
        RadioButton b;
        String id_ref;
        int resID;
        id++;
        while ( id <= 10 ) {
            id_ref = String.format(Locale.getDefault(), "radio_button_%s%d", name, id);
            resID = getResources().getIdentifier(id_ref, "id", getPackageName());
            b = (RadioButton) findViewById(resID);
            assert b != null;
            b.setChecked(false);
            id++;
        }
    }

    private void set_free_range( String name, int id ) {
        while( id > 0 ){
            String id_ref = String.format(Locale.getDefault(), "image_%s%d", name, id);
            int resID = getResources().getIdentifier(id_ref, "id", getPackageName());
            ImageView img = (ImageView) findViewById(resID);
            assert img != null;
            img.setImageResource(R.drawable.ic_check_box_black_24dp);
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
            String id_ref = String.format(Locale.getDefault(), "image_%s%d", name, id);
            int resID = getResources().getIdentifier(id_ref, "id", getPackageName());
            ImageView img = (ImageView) findViewById(resID);
            assert img != null;
            img.setImageResource(R.drawable.ic_crop_din_black_24dp);
            id++;
        }
    }

    private void refresh_sp ( String name, int id ) {
        sp_resources.put( "perm_" + name, id );
        if ( name.equals("faith") ) {
            return ;
        }

        Integer tmp_val = sp_resources.get( name );
        if ( id < tmp_val ) {
            sp_resources.put( name, id );
            while ( tmp_val > id ) {
                String id_ref = String.format(Locale.getDefault(), "image_%s%d", name, tmp_val);
                int resID = getResources().getIdentifier(id_ref, "id", getPackageName());
                ImageView img = (ImageView) findViewById(resID);
                assert img != null;
                img.setImageResource(R.drawable.ic_crop_din_black_24dp);
                tmp_val--;
            }
            sp_resources.put( name, tmp_val );
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

        Integer sp_value = sp_resources.get( special_resource );

        if ( special_resource.equals("health") ) {
            sp_value++;
            if ( sp_value > 14 ) {
                //noinspection UnusedAssignment
                sp_value = 14;
                return ;
            }
            sp_resources.put(special_resource, sp_value);

            String id_ref = String.format(Locale.getDefault(), "image_%s%d", special_resource, sp_value);
            int resID = getResources().getIdentifier(id_ref, "id", getPackageName());
            ImageView img = (ImageView) findViewById(resID);
            assert img != null;
            img.setImageResource(R.drawable.ic_check_box_black_24dp);

            TextView t = (TextView)findViewById(R.id.special_limit_health);
            assert t != null;
            if ( sp_value <= 2 ) {
                return ;
            }
            else if ( 2 < sp_value && sp_value < 7 ) {
                t.setTextColor(Color.RED);
                t.setText( R.string.char_m_1 );
                return ;
            }
            else if ( 6 < sp_value && sp_value < 11 ) {
                t.setTextColor(Color.RED);
                t.setText( R.string.char_m_2 );
                return ;
            }
            else if ( 10 < sp_value && sp_value < 13 ) {
                t.setTextColor(Color.RED);
                t.setText( R.string.char_m_5 );
                return ;
            }
            else if ( 12 < sp_value && sp_value <= 14 ) {
                t.setTextColor(Color.RED);
                t.setText( R.string.char_m_10 );
                return ;
            }
        }

        Integer limit = sp_resources.get( "perm_" + special_resource );
        if ( (special_resource.equals("faith")) || (sp_value < limit) ) {
            sp_value++;
            sp_resources.put(special_resource, sp_value);

            String id_ref = String.format(Locale.getDefault(), "image_%s%d", special_resource, sp_value);
            int resID = getResources().getIdentifier(id_ref, "id", getPackageName());
            ImageView img = (ImageView) findViewById(resID);
            assert img != null;
            img.setImageResource(R.drawable.ic_check_box_black_24dp);
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

        Integer sp_value = sp_resources.get( special_resource );

        if ( special_resource.equals("health") ) {
            if ( sp_value == 0 ) {
                return ;
            }

            String id_ref = String.format(Locale.getDefault(), "image_%s%d", special_resource, sp_value);
            int resID = getResources().getIdentifier(id_ref, "id", getPackageName());
            ImageView img = (ImageView) findViewById(resID);
            assert img != null;
            img.setImageResource(R.drawable.ic_crop_din_black_24dp);

            sp_value--;
            sp_resources.put(special_resource, sp_value);

            TextView t = (TextView)findViewById(R.id.special_limit_health);
            assert t != null;
            if ( sp_value <= 2 ) {
                t.setTextColor( Color.parseColor("#33b5e5") );
                t.setText( R.string.char_0 );
                return ;
            }
            else if ( 2 < sp_value && sp_value < 7 ) {
                t.setTextColor(Color.RED);
                t.setText( R.string.char_m_1 );
                return ;
            }
            else if ( 6 < sp_value && sp_value < 11 ) {
                t.setTextColor(Color.RED);
                t.setText( R.string.char_m_2 );
                return ;
            }
            else if ( 10 < sp_value && sp_value < 13 ) {
                t.setTextColor(Color.RED);
                t.setText( R.string.char_m_5 );
                return ;
            }
            else if ( 12 < sp_value && sp_value <= 14 ) {
                t.setTextColor(Color.RED);
                t.setText( R.string.char_m_10 );
                return ;
            }
        }

        if ( sp_value > 0 ) {
            String id_ref = String.format(Locale.getDefault(), "image_%s%d", special_resource, sp_value);
            int resID = getResources().getIdentifier(id_ref, "id", getPackageName());
            ImageView img = (ImageView) findViewById(resID);
            assert img != null;
            img.setImageResource(R.drawable.ic_crop_din_black_24dp);

            sp_value--;
            sp_resources.put(special_resource, sp_value);
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
            Integer sp_value = sp_resources.get(special_resource);

            TextView t = (TextView)findViewById(R.id.special_limit_health);
            assert t != null;
            t.setTextColor( Color.parseColor("#33b5e5") );
            t.setText( "0" );

            while ( sp_value > 0 ) {
                String id_ref = String.format(Locale.getDefault(), "image_%s%d", special_resource, sp_value);
                int resID = getResources().getIdentifier(id_ref, "id", getPackageName());
                ImageView img = (ImageView) findViewById(resID);
                assert img != null;
                img.setImageResource(R.drawable.ic_crop_din_black_24dp);
                sp_value--;
            }
            sp_resources.put(special_resource, 0);
            return ;
        }

        if ( special_resource.equals("rage") ) {
            sp_resources.put(special_resource, 1);
            for ( Integer i = 2; i <= 10; i++ ) {
                String id_ref = String.format(Locale.getDefault(), "image_%s%d", special_resource, i);
                int resID = getResources().getIdentifier(id_ref, "id", getPackageName());
                ImageView img = (ImageView) findViewById(resID);
                assert img != null;
                img.setImageResource(R.drawable.ic_crop_din_black_24dp);
            }
            ImageView img = (ImageView) findViewById(R.id.image_rage1);
            assert img != null;
            img.setImageResource(R.drawable.ic_check_box_black_24dp);
        }
        else {
            Integer perm_val = sp_resources.get( "perm_" + special_resource );
            sp_resources.put( special_resource, perm_val );
            for ( Integer i = 1; i <= perm_val; i++ ) {
                String id_ref = String.format(Locale.getDefault(), "image_%s%d", special_resource, i);
                int resID = getResources().getIdentifier(id_ref, "id", getPackageName());
                ImageView img = (ImageView) findViewById(resID);
                assert img != null;
                img.setImageResource(R.drawable.ic_check_box_black_24dp);
            }
            perm_val++;
            for ( Integer i = perm_val; i <= 10; i++ ) {
                String id_ref = String.format(Locale.getDefault(), "image_%s%d", special_resource, i);
                int resID = getResources().getIdentifier(id_ref, "id", getPackageName());
                ImageView img = (ImageView) findViewById(resID);
                assert img != null;
                img.setImageResource(R.drawable.ic_crop_din_black_24dp);
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
