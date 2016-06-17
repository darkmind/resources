package com.example.asm.resources;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class main_screen extends AppCompatActivity {
    private Map<String, Integer> sp_resources;

    private boolean mVisible;

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

        setContentView(R.layout.activity_main_screen);
        character char_o = character.getInstance();
        sp_resources = char_o.get_sp_res();
        fill_map();

        mVisible = true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        return true;
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

    public void radio_button_clicked(View view) {
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
        character char_o = character.getInstance();
        Context context = this;
        char_o.flush( context );
    }

    public void run_load_screen(MenuItem item) {

        Intent intent = new Intent(this, load_char.class);
        startActivity(intent);
    }

    public void new_char(MenuItem item) {
        Intent intent = new Intent(this, enter_name.class);
        startActivity(intent);
    }
}
