package com.example.asm.resources;

import android.annotation.SuppressLint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class main_screen extends AppCompatActivity {
    private Map<String, Integer> sp_resources = new HashMap<String,Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        fill_map();
    }

    private void fill_map() {
        sp_resources.put("rage", 1);
        sp_resources.put("faith", 0);
        sp_resources.put("wp", 0);
        sp_resources.put("perm_rage", 1);
        sp_resources.put("perm_faith", 0);
        sp_resources.put("perm_wp", 0);

        ImageView first_rage_point = (ImageView) findViewById(R.id.image_rage1);
        first_rage_point.setImageResource(R.drawable.ic_check_box_black_24dp);

        RadioButton first_perm_rage = (RadioButton) findViewById(R.id.radio_button_rage1);
        first_perm_rage.setChecked(true);
    }

    public void radio_button_clicked(View view) {
        String IdAsString = view.getResources().getResourceName(view.getId());
        Pattern p = Pattern.compile("^(\\D+)(\\d+)$");
        Matcher m = p.matcher(IdAsString);
        m.find();
        String name = m.group(1);
        int id = Integer.parseInt(m.group(2));
        set_range( name, id );
        unset_upper_range( name, id );
        refresh_sp( name, id );
    }

    private void set_range( String name, int id ) {
        RadioButton b;
        String id_ref;
        int resID;
        while ( id > 0 ) {
            id_ref = String.format("%s%d", name, id);
            resID = getResources().getIdentifier(id_ref, "id", getPackageName());
            b = (RadioButton) findViewById(resID);
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
            id_ref = String.format("%s%d", name, id);
            resID = getResources().getIdentifier(id_ref, "id", getPackageName());
            b = (RadioButton) findViewById(resID);
            b.setChecked(false);
            id++;
        }
    }

    private void refresh_sp ( String name, int id ) {
        Pattern p = Pattern.compile("_(\\p{Lower}+)$");
        Matcher m = p.matcher(name);
        m.find();
        String sp_name = m.group(1);

        sp_resources.put( "perm_" + sp_name, id );
        if ( sp_name.equals("faith") ) {
            return ;
        }

        Integer tmp_val = sp_resources.get( sp_name );
        if ( id < tmp_val ) {
            sp_resources.put( sp_name, id );
            while ( tmp_val > id ) {
                String id_ref = String.format("image_%s%d", sp_name, tmp_val);
                int resID = getResources().getIdentifier(id_ref, "id", getPackageName());
                ImageView img = (ImageView) findViewById(resID);
                img.setImageResource(R.drawable.ic_crop_din_black_24dp);
                tmp_val--;
            }
            sp_resources.put( sp_name, tmp_val );
        }
    }

    public void plus_button(View view) {
        String IdAsString = view.getResources().getResourceName(view.getId());
        Pattern p = Pattern.compile("/(\\p{Lower}+)_");
        Matcher m = p.matcher(IdAsString);
        m.find();
        String special_resource = m.group(1);

        Integer sp_value = sp_resources.get( special_resource );
        Integer limit = sp_resources.get( "perm_" + special_resource );
        if ( (special_resource.equals("faith")) || (sp_value < limit) ) {
            sp_value++;
            sp_resources.put(special_resource, sp_value);

            String id_ref = String.format("image_%s%d", special_resource, sp_value);
            int resID = getResources().getIdentifier(id_ref, "id", getPackageName());
            ImageView img = (ImageView) findViewById(resID);
            img.setImageResource(R.drawable.ic_check_box_black_24dp);
        }
    }

    public void minus_button(View view) {
        String IdAsString = view.getResources().getResourceName(view.getId());
        Pattern p = Pattern.compile("/(\\p{Lower}+)_");
        Matcher m = p.matcher(IdAsString);
        m.find();
        String special_resource = m.group(1);
        Integer sp_value = sp_resources.get( special_resource );
        if ( sp_value > 0 ) {
            String id_ref = String.format("image_%s%d", special_resource, sp_value);
            int resID = getResources().getIdentifier(id_ref, "id", getPackageName());
            ImageView img = (ImageView) findViewById(resID);
            img.setImageResource(R.drawable.ic_crop_din_black_24dp);

            sp_value--;
            sp_resources.put(special_resource, sp_value);
        }
    }

    public void reset_button(View view) {
        String IdAsString = view.getResources().getResourceName(view.getId());
        Pattern p = Pattern.compile("/(\\p{Lower}+)_");
        Matcher m = p.matcher(IdAsString);
        m.find();
        String special_resource = m.group(1);

        if ( special_resource.equals("rage") ) {
            sp_resources.put(special_resource, 1);
            for ( Integer i = 2; i <= 10; i++ ) {
                String id_ref = String.format("image_%s%d", special_resource, i);
                int resID = getResources().getIdentifier(id_ref, "id", getPackageName());
                ImageView img = (ImageView) findViewById(resID);
                img.setImageResource(R.drawable.ic_crop_din_black_24dp);
            }
        }
        else {
            Integer perm_val = sp_resources.get( "perm_" + special_resource );
            sp_resources.put( special_resource, perm_val );
            for ( Integer i = 1; i <= perm_val; i++ ) {
                String id_ref = String.format("image_%s%d", special_resource, i);
                int resID = getResources().getIdentifier(id_ref, "id", getPackageName());
                ImageView img = (ImageView) findViewById(resID);
                img.setImageResource(R.drawable.ic_check_box_black_24dp);
            }
            perm_val++;
            for ( Integer i = perm_val; i <= 10; i++ ) {
                String id_ref = String.format("image_%s%d", special_resource, i);
                int resID = getResources().getIdentifier(id_ref, "id", getPackageName());
                ImageView img = (ImageView) findViewById(resID);
                img.setImageResource(R.drawable.ic_crop_din_black_24dp);
            }
        }
    }

}
