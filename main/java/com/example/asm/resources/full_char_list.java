package com.example.asm.resources;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class full_char_list extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_char_list);

        final Spinner spinner_gd = (Spinner) findViewById(R.id.charlist_gods);
        ArrayAdapter<CharSequence> adapter_gd = ArrayAdapter.createFromResource(this,
                R.array.gods_array, android.R.layout.simple_spinner_item);
        adapter_gd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_gd.setAdapter(adapter_gd);
        spinner_gd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });


        final Spinner spinner_al = (Spinner) findViewById(R.id.charlist_alignment);
        ArrayAdapter<CharSequence> adapter_al = ArrayAdapter.createFromResource(this,
                R.array.alignments_array, android.R.layout.simple_spinner_item);
        adapter_al.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_al.setAdapter(adapter_al);
        spinner_al.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if( position == 0 ) {}
                else if( position == 1 ) {}
                else if( position == 2 ) {}
                else if( position == 3 ) {}
                else if( position == 4 ) {}
                else if( position == 5 ) {}
                else if( position == 6 ) {}
                else if( position == 7 ) {}
                else if( position == 8 ) {}
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });


        final Spinner spinner_cl = (Spinner) findViewById(R.id.charlist_class);
        ArrayAdapter<CharSequence> adapter_cl = ArrayAdapter.createFromResource(this,
                R.array.classes_array, android.R.layout.simple_spinner_item);
        adapter_cl.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_cl.setAdapter(adapter_cl);
        spinner_cl.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // priest
                if( position == 2 ) {
                    spinner_gd.setVisibility(View.VISIBLE);
                }
                else {
                    spinner_gd.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
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
            mark_dots( group, name, number, R.drawable.ic_fiber_manual_record_black_24dp );
        }
    }

    private void unset_upper_range( String group, String name, Integer number ) {
        for ( Integer i = (number + 1); i <= 5; i++ ){
            mark_dots( group, name, number, R.drawable.ic_radio_button_unchecked_black_24dp );
        }
    }

    private void mark_dots( String group, String name, Integer number, int pic ) {
        String id_ref = String.format(Locale.getDefault(), "rb_%s_%s%d", group,  name, number);
        int resID = getResources().getIdentifier(id_ref, "id", getPackageName());
        ImageView img = (ImageView) findViewById(resID);
        assert img != null;
        img.setImageResource(pic);
    }
}
