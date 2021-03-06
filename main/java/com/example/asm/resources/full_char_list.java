package com.example.asm.resources;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
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
    private Menu menu_bar;

    private View gifts;
    private View spheres;
    private View disciplines;
    private Spinner spinner_gd;
    private Spinner spinner_al;
    private Spinner spinner_cl;

    private ArrayAdapter<CharSequence> adapter_gd;

    private character char_o;

    private TextView attr_helper;
    private TextView abl_helper;
    private TextView bkg_helper;
    private TextView sph_helper;
    private TextView dis_helper;
    private TextView gft_helper;
    private TextView wp_show_helper;

    private final List<String> g_discs = new ArrayList<>();

    private final String[] discs = { "animalism", "auspex", "celerity", "chimerstry",
            "daimoinon", "dementation", "dominate", "fortitude", "melpominee",
            "obfuscate", "obtenebration", "potence", "presence", "protean",
            "quietus", "serpentis", "temporis", "thanatosis", "vicissitude",
            "alchemistry", "conveyance", "enchantment", "healing", "hellfire",
            "weathercraft" };

    private final String[] resources = { "rage", "faith", "wp" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_char_list);

        char_o = (character) getIntent().getSerializableExtra("CHAR");

        String c_name = char_o.char_name;
        TextView name = (TextView) findViewById(R.id.charlist_char_name);
        name.setText( getResources().getString(R.string.charlist_char_name_hint, c_name) );

        String cr_name   = char_o.chronic_name;
        TextView chronic = (TextView) findViewById(R.id.charlist_chronic_name);
        chronic.setText( getResources().getString(R.string.charlist_chronic_name_hint, cr_name) );

        final EditText exp = (EditText) findViewById(R.id.exp);
        exp.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if ( exp.getText().toString().equals("") ) {
                        char_o.Exp = 0;
                    }
                    else {
                        char_o.Exp = Integer.parseInt(exp.getText().toString());
                    }
                }
                return false;
            }
        });

        gifts       = findViewById(R.id.gifts);
        spheres     = findViewById(R.id.spheres);
        disciplines = findViewById(R.id.disciplines);

        // list for gods
        spinner_gd = (Spinner) findViewById(R.id.charlist_gods);

        // list for alignment
        spinner_al = (Spinner) findViewById(R.id.charlist_alignment);

        // list for classes
        spinner_cl = (Spinner) findViewById(R.id.charlist_class);

        attr_helper    = (TextView) findViewById(R.id.attr_show_helper);
        abl_helper     = (TextView) findViewById(R.id.abl_show_helper);
        bkg_helper     = (TextView) findViewById(R.id.bkg_show_helper);
        sph_helper     = (TextView) findViewById(R.id.sph_show_helper);
        dis_helper     = (TextView) findViewById(R.id.dis_show_helper);
        gft_helper     = (TextView) findViewById(R.id.gft_show_helper);
        wp_show_helper = (TextView) findViewById(R.id.wp_show_helper);

        fill_map();
        update_helpers();

        spinner_al.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                char_o.alignment = spinner_al.getSelectedItem().toString();
                fix_gods_list( spinner_al.getSelectedItem().toString() );

            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        spinner_gd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                char_o.god = spinner_gd.getSelectedItem().toString();
                show_god_disciplines( spinner_gd.getSelectedItem().toString() );
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        spinner_cl.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String c_class = spinner_cl.getSelectedItem().toString();
                char_o.class_name = c_class;
                select_class( c_class );
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        final EditText armor_pen = (EditText) findViewById(R.id.armor_pen_val);
        armor_pen.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if ( armor_pen.getText().toString().equals("") ) {
                        char_o.armor[1] = 0;
                    }
                    else {
                        char_o.armor[1] = Integer.parseInt(armor_pen.getText().toString());
                    }
                    update_summary( "attr", "dexterity" );
                    remark_params();
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        menu_bar = menu;
        return true;
    }

    private void select_class( String c_class ) {
        switch (c_class) {
            case "Воин":
                show_sp("rage");
                init_sp_points( "rage", 1 );
                gifts.setVisibility(View.VISIBLE);
                spheres.setVisibility(View.GONE);
                disciplines.setVisibility(View.GONE);
                spinner_gd.setVisibility(View.GONE);
                if ( char_o.Generated == 0 ) {
                    findViewById( R.id.enlightenment ).setVisibility(View.GONE);
                    findViewById( R.id.concentration ).setVisibility(View.GONE);
                    if (char_o.tal_abl.get("enlightenment") == 1) {
                        set_range("abl", "enlightenment", 0);
                        unset_upper_range("abl", "enlightenment", 0);
                        char_o.return_m_point("tal_abl");
                    }
                    if (char_o.kng_abl.get("religion") == 1) {
                        set_range("abl", "religion", 0);
                        unset_upper_range("abl", "religion", 0);
                        char_o.return_m_point("kng_abl");
                    }
                    update_helpers();
                }
                break;
            case "Маг":
                show_sp("wp");
                gifts.setVisibility(View.GONE);
                spheres.setVisibility(View.VISIBLE);
                disciplines.setVisibility(View.GONE);
                spinner_gd.setVisibility(View.GONE);
                if ( char_o.Generated == 0 ) {
                    findViewById( R.id.enlightenment ).setVisibility(View.VISIBLE);
                    findViewById( R.id.concentration ).setVisibility(View.VISIBLE);
                    init_sp_points( "wp", 1 );
                    if (char_o.tal_abl.get("enlightenment") == 0) {
                        set_range("abl", "enlightenment", 1);
                        unset_upper_range("abl", "enlightenment", 1);
                        char_o.delay_charge("enlightenment", 1);
                    }
                    if (char_o.kng_abl.get("religion") == 1) {
                        set_range("abl", "religion", 0);
                        unset_upper_range("abl", "religion", 0);
                        char_o.return_m_point("kng_abl");
                    }
                    update_helpers();
                }

                break;
            case "Жрец":
                show_sp("faith");
                init_sp_points( "faith", (char_o.kng_abl.get("religion") * 2) );
                gifts.setVisibility(View.GONE);
                spheres.setVisibility(View.GONE);
                disciplines.setVisibility(View.VISIBLE);
                spinner_gd.setVisibility(View.VISIBLE);
                if ( char_o.Generated == 0 ) {
                    findViewById( R.id.enlightenment ).setVisibility(View.GONE);
                    findViewById( R.id.concentration ).setVisibility(View.GONE);
                    if (char_o.kng_abl.get("religion") == 0) {
                        set_range("abl", "religion", 1);
                        unset_upper_range("abl", "religion", 1);
                        char_o.delay_charge("religion", 1);
                    }
                    if (char_o.tal_abl.get("enlightenment") == 1) {
                        set_range("abl", "enlightenment", 0);
                        unset_upper_range("abl", "enlightenment", 0);
                        char_o.return_m_point("tal_abl");
                    }
                    update_helpers();
                }

                break;
        }
    }

    private void init_sp_points( String res, Integer num ) {
        switch (res) {
            case "rage":
                if (char_o.sp_resources.get(res) == 0) {
                    char_o.sp_resources.put(res, num);
                    set_free_range(res, num);
                    unset_free_upper_range(res, num);
                }
                break;
            case "wp":
                if (char_o.wp == 0) {
                    char_o.wp = num;
                    set_range("button", res, 1);
                    unset_upper_range("button", res, 1);
                }
                break;
            case "faith":
                char_o.sp_resources.put("perm_" + res, num);
                set_range("button", res, num);
                unset_upper_range("button", res, num);

                break;
        }
        reset_parameter( res );
    }

    private void update_helpers() {
        if ( char_o.Generated == 0 ) {
            attr_helper.setText(Arrays.toString(char_o.attr));
            abl_helper.setText(Arrays.toString(char_o.abl));
            bkg_helper.setText( getResources().getString(
                    R.string.gen_points_helper, char_o.bkg_gen_points) );
            sph_helper.setText(getResources().getString(
                    R.string.gen_points_helper, char_o.class_feature_gen_points) );
            dis_helper.setText(getResources().getString(
                    R.string.gen_points_helper, char_o.class_feature_gen_points) );
            gft_helper.setText(getResources().getString(
                    R.string.gen_points_helper, char_o.class_feature_gen_points) );
        }
        else if ( char_o.Generated == 1 ) {
            attr_helper.setText(getResources().getString(
                    R.string.free_points_helper, char_o.free_points, 5));
            abl_helper.setText(getResources().getString(
                    R.string.free_points_helper, char_o.free_points, 2));
            bkg_helper.setText(getResources().getString(
                    R.string.free_points_helper, char_o.free_points, 1));
            sph_helper.setText(getResources().getString(
                    R.string.free_points_helper, char_o.free_points, 7));
            dis_helper.setText(getResources().getString(
                    R.string.free_points_helper, char_o.free_points, 7));
            gft_helper.setText(getResources().getString(
                    R.string.free_points_helper, char_o.free_points, 7));
            wp_show_helper.setText(getResources().getString(
                    R.string.free_points_helper, char_o.free_points, 2));
        }
        else if ( char_o.Generated == 2 ) {
            attr_helper.setText( getResources().getString(R.string.price_attr_exp) );
            abl_helper.setText( getResources().getString(R.string.price_abl_exp) );
            bkg_helper.setText( getResources().getString(R.string.price_bkg_exp) );
            sph_helper.setText( getResources().getString(R.string.price_class_feature_exp) );
            dis_helper.setText( getResources().getString(R.string.price_class_feature_exp) );
            gft_helper.setText( getResources().getString(R.string.price_gft_exp) );
            wp_show_helper.setText( getResources().getString(R.string.price_wp_exp) );
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

    @SuppressWarnings("unused")
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
            if ( group.equals("button") ) {
                return ;
            }
            number = char_o.save_new_values( group, name, number );
            if ( number > 0 ) {
                set_range(group, name, number);
            }
            unset_upper_range( group, name, number );
            if ( char_o.class_name.equals("Жрец") ) {
                if ( name.equals("religion") ) {
                    init_sp_points( "faith", (char_o.kng_abl.get("religion") * 2) );
                }
            }
            generation_complete();
            switch (group) {
                case "attr":
                    attr_helper.setText(Arrays.toString(char_o.attr));
                    break;
                case "abl":
                    abl_helper.setText(Arrays.toString(char_o.abl));
                    break;
                case "bkg":
                    bkg_helper.setText(getResources().getString
                            (R.string.gen_points_helper, char_o.bkg_gen_points));
                    break;
                case "sph":
                    sph_helper.setText(getResources().getString(
                            R.string.gen_points_helper, char_o.class_feature_gen_points));
                    break;
                case "dis":
                    dis_helper.setText(getResources().getString(
                            R.string.gen_points_helper, char_o.class_feature_gen_points));
                    break;
                case "gft":
                    gft_helper.setText(getResources().getString(
                            R.string.gen_points_helper, char_o.class_feature_gen_points));
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
        else if ( char_o.Generated == 2 ) {
            if ( char_o.upping ) {
                number = char_o.save_exp_values( group, name, number );
                change_exp_value();
                set_range( group, name, number );
                unset_upper_range( group, name, number );
                exp_complete();
            }
        }

        update_summary( group, name );
    }

    @SuppressLint("SetTextI18n")
    private void change_exp_value() {
        EditText w_exp = (EditText) findViewById( R.id.exp );
        w_exp.setText( char_o.Exp.toString() );
    }

    private void generation_complete() {
        if ( ! char_o.check_gen_points() ) {
            AlertDialog.Builder builder = new AlertDialog.Builder(full_char_list.this);
            builder.setTitle(R.string.generation);
            builder.setMessage(R.string.go_to_free);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    finish_gen_points();
                }
            });
            builder.setNegativeButton(R.string.cancel, null);
            builder.show();

        }
    }

    private void finish_gen_points() {
        char_o.Generated = 1;
        wp_show_helper.setVisibility(View.VISIBLE);
        update_helpers();
        char_o.store_values();
        lock_unlock_names();
    }

    private void fp_complete() {
        if ( char_o.free_points == 0 ) {
            AlertDialog.Builder builder = new AlertDialog.Builder(full_char_list.this);
            builder.setTitle(R.string.generation);
            builder.setMessage(R.string.finish_gen);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    finish_fp_points();
                }
            });
            builder.setNegativeButton(R.string.cancel, null);
            builder.show();
        }
    }

    private void finish_fp_points() {
        char_o.Generated = 2;
        update_helpers();
        char_o.store_values();
        lock_unlock_names();

        change_exp_value();
        View exp = findViewById(R.id.exp_section);
        exp.setVisibility(View.VISIBLE);

        MenuItem up_exp = menu_bar.findItem(R.id.up_by_exp);
        up_exp.setVisible(true);
        up_exp = menu_bar.findItem(R.id.finish_up_by_exp);
        up_exp.setVisible(false);
    }

    private void exp_complete() {
        Integer wp = char_o.wp;
        if ( char_o.Exp < wp ) {
            AlertDialog.Builder builder = new AlertDialog.Builder(full_char_list.this);
            builder.setTitle(R.string.exp_title);
            builder.setMessage(R.string.exp_msg);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    finish_exp_points();
                }
            });
            builder.setNegativeButton(R.string.cancel, null);
            builder.show();
        }
    }

    private void finish_exp_points() {
        char_o.store_values();
        MenuItem up_exp = menu_bar.findItem(R.id.up_by_exp);
        up_exp.setVisible(true);
        up_exp = menu_bar.findItem(R.id.finish_up_by_exp);
        up_exp.setVisible(false);
    }

    @SuppressLint("SetTextI18n")
    private void update_summary(String group, String name ) {
        boolean penalized = false;
        Integer dexterity = char_o.phis_attr.get("dexterity");
        Integer penalty = 5 - char_o.armor[1];
        if (dexterity > penalty) {
            dexterity = penalty;
            penalized = true;
        }

        if ( group.equals("attr") ) {
            switch (name) {
                case "strength": {
                    update_atk_hands();
                    update_atk_melee( dexterity, penalized );
                    break;
                }
                case "dexterity": {
                    update_atk_melee( dexterity, penalized );
                    update_atk_range( dexterity, penalized );
                    update_atk_throw( dexterity, penalized );
                    update_def( dexterity, penalized );
                    update_init( dexterity, penalized );
                    break;
                }
                case "wits": {
                    update_def( dexterity, penalized );
                    update_init( dexterity, penalized );
                    break;
                }
            }
        }
        else if ( group.equals("abl") ) {
            switch (name) {
                case "brawl": {
                    update_atk_hands();
                    break;
                }
                case "melee": {
                    update_atk_melee( dexterity, penalized );
                    break;
                }
                case "firearms": {
                    update_atk_range( dexterity, penalized );
                    break;
                }
                case "athletics": {
                    update_atk_throw( dexterity, penalized );
                    break;
                }
            }
        }
    }

    private void remark_params() {
        set_range("attr", "dexterity", char_o.phis_attr.get("dexterity"));
    }

    private void update_init( Integer dexterity, boolean penalized ) {
        Integer value = dexterity + char_o.men_attr.get("wits");
        TextView tv   = (TextView) findViewById(R.id.init);
        tv.setTextColor( Color.WHITE );
        if ( penalized ) {
            tv.setTextColor( Color.RED );
        }
        tv.setText(getResources().getString(R.string.init, value));
    }

    private void update_def( Integer dexterity, boolean penalized ) {
        Integer value = dexterity;
        if (dexterity > char_o.men_attr.get("wits")) {
            value = char_o.men_attr.get("wits");
        }
        TextView tv   = (TextView) findViewById(R.id.def);
        tv.setTextColor( Color.WHITE );
        if ( penalized ) {
            tv.setTextColor( Color.RED );
        }
        tv.setText(getResources().getString(R.string.def, value));
    }

    private void update_atk_hands() {
        Integer value = char_o.phis_attr.get("strength") + char_o.tal_abl.get("brawl");
        TextView tv   = (TextView) findViewById(R.id.atk_hands);
        tv.setText(getResources().getString(R.string.atk_hands, value));
    }

    private void update_atk_melee( Integer dexterity, boolean penalized ) {
        Integer value_s = char_o.phis_attr.get("strength") + char_o.skl_abl.get("melee");
        Integer value_d = dexterity + char_o.skl_abl.get("melee");
        TextView tv     = (TextView) findViewById(R.id.atk_melee);
        tv.setTextColor( Color.WHITE );
        if ( penalized ) {
            tv.setTextColor( Color.RED );
        }
        tv.setText(getResources().getString(R.string.atk_melee, value_s, value_d));
    }

    private void update_atk_range( Integer dexterity, boolean penalized ) {
        Integer value = dexterity + char_o.skl_abl.get("firearms");
        TextView tv   = (TextView) findViewById(R.id.atk_range);
        tv.setTextColor( Color.WHITE );
        if ( penalized ) {
            tv.setTextColor( Color.RED );
        }
        tv.setText(getResources().getString(R.string.atk_range, value));
    }

    private void update_atk_throw( Integer dexterity, boolean penalized ) {
        Integer value = dexterity + char_o.tal_abl.get("athletics");
        TextView tv   = (TextView) findViewById(R.id.atk_throw);
        tv.setTextColor( Color.WHITE );
        if ( penalized ) {
            tv.setTextColor( Color.RED );
        }
        tv.setText(getResources().getString(R.string.atk_throw, value));
    }

    private void set_range( String group, String name, Integer number ) {
        for ( Integer i = 1; i <= number; i++ ){
            mark_dots( group, name, i, R.drawable.filled );
        }

        if ( group.equals("attr") && name.equals("dexterity") ) {
            Integer penalty = 5 - char_o.armor[1];
            if (number > penalty) {
                for (Integer i = (penalty + 1); i <= number; i++) {
                    mark_dots(group, name, i, R.drawable.pen);
                }
            }
        }
    }

    private void unset_upper_range( String group, String name, Integer number ) {
        Integer limit = 5;
        if ( group.equals("button") ) {
            limit = 10;
        }
        for ( Integer i = (number + 1); i <= limit; i++ ){
            mark_dots( group, name, i, R.drawable.empty );
        }
    }

    private void mark_dots( String group, String name, Integer number, int pic ) {
        if ( number > 0 ) {
            String id_ref = String.format(Locale.getDefault(), "rb_%s_%s%d", group, name, number);
            int resID = getResources().getIdentifier(id_ref, "id", getPackageName());
            ImageView img = (ImageView) findViewById(resID);
            assert img != null;
            img.setImageResource(pic);
        }
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

        if ( spinner_gd.getAdapter() == null ) {
            spinner_gd.setAdapter(adapter_gd);
        }

        ArrayList<String> new_list = new ArrayList<>();
        for (int i = 0; i < adapter_gd.getCount(); i++) {
            new_list.add(adapter_gd.getItem(i).toString());
        }

        ArrayList<String> old_list = new ArrayList<>();
        for (int i = 0; i < spinner_gd.getAdapter().getCount(); i++) {
            old_list.add(spinner_gd.getAdapter().getItem(i).toString());
        }

        if (!old_list.toString().equals(new_list.toString())) {
            spinner_gd.setAdapter(adapter_gd);
        }
    }

    private void show_god_disciplines( String god_name ) {
        for (String d: discs) {
            if ( d.equals("alchemistry") ) {
                d = "dis_alchemistry";
            }
            int resID = getResources().getIdentifier(d, "id", getPackageName());
            View disc = findViewById(resID);
            disc.setVisibility(View.GONE);
        }
        g_discs.clear();
        switch (god_name) {
            case "Акади":
                g_discs.add("celerity");
                g_discs.add("conveyance");
                g_discs.add("weathercraft");
                break;
            case "Грумбар":
                g_discs.add("fortitude");
                g_discs.add("temporis");
                break;
            case "Истишия":
                g_discs.add("potence");
                g_discs.add("alchemistry");
                g_discs.add("melpominee");
                break;
            case "Келемвор":
                g_discs.add("conveyance");
                g_discs.add("auspex");
                g_discs.add("temporis");
                break;
            case "Коссут":
                g_discs.add("potence");
                g_discs.add("hellfire");
                g_discs.add("dominate");
                break;
            case "Латандер":
                g_discs.add("healing");
                g_discs.add("potence");
                g_discs.add("presence");
                break;
            case "Мистра":
                g_discs.add("hellfire");
                g_discs.add("alchemistry");
                g_discs.add("enchantment");
                break;
            case "Огма":
                g_discs.add("conveyance");
                g_discs.add("alchemistry");
                g_discs.add("enchantment");
                break;
            case "Сайрик":
                g_discs.add("dominate");
                g_discs.add("chimerstry");
                g_discs.add("dementation");
                break;
            case "Суне":
                g_discs.add("presence");
                g_discs.add("healing");
                g_discs.add("celerity");
                break;
            case "Сильванус":
                g_discs.add("animalism");
                g_discs.add("protean");
                g_discs.add("serpentis");
                break;
            case "Талос":
                g_discs.add("hellfire");
                g_discs.add("weathercraft");
                g_discs.add("dominate");
                break;
            case "Темпус":
                g_discs.add("potence");
                g_discs.add("healing");
                g_discs.add("fortitude");
                break;
            case "Тир":
                g_discs.add("auspex");
                g_discs.add("fortitude");
                break;
            case "Чонти":
                g_discs.add("weathercraft");
                g_discs.add("healing");
                g_discs.add("animalism");
                break;
            case "Шар":
                g_discs.add("dominate");
                g_discs.add("obtenebration");
                g_discs.add("obfuscate");
                break;
            case "Бэйн":
                g_discs.add("quietus");
                g_discs.add("dominate");
                g_discs.add("daimoinon");
                break;
        }

        display_discs();
    }

    private void display_discs () {
        for( String d: g_discs ) {
            if ( d.equals("alchemistry") ) {
                d = "dis_alchemistry";
            }
            int resID = getResources().getIdentifier(d, "id", getPackageName());
            View disc = findViewById(resID);
            disc.setVisibility(View.VISIBLE);
        }
    }

    private void fill_map() {
        Integer pos;

        // alignment
        ArrayAdapter<CharSequence> adapter_al = ArrayAdapter.createFromResource(this,
                R.array.alignments_array, android.R.layout.simple_spinner_item);
        adapter_al.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_al.setAdapter(adapter_al);
        pos = adapter_al.getPosition( char_o.alignment );
        spinner_al.setSelection(pos);

        // class
        ArrayAdapter<CharSequence> adapter_cl = ArrayAdapter.createFromResource(this,
                R.array.classes_array, android.R.layout.simple_spinner_item);
        adapter_cl.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_cl.setAdapter(adapter_cl);
        pos = adapter_cl.getPosition( char_o.class_name );
        spinner_cl.setSelection(pos);

        // god
        if ( char_o.class_name.equals("Жрец") ) {
            fix_gods_list(char_o.alignment);
            pos = adapter_gd.getPosition(char_o.god);
            spinner_gd.setSelection(pos);
        }

        Integer val;

        // attributes
        for ( String resource : char_o.phis_attr.keySet() ) {
            val = char_o.phis_attr.get(resource);
            set_range( "attr", resource, val );
            unset_upper_range( "attr", resource, val );
            update_summary( "attr", resource );
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
            update_summary( "attr", resource );
        }

        // abilities
        for ( String resource : char_o.tal_abl.keySet() ) {
            val = char_o.tal_abl.get(resource);
            set_range( "abl", resource, val );
            unset_upper_range( "abl", resource, val );
            update_summary( "abl", resource );
        }
        for ( String resource : char_o.skl_abl.keySet() ) {
            val = char_o.skl_abl.get(resource);
            set_range( "abl", resource, val );
            unset_upper_range( "abl", resource, val );
            update_summary( "abl", resource );
        }
        for ( String resource : char_o.kng_abl.keySet() ) {
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

        set_range( "button", "wp", char_o.wp );
        unset_upper_range( "button", "wp", char_o.wp );

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

        lock_unlock_names();
        set_health_pen();
    }

    private void lock_unlock_names() {
        if ( char_o.Generated == 1 ) {
            spinner_al.setVisibility(View.GONE);
            spinner_cl.setVisibility(View.GONE);
            spinner_gd.setVisibility(View.GONE);

            TextView al = (TextView) findViewById(R.id.txt_charlist_alignment);
            al.setText(getResources().getString(R.string.charlist_alignment_hint, char_o.alignment));
            al.setVisibility(View.VISIBLE);

            TextView cl = (TextView) findViewById(R.id.txt_charlist_class);
            cl.setText(getResources().getString(R.string.charlist_class_hint, char_o.class_name));
            cl.setVisibility(View.VISIBLE);

            if (char_o.class_name.equals("Жрец")) {
                TextView gd = (TextView) findViewById(R.id.txt_charlist_gods);
                gd.setText(getResources().getString(R.string.charlist_gods_hint, char_o.god));
                gd.setVisibility(View.VISIBLE);
            }
        }
        else {
            spinner_al.setVisibility(View.VISIBLE);
            spinner_cl.setVisibility(View.VISIBLE);

            TextView al = (TextView) findViewById(R.id.txt_charlist_alignment);
            al.setVisibility(View.GONE);

            TextView cl = (TextView) findViewById(R.id.txt_charlist_class);
            cl.setVisibility(View.GONE);

            if (char_o.class_name.equals("Жрец")) {
                spinner_gd.setVisibility(View.VISIBLE);
                TextView gd = (TextView) findViewById(R.id.txt_charlist_gods);
                gd.setVisibility(View.GONE);
            }
        }
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

    @SuppressWarnings("unused")
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
                return ;
            }
            char_o.sp_resources.put(special_resource, sp_value);
            mark_checkboxes( special_resource, sp_value, R.drawable.ic_check_box_black_24dp );
            set_health_pen();
            return ;
        }
        else if ( special_resource.equals("rage") ) {
            sp_value += 1;
            char_o.sp_resources.put(special_resource, sp_value);
            mark_checkboxes( special_resource, sp_value, R.drawable.ic_check_box_black_24dp );
            return ;
        }

        Integer limit = char_o.sp_resources.get( "perm_" + special_resource );
        if ( (special_resource.equals("faith")) || (sp_value < limit) ) {
            sp_value++;
            char_o.sp_resources.put(special_resource, sp_value);
            mark_checkboxes( special_resource, sp_value, R.drawable.ic_check_box_black_24dp );
        }
    }

    @SuppressWarnings("unused")
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

    @SuppressWarnings("unused")
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

        reset_parameter(special_resource);

        if ( special_resource.equals("health") ) {
            TextView t = (TextView)findViewById(R.id.special_limit_health);
            assert t != null;
            t.setTextColor( Color.parseColor("#33b5e5") );
            t.setText( "0" );
        }
    }

    private void reset_parameter ( String name ) {
        Integer sp_value = char_o.sp_resources.get(name);
        switch (name) {
            case "health":
                while (sp_value > 0) {
                    mark_checkboxes(name, sp_value, R.drawable.ic_crop_din_black_24dp);
                    sp_value--;
                }
                char_o.sp_resources.put(name, 0);
                break;
            case "rage":
                for (Integer i = 1; i <= 10; i++) {
                    mark_checkboxes(name, i, R.drawable.ic_crop_din_black_24dp);
                }
                if (char_o.class_name.equals("Воин")) {
                    char_o.sp_resources.put(name, 1);
                    mark_checkboxes(name, 1, R.drawable.ic_check_box_black_24dp);
                }
                break;
            default:
                Integer perm_val = char_o.wp;
                for (Integer i = 1; i <= perm_val; i++) {
                    mark_checkboxes(name, i, R.drawable.ic_check_box_black_24dp);
                }
                perm_val++;
                for (Integer i = perm_val; i <= 10; i++) {
                    mark_checkboxes(name, i, R.drawable.ic_crop_din_black_24dp);
                }
                break;
        }
    }

    @SuppressWarnings("unused")
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

    @SuppressWarnings({"UnusedParameters", "unused"})
    public void save_char(MenuItem item) {
        Context context = this;
        char_o.flush( context );
    }

    @SuppressWarnings({"UnusedParameters", "unused"})
    public void run_load_screen(MenuItem item) {

        Intent intent = new Intent(this, load_char.class);
        startActivity(intent);
        finish();
    }

    @SuppressWarnings({"UnusedParameters", "unused"})
    public void new_char(MenuItem item) {
        Intent intent = new Intent(this, enter_name.class);
        startActivity(intent);
        finish();
    }

    @SuppressWarnings("unused")
    public void run_hide_zero(MenuItem item) {

        item.setVisible(false);
        MenuItem show_params =  menu_bar.findItem(R.id.action_show_zero);
        show_params.setVisible(true);

        for ( String param : char_o.tal_abl.keySet() ) {
            if ( param.equals("gen_points") ) {
                continue;
            }
            if ( char_o.tal_abl.get(param) == 0 ) {
                display_param(param, View.GONE);
            }
        }

        for ( String param : char_o.skl_abl.keySet() ) {
            if ( param.equals("gen_points") ) {
                continue;
            }
            if ( char_o.skl_abl.get(param) == 0 ) {
                display_param(param, View.GONE);
            }
        }

        for ( String param : char_o.kng_abl.keySet() ) {
            if ( param.equals("gen_points") ) {
                continue;
            }
            if ( char_o.kng_abl.get(param) == 0 ) {
                display_param(param, View.GONE);
            }
        }

        for ( String param : char_o.bkg.keySet() ) {
            if ( char_o.bkg.get(param) == 0 ) {
                display_param(param, View.GONE);
            }
        }

        if ( char_o.class_name.equals("Маг") ) {
            for (String param : char_o.sph.keySet()) {
                if (char_o.sph.get(param) == 0) {
                    display_param(param, View.GONE);
                }
            }
        }

        if ( char_o.class_name.equals("Жрец") ) {
            for (String param : g_discs) {
                if (char_o.dis.get(param) == 0) {
                    if ( param.equals("alchemistry") ) {
                        param = "dis_alchemistry";
                    }
                    display_param(param, View.GONE);
                }
            }
        }

        if ( char_o.class_name.equals("Воин") ) {
            for (String param : char_o.gft.keySet()) {
                if (char_o.gft.get(param) == 0) {
                    display_param(param, View.GONE);
                }
            }
        }
    }

    private void display_param( String param, Integer vis ) {
        int resID = getResources().getIdentifier(param, "id", getPackageName());
        View w_param = findViewById(resID);
        w_param.setVisibility(vis);
    }

    @SuppressWarnings("unused")
    public void run_show_zero(MenuItem item) {
        item.setVisible(false);
        MenuItem hide_params = menu_bar.findItem(R.id.action_hide_zero);
        hide_params.setVisible(true);

        for ( String param : char_o.tal_abl.keySet() ) {
            if ( param.equals("gen_points") ) {
                continue;
            }
            if ( param.equals("enlightenment") && !char_o.class_name.equals("Маг") ){
                continue;
            }
            if ( char_o.tal_abl.get(param) == 0 ) {
                display_param(param, View.VISIBLE);
            }
        }

        for ( String param : char_o.skl_abl.keySet() ) {
            if ( param.equals("gen_points") ) {
                continue;
            }
            if ( param.equals("concentration") && !char_o.class_name.equals("Маг") ){
                continue;
            }
            if ( char_o.skl_abl.get(param) == 0 ) {
                display_param(param, View.VISIBLE);
            }
        }

        for ( String param : char_o.kng_abl.keySet() ) {
            if ( param.equals("gen_points") ) {
                continue;
            }
            if ( char_o.kng_abl.get(param) == 0 ) {
                display_param(param, View.VISIBLE);
            }
        }

        for ( String param : char_o.bkg.keySet() ) {
            if ( char_o.bkg.get(param) == 0 ) {
                display_param(param, View.VISIBLE);
            }
        }

        if ( char_o.class_name.equals("Маг") ) {
            for (String param : char_o.sph.keySet()) {
                if (char_o.sph.get(param) == 0) {
                    display_param(param, View.VISIBLE);
                }
            }
        }

        if ( char_o.class_name.equals("Жрец") ) {
            display_discs();
        }

        if ( char_o.class_name.equals("Воин") ) {
            for (String param : char_o.gft.keySet()) {
                if (char_o.gft.get(param) == 0) {
                    display_param(param, View.VISIBLE);
                }
            }
        }
    }

    @SuppressWarnings("unused")
    public void run_up_by_exp(MenuItem item) {

        MenuItem show_params =  menu_bar.findItem(R.id.finish_up_by_exp);
        show_params.setVisible(true);
        show_params =  menu_bar.findItem(R.id.up_by_exp);
        show_params.setVisible(false);

        char_o.upping = true;
        char_o.store_values();
    }

    @SuppressWarnings("unused")
    public void finish_run_up_by_exp(MenuItem item) {
        char_o.upping = false;
        finish_exp_points();
    }

    @SuppressWarnings("unused")
    public void helper_clicked(View view) {
        String IdAsString = view.getResources().getResourceName(view.getId());
        Pattern p = Pattern.compile("(\\p{Lower}+)_(\\p{Lower}+)$");
        Matcher m = p.matcher(IdAsString);
        String group;
        String name;
        if( m.find() ){
            group = m.group(1);
            name  = m.group(2);
        }
        else {
            throw new RuntimeException( "Can't fine resource and value in " + IdAsString );
        }

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.help);
        TextView txt = (TextView) dialog.findViewById(R.id.help_text);

        String id_ref = String.format(Locale.getDefault(), "help_%s_%s", group, name);
        int resID = getResources().getIdentifier(id_ref, "string", getPackageName());

        if ( resID != 0 ) {
            txt.setText(getString(resID));
            dialog.show();
        }
    }
}
