package com.example.asm.resources;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class main_screen extends AppCompatActivity {
    private Map<String, Integer> sp_resources = new HashMap<String,Integer>();

    private boolean mVisible;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };

    public void switch_full_screen() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        mHideHandler.postDelayed(mHidePart2Runnable, 0);
        mVisible = false;
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_screen);

        fill_map();

        mVisible = true;
    }

    public void switch_screen(View view) {
        mContentView = view;
        switch_full_screen();
    }

    private void fill_map() {
        sp_resources.put("rage", 1);
        sp_resources.put("faith", 0);
        sp_resources.put("wp", 0);
        sp_resources.put("health", 0);
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

        if ( special_resource.equals("health") ) {
            sp_value++;
            if ( sp_value > 14 ) {
                sp_value = 14;
                return ;
            }
            sp_resources.put(special_resource, sp_value);

            String id_ref = String.format("image_%s%d", special_resource, sp_value);
            int resID = getResources().getIdentifier(id_ref, "id", getPackageName());
            ImageView img = (ImageView) findViewById(resID);
            img.setImageResource(R.drawable.ic_check_box_black_24dp);

            TextView t = (TextView)findViewById(R.id.special_limit_health);
            if ( sp_value <= 2 ) {
                return ;
            }
            else if ( 2 < sp_value && sp_value < 7 ) {
                t.setTextColor(Color.RED);
                t.setText( "-1" );
                return ;
            }
            else if ( 6 < sp_value && sp_value < 11 ) {
                t.setTextColor(Color.RED);
                t.setText( "-2" );
                return ;
            }
            else if ( 10 < sp_value && sp_value < 13 ) {
                t.setTextColor(Color.RED);
                t.setText( "-5" );
                return ;
            }
            else if ( 12 < sp_value && sp_value <= 14 ) {
                t.setTextColor(Color.RED);
                t.setText( "-10" );
                return ;
            }
        }

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

        if ( special_resource.equals("health") ) {
            if ( sp_value == 0 ) {
                return ;
            }

            String id_ref = String.format("image_%s%d", special_resource, sp_value);
            int resID = getResources().getIdentifier(id_ref, "id", getPackageName());
            ImageView img = (ImageView) findViewById(resID);
            img.setImageResource(R.drawable.ic_crop_din_black_24dp);

            sp_value--;
            sp_resources.put(special_resource, sp_value);

            TextView t = (TextView)findViewById(R.id.special_limit_health);
            if ( sp_value <= 2 ) {
                t.setTextColor( Color.parseColor("#33b5e5") );
                t.setText( "0" );
                return ;
            }
            else if ( 2 < sp_value && sp_value < 7 ) {
                t.setTextColor(Color.RED);
                t.setText( "-1" );
                return ;
            }
            else if ( 6 < sp_value && sp_value < 11 ) {
                t.setTextColor(Color.RED);
                t.setText( "-2" );
                return ;
            }
            else if ( 10 < sp_value && sp_value < 13 ) {
                t.setTextColor(Color.RED);
                t.setText( "-5" );
                return ;
            }
            else if ( 12 < sp_value && sp_value <= 14 ) {
                t.setTextColor(Color.RED);
                t.setText( "-10" );
                return ;
            }
        }

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

        if ( special_resource.equals("health") ) {
            Integer sp_value = sp_resources.get(special_resource);

            TextView t = (TextView)findViewById(R.id.special_limit_health);
            t.setTextColor( Color.parseColor("#33b5e5") );
            t.setText( "0" );

            while ( sp_value > 0 ) {
                String id_ref = String.format("image_%s%d", special_resource, sp_value);
                int resID = getResources().getIdentifier(id_ref, "id", getPackageName());
                ImageView img = (ImageView) findViewById(resID);
                img.setImageResource(R.drawable.ic_crop_din_black_24dp);
                sp_value--;
            }
            sp_resources.put(special_resource, 0);
            return ;
        }

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
